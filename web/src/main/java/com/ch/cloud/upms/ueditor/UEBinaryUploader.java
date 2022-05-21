package com.ch.cloud.upms.ueditor;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.upload.StorageManager;
import com.ch.e.PubError;
import com.ch.utils.CommonUtils;
import com.ch.e.ExceptionUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 01370603
 * @since 2018/12/24 11:54
 */
public class UEBinaryUploader {

    public static State save(HttpServletRequest request, Map<String, Object> conf) {
        FileItemStream fileStream = null;
        boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

        if (!ServletFileUpload.isMultipartContent(request)) {
            return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
        }

        ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

        if (isAjaxUpload) {
            upload.setHeaderEncoding("UTF-8");
        }

        try {
            String originFileName = "";
            String contentType = "";
            String md5 = "";
            InputStream is = null;
            if (request instanceof MultipartHttpServletRequest) {
                MultipartFile upfile = ((MultipartHttpServletRequest) request).getFile((String) conf.get("fieldName"));
                if (upfile != null) {
                    originFileName = upfile.getOriginalFilename();
                    contentType = upfile.getContentType();
                    md5 = DigestUtils.md5Hex(upfile.getInputStream());
                    is = upfile.getInputStream();
                }
            }

            if (CommonUtils.isEmpty(is)) {
                FileItemIterator iterator = upload.getItemIterator(request);
                while (iterator.hasNext()) {
                    fileStream = iterator.next();
                    if (!fileStream.isFormField())
                        break;
                    fileStream = null;
                }
                if (fileStream == null) {
                    return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
                }
                originFileName = fileStream.getName();
                contentType = fileStream.getContentType();
                md5 = DigestUtils.md5Hex(fileStream.openStream());
                is = fileStream.openStream();
            }
            if (originFileName == null) {
                throw ExceptionUtils.create(PubError.INVALID);
            }

            String savePath = (String) conf.get("savePath");

            String suffix = FileType.getSuffixByFilename(originFileName);
            originFileName = originFileName.substring(0, originFileName.length() - suffix.length());
            savePath = savePath + suffix;

            long maxSize = (Long) conf.get("maxSize");

            if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
                return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
            }

            savePath = PathFormat.parse(savePath, originFileName);

//			String physicalPath = (String) conf.get("rootPath") + savePath;

            String physicalPath = (String) conf.get("physicsPath");
            if (physicalPath != null && !"".equals(physicalPath))
                physicalPath += savePath;
            else
                physicalPath = conf.get("rootPath") + savePath;

            State storageState = StorageManager.saveFileByInputStream(is, physicalPath, maxSize);
            is.close();

            if (storageState.isSuccess()) {
//                if (storageState.getInfoString("url") == null) {
//                    storageState.putInfo("url", PathFormat.format(savePath));
//                }
                storageState.putInfo("url", PathFormat.format(savePath));
                storageState.putInfo("type", suffix);
                storageState.putInfo("original", originFileName + suffix);
                storageState.putInfo("contentType", contentType);
                storageState.putInfo("md5", md5);
            }

            return storageState;
        } catch (FileUploadException e) {
            return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
        } catch (IOException ignored) {
        }
        return new BaseState(false, AppInfo.IO_ERROR);
    }

    private static boolean validType(String type, String[] allowTypes) {
        List<String> list = Arrays.asList(allowTypes);

        return list.contains(type);
    }
}
