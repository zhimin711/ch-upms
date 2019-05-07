package com.ch.cloud.upms.service;

import com.ch.cloud.upms.model.BtUploadFile;
import com.ch.mybatis.service.IService;

import java.util.List;

/**
 * @author 01370603
 * @date 2018/12/24 18:57
 */
public interface IUploadFileService extends IService<Long, BtUploadFile> {

    List<BtUploadFile> findImageByUsername(String username);

    BtUploadFile findByFileName(String uid);

    int remove(String uid, String username);
}
