package com.ch.cloud.upms.controller;

import com.baidu.ueditor.define.ActionMap;
import com.ch.Constants;
import com.ch.cloud.upms.model.BtUploadFile;
import com.ch.cloud.upms.pojo.FileInfo;
import com.ch.cloud.upms.service.IUploadFileService;
import com.ch.cloud.upms.ueditor.UEActionEnter;
import com.ch.cloud.upms.ueditor.UEListResult;
import com.ch.cloud.upms.ueditor.UEResult;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.type.FileType;
import com.ch.utils.DateUtils;
import com.ch.utils.JSONUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * desc:
 *
 * @author zhimin
 * @date 2018/12/21 10:40 PM
 */
@RestController
@RequestMapping("ue")
public class UEditorController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUploadFileService uploadFileService;

    @Value("${path.upload:}")
    private String uploadPath;

    @RequestMapping({"", "/"})
    public void config(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        int type = ActionMap.getType(request.getParameter("action"));
        String username = RequestUtils.getHeaderUser(request);

        try {
            String exec = new UEActionEnter(request, uploadPath + File.separator + "ue").exec();

            if (type == ActionMap.UPLOAD_IMAGE || type == ActionMap.UPLOAD_FILE || type == ActionMap.UPLOAD_VIDEO || type == ActionMap.UPLOAD_SCRAWL) {
                UEResult uploadResult = JSONUtils.fromJson(exec, UEResult.class);
                if (uploadResult.isSuccess()) {
                    BtUploadFile q1 = new BtUploadFile();
                    q1.setMd5(uploadResult.getMd5());
                    q1.setFileType(uploadResult.getType());
                    List<BtUploadFile> records1 = uploadFileService.find(q1);
                    if (!records1.isEmpty()) {
                        FileUtils.deleteQuietly(new File(uploadPath + File.separator + "ue" + uploadResult.getUrl()));
                        uploadResult.setUrl(records1.get(0).getFilePath());
                    } else {

                        String url = "/upload/ue" + uploadResult.getUrl();
                        uploadResult.setUrl(url);

                        BtUploadFile uploadFile = new BtUploadFile();
                        uploadFile.setOriginalName(uploadResult.getOriginal());
                        uploadFile.setFileName(uploadResult.getTitle());
                        uploadFile.setFilePath(uploadResult.getUrl());
                        uploadFile.setFileType(uploadResult.getType());
                        uploadFile.setFileSize(Long.valueOf(uploadResult.getSize()));
                        if (type == ActionMap.UPLOAD_IMAGE) {
                            uploadFile.setType(FileType.IMAGE.getCode());
                        } else if (type == ActionMap.UPLOAD_SCRAWL) {
                            uploadFile.setType(FileType.SCRAWL.getCode());
                        } else if (type == ActionMap.UPLOAD_VIDEO) {
                            uploadFile.setType(FileType.IMAGE.getCode());
                        } else {
                            uploadFile.setType(FileType.UNKNOWN.getCode());
                        }
                        uploadFile.setMd5(uploadResult.getMd5());
                        uploadFile.setStatus(Constants.ENABLED);
                        uploadFile.setSrcType(Constants.ZERO);
                        uploadFile.setCreateAt(DateUtils.currentTime());
                        uploadFile.setCreateBy(username);
                        uploadFileService.save(uploadFile);
                    }
                    exec = JSONUtils.toJson(uploadResult);
                }
            }
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            logger.error("ue action fail!", e);
            writer.write(JSONUtils.toJson(new UEResult()));
            writer.flush();
            writer.close();
        }
    }

    /*
    {
        "state": "SUCCESS",
        "list": [{
            "url": "upload/1.jpg"
        }, {
            "url": "upload/2.jpg"
        }, ],
        "start": 20,
        "total": 100
    }
    */
    @RequestMapping("listImage")
    public UEListResult imageList(HttpServletRequest request) {

        String username = RequestUtils.getHeaderUser(request);

        List<BtUploadFile> fileList = uploadFileService.findImageByUsername(username);
        UEListResult result = new UEListResult();
        result.setState("SUCCESS");
        result.setTotal(fileList.size());
        if (!fileList.isEmpty()) {
            List<FileInfo> list = fileList.parallelStream().map(r -> {
                FileInfo info = new FileInfo();
                info.setOriginal(r.getOriginalName());
                info.setTitle(r.getFileName());
                info.setUrl(r.getFilePath());
                info.setType(r.getFileType());
                return info;
            }).collect(Collectors.toList());
            result.setList(list);
        }
        return result;
    }


    /**
     * 下载服务器已存在的文件
     *
     * @param request  请求
     * @param response 响应
     * @param fileName 文件名
     */
//    @RequestMapping("upload/image/{dir}/{fileName}")
    public BufferedImage getImage(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @PathVariable("dir") String dir,
                                  @PathVariable("fileName") String fileName) throws IOException {
        File file = new File(uploadPath + "/image/" + dir, fileName);
        return ImageIO.read(new FileInputStream(file));
    }

}
