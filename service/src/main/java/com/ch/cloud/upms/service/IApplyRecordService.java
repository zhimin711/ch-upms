package com.ch.cloud.upms.service;

import com.ch.cloud.upms.model.ApplyRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 业务-申请记录表 服务类
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-11-12
 */
public interface IApplyRecordService extends IService<ApplyRecord> {

    Boolean approveNacos(ApplyRecord record);
}
