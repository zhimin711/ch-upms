package com.ch.cloud.upms.mq;


import com.alibaba.fastjson.JSONObject;
import com.ch.Constants;
import com.ch.Separator;
import com.ch.cloud.upms.model.OPRecord;
import com.ch.cloud.upms.model.Permission;
import com.ch.cloud.upms.service.IOPRecordService;
import com.ch.cloud.upms.service.IPermissionService;
import com.ch.utils.CommonUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.List;

@Service
@RocketMQMessageListener(consumerGroup = "ch-upms", topic = "request-logs")
@Log4j2
public class RequestLogsConsumer implements RocketMQListener<String> {

    private final static String REQUEST_PROCESS_SEPARATOR = "\n\\[REQUEST_PROCESS_SEPARATOR\\]\n";

    public static final String LOGIN_ACCESS = "/auth/login/token/access";
    public static final String LOGIN_USER = "/auth/login/token/user";
    public static final String LOGIN_REFRESH = "/auth/login/token/refresh";

    @Resource
    private IPermissionService permissionService;

    @Resource
    private IOPRecordService opRecordService;

    @Override
    public void onMessage(String message) {
//        log.info("接收到消息：\n{}", message);
        try {
            OPRecord record = parseRecord(message);
            opRecordService.save(record);
        } catch (Exception e) {
            log.error("parse log error!~", e);
        }
    }

    public OPRecord parseRecord(String message) {
        OPRecord record = new OPRecord();
        String[] infos = message.split(REQUEST_PROCESS_SEPARATOR);
        for (String info : infos) {
            JSONObject object = JSONObject.parseObject(info);
            if (object.containsKey("request")) {
                record.setRequest(object.getJSONObject("request").toJSONString());
                record.setRequestIp(getIP(object.getJSONObject("request")));
            } else if (object.containsKey("proxy")) {
                record.setProxy(object.getJSONObject("proxy").toJSONString());
            } else if (object.containsKey("response")) {
                record.setResponse(object.getJSONObject("response").toJSONString());

                String status = object.getJSONObject("response").getString("status");
                record.setStatus(CommonUtils.isNumeric(status) ? Integer.valueOf(status) : null);
                parseError(object.getJSONObject("response"), record);
            } else if (object.containsKey("record")) {
                copyProperties(object.getJSONObject("record"), record);
            }
        }
        if (CommonUtils.isEquals(record.getUrl(), LOGIN_ACCESS)) {
            record.setAuthCode("LOGIN_ACCESS");
        } else if (CommonUtils.isEquals(record.getUrl(), LOGIN_USER)) {
            record.setAuthCode("LOGIN_USER");
        } else if (CommonUtils.isEquals(record.getUrl(), LOGIN_REFRESH)) {
            record.setAuthCode("LOGIN_REFRESH");
        }

        return record;
    }

    private void parseError(JSONObject source, OPRecord target) {
        if (!source.containsKey("data")) {
            return;
        }
        try {
            JSONObject data = source.getJSONObject("data");
            if (data.containsKey("success") && data.getBoolean("success")) {
                if (data.containsKey("code")) {
                    target.setErrorCode(data.getString("code"));
                }
                if (data.containsKey("message")) {
                    target.setErrorMessage(data.getString("message"));
                }
            }
        } catch (Exception e) {
            log.warn("parse response data json object error!", e);
        }
    }

    private void copyProperties(JSONObject source, OPRecord target) {
        String url = source.getString("url");
        String method = source.getString("method");
        String username = source.getString("username");
        String startTimestamp = source.getString("startTimestamp");
        String endTimestamp = source.getString("endTimestamp");

        target.setUrl(url);
        target.setMethod(method);
        target.setOperator(username);

        target.setRequestTime(Long.valueOf(startTimestamp));
        target.setResponseTime(Long.valueOf(endTimestamp));

        String[] urls = url.split(Separator.OBLIQUE_LINE);
        List<Permission> permissions = permissionService.match(Separator.OBLIQUE_LINE + urls[1], null);
        if (permissions.isEmpty()) {
            return;
        }
        AntPathMatcher pathMatcher = new AntPathMatcher(Separator.OBLIQUE_LINE);
        for (Permission r : permissions) {
            if (pathMatcher.match(r.getUrl(), url)) {
                if (CommonUtils.isNotEmpty(r.getMethod()) && CommonUtils.isEquals(r.getMethod(), method) || CommonUtils.isEmpty(r.getMethod())) {
                    target.setAuthCode(r.getCode());
                    break;
                }
            }
        }
    }


    public static String getIP(JSONObject request) {
        if (!request.containsKey("headers")) return "N/A";
        JSONObject headers = request.getJSONObject("headers");
        String ip = headers.getString("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getString("X-Original-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getString("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getString("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getString("X-Real-IP");
        }
        /*if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }*/
        return ip;
    }

}
