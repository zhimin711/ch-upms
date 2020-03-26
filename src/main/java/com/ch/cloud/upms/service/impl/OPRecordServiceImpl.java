package com.ch.cloud.upms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.cloud.upms.mapper.OPRecordMapper;
import com.ch.cloud.upms.model.OPRecord;
import com.ch.cloud.upms.model.User;
import com.ch.cloud.upms.service.IOPRecordService;
import com.ch.utils.CommonUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志表-操作记录 服务实现类
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-25
 */
@Service
public class OPRecordServiceImpl extends ServiceImpl<OPRecordMapper, OPRecord> implements IOPRecordService {

    @Override
    public Page<OPRecord> page(OPRecord record, int pageNum, int pageSize) {
        return super.query()
                .likeRight(CommonUtils.isNotEmpty(record.getAuthCode()), "auth_code", record.getAuthCode())
                .notLike(CommonUtils.isEmpty(record.getAuthCode()), "auth_code", "LOGIN_")
                .likeRight(CommonUtils.isNotEmpty(record.getUrl()), "url", record.getUrl())
                .eq(CommonUtils.isNotEmpty(record.getStatus()), "status", record.getStatus())
                .orderByDesc("request_time").page(new Page<>(pageNum, pageSize));
    }
}
