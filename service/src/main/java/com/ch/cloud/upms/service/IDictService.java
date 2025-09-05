package com.ch.cloud.upms.service;

import com.ch.Status;
import com.ch.cloud.upms.base.model.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 数据字典表 服务类
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-07-04
 */
public interface IDictService extends IService<Dict> {

    Dict findByCode(String code);

    List<Dict> findByPidAndName(Long id, String name, Status status);
}
