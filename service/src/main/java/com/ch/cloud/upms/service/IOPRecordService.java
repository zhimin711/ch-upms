package com.ch.cloud.upms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.cloud.upms.log.model.OPRecord;

/**
 * <p>
 * 日志表-操作记录 服务类
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-25
 */
public interface IOPRecordService extends IService<OPRecord> {

    Page<OPRecord> page(OPRecord record, int pageNum, int pageSize);
}
