package com.ch.cloud.upms.utils;

import com.ch.Constants;
import com.ch.e.CoreError;
import com.ch.utils.ExceptionUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 01370603
 */
public class RequestUtils {

    private RequestUtils() {
    }

    public static HttpServletRequest get() {
        RequestAttributes reqAttr = RequestContextHolder.getRequestAttributes();
        if (reqAttr == null) throw ExceptionUtils.create(CoreError.NOT_EXISTS, "http request not found!l");
        return ((ServletRequestAttributes) reqAttr).getRequest();
    }

    public static String getHeaderUser() {
        return get().getHeader(Constants.TOKEN_USER);
    }

    public static String getHeaderUser(HttpServletRequest request) {
        return request.getHeader(Constants.TOKEN_USER);
    }

}
