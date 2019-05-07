package com.ch.cloud.upms.aop;

import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.utils.BeanExtUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import com.google.common.collect.Maps;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * 创建与更新人和时间切面
 *
 * @author 01370603
 */
@Aspect
@Order(3)
@Component
public class OPAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(public * com.ch.cloud..*Service.save*(*)) "
            + "|| execution(public * com.ch.cloud..*Service.batchSave*(*)) "
            + "|| execution(public * com.ch.mybatis.service.*Service.save*(*)) "
            + "|| execution(public * com.ch.mybatis.service.*Service.batchSave*(*)) "
    )
    public void doSave() {
    }

    @Before("doSave()")
    public void doBeforeSave(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length == 1) {
            String username = RequestUtils.getHeaderUser();
            Object o = args[0];
            logger.info("{} doBeforeSave => {}", username, o);
            Map<String, String> map = Maps.newHashMap();
            map.put("createBy", username);
            String createAt = DateUtils.format(DateUtils.currentTime());
            map.put("createAt", createAt);
            if (o instanceof Collection) {
                for (Object obj : (Collection) o) {
                    setFieldValue(obj, map);
                }
            } else {
                setFieldValue(o, map);
            }
        }

    }

    @Pointcut("execution(public * com.ch.cloud..*Service.update*(*)) "
            + "|| execution(public * com.ch.cloud..*Service.batchUpdate*(*)) "
            + "|| execution(public * com.ch.mybatis.service.*Service.update*(*)) "
            + "|| execution(public * com.ch.mybatis.service.*Service.batchUpdate*(*)) "
    )
    public void doUpdate() {
    }

    @Before("doUpdate()")
    public void doBeforeUpdate(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length == 1) {
            Object o = args[0];
            String username = RequestUtils.getHeaderUser();
            logger.info("{} doBeforeUpdate => {}", username, o);
            Map<String, String> map = Maps.newHashMap();
            map.put("updateBy", username);
            String updateAt = DateUtils.format(DateUtils.currentTime());
            map.put("updateAt", updateAt);
            if (o instanceof Collection) {
                for (Object obj : (Collection) o) {
                    setFieldValue(obj, map);
                }
            } else {
                setFieldValue(o, map);
            }
        }
    }

    @Pointcut("execution(public * com.ch.cloud..*Service.delete*(*)) "
            + "|| execution(public * com.ch.cloud..*Service.batchDelete*(*)) "
            + "|| execution(public * com.ch.mybatis.service.*Service.delete*(*)) "
            + "|| execution(public * com.ch.mybatis.service.*Service.batchDelete*(*)) "
    )
    public void doDelete() {
    }

    @Before("doDelete()")
    public void doBeforeDelete(JoinPoint joinPoint) {
        logger.info("doBeforeDelete => {}", joinPoint.getArgs()[0]);
    }

    private void setFieldValue(Object o, Map<String, String> valueMap) {
//        Map<String, String> propMap = CommonUtils.getFieldValueMap(o);
        Map<String, String> map = Maps.newHashMap();
        if (o != null && valueMap != null && !valueMap.isEmpty()) {
            valueMap.forEach((k, v) -> {
                if (CommonUtils.isNotEmpty(v)) {
                    map.put(k, v);
                }
            });
        }
        if (o != null && !map.isEmpty()) {
            BeanExtUtils.setFieldValue(o, map);
        }
    }
}
