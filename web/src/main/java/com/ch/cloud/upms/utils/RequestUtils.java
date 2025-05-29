package com.ch.cloud.upms.utils;

import com.ch.Constants;
import com.ch.e.Assert;
import com.ch.e.PubError;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Http请求工具类
 *
 * @author 01370603
 */
public class RequestUtils {
    
    private RequestUtils() {
    }
    
    public static HttpServletRequest get() {
        RequestAttributes reqAttr = RequestContextHolder.getRequestAttributes();
        Assert.notNull(reqAttr, PubError.NOT_EXISTS, "http request not found!");
        return ((ServletRequestAttributes) reqAttr).getRequest();
    }
    
    public static String getHeaderUser() {
        return get().getHeader(Constants.X_TOKEN_USER);
    }
    
    public static String getHeaderUser(HttpServletRequest request) {
        return request.getHeader(Constants.X_TOKEN_USER);
    }
    
}
