package com.ch.cloud.upms.controller;

import com.ch.Constants;
import com.ch.Status;
import com.ch.cloud.upms.model.BtUploadFile;
import com.ch.cloud.upms.pojo.UploadImg;
import com.ch.cloud.upms.service.IUploadFileService;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.e.CoreError;
import com.ch.e.CoreException;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.*;
import com.google.common.collect.Lists;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 描述：com.ch.cloud.upms.controller
 *
 * @author 80002023
 * 2017/3/21.
 * @version 1.0
 * @since 1.8
 */
@RestController
public class UploadController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    IUploadFileService uploadFileService;

    @Value("${path.upload:}")
    private String uploadPath;

    @PostMapping("upload")
    public Result<?> upload(@RequestParam("files[]") MultipartFile[] files,
                            HttpServletRequest request,
                            String type,
                            String version) {
        logger.info("File upload ...");
        if (CommonUtils.isEmpty(uploadPath)) {
            return new Result<>(CoreError.NON_NULL, "系统未配置存储目录，不上传，请联系管理员！");
        }
        List<String> fileNameList = Lists.newArrayList();
        List<File> newFiles = Lists.newArrayList();

        String username = RequestUtils.getHeaderUser(request);
        try {

            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    logger.info("没有文件");
                    continue;
                }
                logger.info("文件名称: {}, 文件长度: {}, 文件类型: {}", file.getOriginalFilename(), file.getSize(), file.getContentType());


                String md5 = DigestUtils.md5Hex(file.getInputStream());
                String fileType = FileExtUtils.getFileExtensionName(file.getOriginalFilename());


                //校验文件MD5是否存在
                BtUploadFile q1 = new BtUploadFile();
                q1.setMd5(md5);
                q1.setFileType(fileType);
                List<BtUploadFile> records1 = uploadFileService.find(q1);
                //校验文件名、文件类型及版本是否存在
                BtUploadFile q2 = new BtUploadFile();
                q2.setOriginalName(file.getOriginalFilename());
                q2.setFileType(file.getContentType());
                q2.setType(type);
                q2.setCreateBy(username);
                List<BtUploadFile> records2 = uploadFileService.find(q2);
                //文件会上传到\\upload\\文件夹中
                String dateStr = DateUtils.format(DateUtils.currentTime(), DateUtils.Pattern.DATE_SHORT);
                String folder = FileExtUtils.linkPath(dateStr, username);
                if ("1".equals(type)) {
                    if (CommonUtils.isNotEmpty(records1)) {
//                        throw ExceptionUtils.create(CoreError.EXISTS, file.getOriginalFilename() + "文件已存在！");
                        fileNameList.add(records1.get(0).getFileName());
                        fileNameList.add(records1.get(0).getFilePath());
                        return new Result<>(fileNameList);
                    }
                    folder = FileExtUtils.linkPath("common", fileType);
                } else if (CommonUtils.isNotEmpty(records1)) {
//                    throw ExceptionUtils.create(CoreError.EXISTS, file.getOriginalFilename() + "文件您已上传！");
                    fileNameList.add(records1.get(0).getFileName());
                    fileNameList.add(records1.get(0).getFilePath());
                    return new Result<>(fileNameList);
                }
                //会把原Session invoke
                //String path = request.getSession().getServletContext().getRealPath(folder);
                //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
                String uuid = UUIDGenerator.generate();
                String fileName = uuid + FileExtUtils.getFileExtension(file.getOriginalFilename());
                //保存文件
                File newFile = new File(uploadPath + folder, fileName);
                FileUtils.copyInputStreamToFile(file.getInputStream(), newFile);
                newFiles.add(newFile);
                // 删除原文件记录
                if (!"1".equals(type) && CommonUtils.isNotEmpty(records2)) {
                    records2.forEach(r -> {
                        r.setStatus(Constants.THREE);
                        r.setUpdateBy(username);
                        r.setUpdateAt(DateUtils.currentTime());
                    });
                    uploadFileService.batchUpdate(records2);
                }
                // 生成文件记录
                BtUploadFile record = new BtUploadFile();
                record.setOriginalName(file.getOriginalFilename());
                record.setFileName(uuid);
                record.setFileType(fileType);
                record.setFileSize(file.getSize());
                record.setContentType(file.getContentType());
                record.setFilePath(folder + File.separator + fileName);
                record.setType(type);
                record.setMd5(md5);
                record.setVersion(version);
                record.setCreateBy(username);
                record.setCreateAt(DateUtils.currentTime());

                if ("1".equals(type)) {
                    record.setStatus(Constants.ENABLED);
                }

                int c = uploadFileService.save(record);
                if (c > 0) {
                    fileNameList.add(uuid);
                    fileNameList.add(record.getFilePath());
                    //deal with by type
                }
            }
            return new Result<>(fileNameList);
        } catch (CoreException e) {
            logger.error("upload file error!", e);
            newFiles.forEach(FileUtils::deleteQuietly);
            return new Result<>(e.getError(), e.getMessage());
        } catch (Exception e) {
            logger.error("upload file error!", e);
            newFiles.forEach(FileUtils::deleteQuietly);
            return new Result<>(CoreError.UNKNOWN, "遇到未知错误，请联系管理员！");
        }
    }


    @GetMapping("download/{uid}")
    public Result<BtUploadFile> download(@PathVariable String uid,
                                         HttpServletResponse response) {
        BtUploadFile record = uploadFileService.findByFileName(uid);
        if (record == null) return new Result<>(Status.ERROR);
        long speed = 1024 * 100L;//限制下载速度为100k/s,
        long current = 0;
        OutputStream os = null;
        InputStream is = null;
        try {
            os = response.getOutputStream();
            is = new FileInputStream(record.getFilePath());
            byte[] temp = new byte[1024];
            int i;
            long startTime = System.currentTimeMillis();
            while ((i = is.read(temp)) != -1) {
                current = current + i;
                os.write(temp);
                if (current > speed) {
                    pause(startTime + 1000);
                    current = 0;
                    startTime = System.currentTimeMillis();
                }
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(os, is);
        }
        return new Result<>(Status.ERROR);
    }

    @GetMapping("upload/remove/{uid}")
    public Result<Integer> remove(@PathVariable String uid, HttpServletRequest request) {
        String username = RequestUtils.getHeaderUser(request);
        return ResultUtils.wrapFail(() -> uploadFileService.remove(uid, username));
    }

    /**
     * 下载服务器已存在的文件
     *
     * @param request  请求
     * @param response 响应
     * @param fileName 文件名
     */
    @RequestMapping("download/{fileName}")
    public void downloadExistsFile(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @PathVariable("fileName") String fileName) {

        BtUploadFile record = uploadFileService.findByFileName(fileName);
        if (record == null) {
            throw ExceptionUtils.create(CoreError.NOT_EXISTS, "文件不存在");
        }
        ;

        logger.debug("下载文件路径：{}", record.getFilePath());
        long fSize = record.getFileSize();
        // 下载
        response.setContentType("application/x-download");
        String isoFileName = this.encodeFileName(record.getOriginalName());
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-Length", String.valueOf(fSize));
        response.setHeader("Content-Disposition", "attachment; filename=" + isoFileName);
        long pos = 0;
        if (null != request.getHeader("Range")) {
            // 断点续传
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            try {
                pos = Long.parseLong(request.getHeader("Range").replaceAll("bytes=", "").replaceAll("-", ""));
            } catch (NumberFormatException e) {
                logger.error(request.getHeader("Range") + " is not Number!");
                pos = 0;
            }
        }

        String contentRange = "bytes " + pos + "-" + (fSize - 1) + "/" + fSize;
        response.setHeader("Content-Range", contentRange);

        logger.debug("Content-Range: {}", contentRange);

        InputStream is = null;
        OutputStream os = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
//            os = response.getOutputStream();
//            is = new FileInputStream(record.getFilePath());
//            is.skip(pos);
            bos = new BufferedOutputStream(response.getOutputStream());
            bis = new BufferedInputStream(new FileInputStream(record.getFilePath()));
            long len = bis.skip(pos);
            byte[] buffer = new byte[5 * 1024];
            int length = 0;
            while ((length = bis.read(buffer, 0, buffer.length)) != -1) {
                bos.write(buffer, 0, length);
            }
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(bos, bis, os, is);
        }
    }


    private void pause(long time) {
        while (true) {
            if (System.currentTimeMillis() > time) {
                break;
            }
        }
    }

    private String encodeFileName(String fileName) {
        try {
            return new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    @PostMapping("upload/img")
    public Result<?> uploadImg(@RequestParam("files[]") MultipartFile[] files
            , UploadImg uploadImg
            , HttpServletRequest request) {
        logger.info("File upload ...");

        if (CommonUtils.isEmpty(uploadPath)) {
            return new Result<>(CoreError.NON_NULL, "系统未配置存储目录，不上传，请联系管理员！");
        }
        List<String> fileNameList = Lists.newArrayList();
        List<File> newFiles = Lists.newArrayList();

        String username = RequestUtils.getHeaderUser(request);
        try {

            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    logger.info("没有文件");
                    continue;
                }
                logger.info("文件名称: {}, 文件长度: {}, 文件类型: {}", file.getOriginalFilename(), file.getSize(), file.getContentType());


                String md5 = DigestUtils.md5Hex(file.getInputStream());
                String fileType = FileExtUtils.getFileExtensionName(file.getOriginalFilename());


                //校验文件MD5是否存在
                BtUploadFile q1 = new BtUploadFile();
                q1.setMd5(md5);
                q1.setFileType(fileType);
                List<BtUploadFile> records1 = uploadFileService.find(q1);
                //文件会上传到\\upload\\文件夹中
                String dateStr = DateUtils.format(DateUtils.currentTime(), DateUtils.Pattern.DATE_SHORT);
                String folder = FileExtUtils.linkPath(dateStr, username);
                if ("3".equals(uploadImg.getType())) {
                    folder = FileExtUtils.linkPath("article", dateStr);
                }
                if (CommonUtils.isNotEmpty(records1)) {
                    fileNameList.add(records1.get(0).getFileName());
                    fileNameList.add(records1.get(0).getFilePath());
                    return new Result<>(fileNameList);
                }
                //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
                String uuid = UUIDGenerator.generate();
                String fileName = uuid + FileExtUtils.getFileExtension(file.getOriginalFilename());
                //保存文件
                File newFile = new File(uploadPath + folder, fileName);
                if ("crop".equals(uploadImg.getAction())) {
                    float ratio = 1.0f;
                    if (uploadImg.getRatio() != null) ratio = uploadImg.getRatio();
                    ImageUtils.subRatio(file.getInputStream(), newFile, ratio);
                }
                newFiles.add(newFile);
                // 生成文件记录
                BtUploadFile record = new BtUploadFile();
                record.setOriginalName(file.getOriginalFilename());
                record.setFileName(uuid);
                record.setFileType(fileType);
                record.setFileSize(file.getSize());
                record.setContentType(file.getContentType());
                record.setFilePath(FileExtUtils.convertToUnix(FileExtUtils.linkPath("upload", folder, fileName)));
                record.setType(uploadImg.getType());
                record.setMd5(md5);
                record.setVersion(uploadImg.getVersion());
                record.setCreateBy(username);
                record.setCreateAt(DateUtils.currentTime());

                int c = uploadFileService.save(record);
                if (c > 0) {
                    fileNameList.add(uuid);
                    fileNameList.add(record.getFilePath());
                    //deal with by type
                }
            }
            return new Result<>(fileNameList);
        } catch (CoreException e) {
            logger.error("upload file error!", e);
            newFiles.forEach(FileUtils::deleteQuietly);
            return new Result<>(e.getError(), e.getMessage());
        } catch (Exception e) {
            logger.error("upload file error!", e);
            newFiles.forEach(FileUtils::deleteQuietly);
            return new Result<>(CoreError.UNKNOWN, "遇到未知错误，请联系管理员！");
        }
    }

    @PostMapping("upload/netImg")
    public Result<String> fetchNetImg(@RequestBody UploadImg uploadImg,
//                                      @RequestParam String action,
                                      HttpServletRequest request) {
        return ResultUtils.wrapFail(() -> {
            String username = RequestUtils.getHeaderUser(request);

            String md5 = EncryptUtils.md5(uploadImg.getImgUrl());
            String oFileName = ImageUtils.parseFileNameByUrl(uploadImg.getImgUrl());
            String fileExt = FileExtUtils.getFileExtension(oFileName);

            //校验文件MD5是否存在
            BtUploadFile q1 = new BtUploadFile();
            q1.setMd5(md5);
            q1.setFileType(fileExt);
            List<BtUploadFile> records1 = uploadFileService.find(q1);
            if (!records1.isEmpty()) {
                return records1.get(0).getFilePath();
            }

            String dateStr = DateUtils.format(DateUtils.currentTime(), DateUtils.Pattern.DATE_SHORT);
            String uuid = UUIDGenerator.generate();
            String folder = FileExtUtils.linkPath(dateStr, username);
            if ("3".equals(uploadImg.getType())) {
                folder = FileExtUtils.linkPath("article", dateStr);
            }
            String fileName = uuid + fileExt;
            //保存文件
            File newFile = new File(uploadPath + folder, fileName);

            if ("crop".equals(uploadImg.getAction())) {
                float ratio = 1.0f;
                if (uploadImg.getRatio() != null) ratio = uploadImg.getRatio();
                ImageUtils.subRatio(uploadImg.getImgUrl(), newFile, ratio);

            }
            // 生成文件记录
            BtUploadFile record = new BtUploadFile();
            record.setOriginalName(oFileName);
            record.setFileName(uuid);
            record.setFileType(fileExt);

            record.setFileSize(newFile.length());
//            record.setContentType(file.getContentType());
            record.setFilePath(FileExtUtils.linkPath("upload", folder, fileName));
            record.setType(uploadImg.getType());
            record.setMd5(md5);
//            record.setVersion(version);
            record.setCreateBy(username);
            record.setCreateAt(DateUtils.currentTime());

            record.setStatus(Constants.ENABLED);
            record.setSrcType(Constants.TWO);
            int c = uploadFileService.save(record);
            return record.getFilePath();
        });
    }
}
