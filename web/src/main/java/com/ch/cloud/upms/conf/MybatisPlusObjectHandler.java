package com.ch.cloud.upms.conf;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * desc:  自动补充插入或更新时的值
 *
 * @author zhimin
 * @since 2022/3/11 12:13 AM
 */
@Component
public class MybatisPlusObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (CommonUtils.isEmpty(metaObject.getValue("createBy"))) {
            this.setFieldValByName("createBy", RequestUtils.getHeaderUser(), metaObject);
        }
        if (CommonUtils.isEmpty(metaObject.getValue("updateBy"))) {
            this.setFieldValByName("updateBy", RequestUtils.getHeaderUser(), metaObject);
        }

        this.setFieldValByName("createAt", DateUtils.current(), metaObject);
        this.setFieldValByName("updateAt", DateUtils.current(), metaObject);

        this.setFieldValByName("deleted", Boolean.FALSE, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (CommonUtils.isEmpty(metaObject.getValue("updateBy"))) {
            this.setFieldValByName("updateBy", RequestUtils.getHeaderUser(), metaObject);
        }
        this.setFieldValByName("updateAt", DateUtils.current(), metaObject);
    }
}
