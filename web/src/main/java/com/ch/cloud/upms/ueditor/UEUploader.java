package com.ch.cloud.upms.ueditor;

import com.baidu.ueditor.define.State;
import com.baidu.ueditor.upload.Base64Uploader;
import com.ch.toolkit.UUIDGenerator;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 01370603
 * @date 2018/12/24 11:53
 */
public class UEUploader {

    private HttpServletRequest request;
    private Map<String, Object> conf;

    public UEUploader(HttpServletRequest request, Map<String, Object> conf) {
        this.request = request;
        this.conf = conf;
    }

    public final State doExec() {
        String filedName = (String) this.conf.get("fieldName");
        State state;
        if ("true".equals(this.conf.get("isBase64"))) {
            state = Base64Uploader.save(this.request.getParameter(filedName), this.conf);
            state.putInfo("md5", UUIDGenerator.generate());
        } else {
            state = UEBinaryUploader.save(this.request, this.conf);
        }
        return state;
    }

}
