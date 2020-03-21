package com.ch.cloud.upms.service;

import com.ch.cloud.upms.model.OPRecord;
import com.ch.mybatis.service.IService;
import com.github.pagehelper.PageInfo;

public interface IOPRecordService extends IService<Long, OPRecord> {

    PageInfo<OPRecord> findPageBy(OPRecord record, int pageNum, int pageSize);
}
