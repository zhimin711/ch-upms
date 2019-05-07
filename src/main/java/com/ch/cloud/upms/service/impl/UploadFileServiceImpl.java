package com.ch.cloud.upms.service.impl;

import com.ch.Constants;
import com.ch.cloud.upms.mapper.BtUploadFileMapper;
import com.ch.cloud.upms.model.BtUploadFile;
import com.ch.cloud.upms.service.IUploadFileService;
import com.ch.mybatis.service.BaseService;
import com.ch.type.FileType;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author 01370603
 * @date 2018/12/24 18:57
 */
@Service
public class UploadFileServiceImpl extends BaseService<Long, BtUploadFile> implements IUploadFileService {

    @Autowired(required = false)
    private BtUploadFileMapper uploadFileMapper;

    @Override
    protected Mapper<BtUploadFile> getMapper() {
        return uploadFileMapper;
    }

    @Override
    public List<BtUploadFile> findImageByUsername(String username) {
        if (CommonUtils.isEmpty(username)) {
            return Lists.newArrayList();
        }
        BtUploadFile record = new BtUploadFile();
        record.setType(FileType.IMAGE.getCode());
        record.setStatus(Constants.ENABLED);
        record.setCreateBy(username);
        return find(record);
    }

    @Override
    public BtUploadFile findByFileName(String uid) {
        BtUploadFile record = new BtUploadFile();
        record.setStatus(Constants.ENABLED);
        record.setFileName(uid);
        return getMapper().selectOne(record);
    }

    @Override
    public int remove(String uid, String username) {
        if (CommonUtils.isEmpty(uid)) return 0;
        Example example = getExample();
        example.createCriteria().andEqualTo("fileName", uid);
        BtUploadFile v = new BtUploadFile();
        v.setStatus(Constants.THREE);
        v.setUpdateAt(DateUtils.currentTime());
        v.setUpdateBy(username);
        return getMapper().updateByExampleSelective(v, example);
    }
}
