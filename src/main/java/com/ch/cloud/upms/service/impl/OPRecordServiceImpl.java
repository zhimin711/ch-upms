package com.ch.cloud.upms.service.impl;

import com.ch.cloud.upms.mapper.OPRecordMapper;
import com.ch.cloud.upms.model.OPRecord;
import com.ch.cloud.upms.service.IOPRecordService;
import com.ch.mybatis.service.BaseService;
import com.ch.mybatis.utils.ExampleUtils;
import com.ch.utils.SQLUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OPRecordServiceImpl extends BaseService<Long, OPRecord> implements IOPRecordService {

    @Resource
    private OPRecordMapper opRecordMapper;

    @Override
    protected Mapper<OPRecord> getMapper() {
        return opRecordMapper;
    }

    @Override
    public PageInfo<OPRecord> findPageBy(OPRecord record, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Sqls sqls = Sqls.custom();
        if ("login".equals(record.getAuthCode())) {
            sqls.andLike("url", SQLUtils.likeSuffix("/auth")).andIsNull("authCode");
            record.setAuthCode(null);
        } else {
            sqls.andIsNotNull("authCode");
        }
        ExampleUtils.dynEqual(sqls, record);
        Example ex = Example.builder(OPRecord.class).where(sqls).orderByDesc("requestTime").build();
        List<OPRecord> records = getMapper().selectByExample(ex);
        return new PageInfo<>(records);
    }
}
