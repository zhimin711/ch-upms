package com.ch.cloud.upms.mq;


import com.alibaba.fastjson.JSONObject;
import com.ch.Constants;
import com.ch.cloud.upms.model.OPRecord;
import com.ch.cloud.upms.model.StPermission;
import com.ch.cloud.upms.service.IOPRecordService;
import com.ch.cloud.upms.service.IPermissionService;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import jdk.nashorn.internal.ir.annotations.Reference;
import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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
            String[] infos = message.split(REQUEST_PROCESS_SEPARATOR);
            OPRecord record = new OPRecord();
            for (String info : infos) {
                JSONObject object = JSONObject.parseObject(info);
                if (object.containsKey("request")) {
                    record.setRequest(object.getJSONObject("request").toJSONString());
                } else if (object.containsKey("proxy")) {
                    record.setProxy(object.getJSONObject("proxy").toJSONString());
                } else if (object.containsKey("response")) {
                    record.setResponse(object.getJSONObject("response").toJSONString());

                    String status = object.getJSONObject("response").getString("status");
                    record.setStatus(CommonUtils.isNumeric(status) ? Integer.valueOf(status) : null);
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
            opRecordService.save(record);
        } catch (Exception e) {
            log.error("parse log error!~", e);
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

        String[] urls = url.split(Constants.SEPARATOR_3);
        List<StPermission> permissions = permissionService.match(Constants.SEPARATOR_3 + urls[1], null);
        if (permissions.isEmpty()) {
            return;
        }
        AntPathMatcher pathMatcher = new AntPathMatcher(Constants.SEPARATOR_3);
        for (StPermission r : permissions) {
            if (pathMatcher.match(r.getUrl(), url)) {
                if (CommonUtils.isNotEmpty(r.getMethod()) && CommonUtils.isEquals(r.getMethod(), method) || CommonUtils.isEmpty(r.getMethod())) {
                    target.setAuthCode(r.getCode());
                    break;
                }
            }
        }
    }
}