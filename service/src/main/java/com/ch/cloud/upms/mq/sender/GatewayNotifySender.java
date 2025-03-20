package com.ch.cloud.upms.mq.sender;

import com.alibaba.fastjson.JSON;
import com.ch.pojo.KeyValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * desc:
 *
 * @author zhimin
 * @since 2022/5/25 08:46
 */
@Component
@Slf4j
public class GatewayNotifySender {

    @Value("${rocketmq.enabled:false}")
    private Boolean mqOn;

    public static final String MQ_GATEWAY_CLEAN_TOPIC = "gateway-clean";

    @Autowired(required = false)
    private RocketMQTemplate rocketMQTemplate;

    public void cleanNotify(KeyValue keyValue) {
        String message = JSON.toJSONString(keyValue);
        log.debug("cleanNotify:{}, mq status: {}", message, mqOn);
        if (!mqOn) return;
        rocketMQTemplate.convertAndSend(MQ_GATEWAY_CLEAN_TOPIC, message);
    }

}
