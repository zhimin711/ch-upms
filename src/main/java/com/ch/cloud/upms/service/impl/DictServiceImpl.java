package com.ch.cloud.upms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.Status;
import com.ch.cloud.upms.mapper.DictMapper;
import com.ch.cloud.upms.model.Dict;
import com.ch.cloud.upms.service.IDictService;
import com.ch.utils.CommonUtils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-07-04
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

    @Override
    public Dict findByCode(String code) {
        return super.lambdaQuery().eq(Dict::getCode, code).one();
    }

    @Override
    public List<Dict> findByPid(Long pid, Status status) {
        return super.lambdaQuery().eq(Dict::getPid, pid).eq(status != Status.UNKNOWN, Dict::getStatus, status.getCode()).orderByAsc(Dict::getSort).list();
    }

    @Override
    public boolean save(Dict entity) {
        boolean flag = super.save(entity);
        if (flag && CommonUtils.isNotEmpty(entity.getChildren())) {
            entity.getChildren().forEach(r -> {
                r.setPid(entity.getId());
                r.setCreateBy(entity.getCreateBy());
            });
            super.saveBatch(entity.getChildren());
        }
        return flag;
    }

    @Override
    public boolean updateById(Dict entity) {
        boolean flag = super.updateById(entity);
        if (flag && CommonUtils.isNotEmpty(entity.getChildren())) {
            List<Dict> children = this.findByPid(entity.getId(), Status.UNKNOWN);
            List<Long> origIds = children.stream().map(Dict::getId).collect(Collectors.toList());
            List<Long> updateIds = Lists.newArrayList();
            List<Dict> updateChildren = Lists.newArrayList();
            List<Dict> addChildren = Lists.newArrayList();
            entity.getChildren().forEach(r -> {
                r.setPid(entity.getId());
                if (origIds.contains(r.getId())) {
                    r.setUpdateBy(entity.getUpdateBy());
                    updateChildren.add(r);
                    updateIds.add(r.getId());
                } else {
                    r.setCreateBy(entity.getUpdateBy());
                    addChildren.add(r);
                }
            });
            if (CommonUtils.isNotEmpty(updateChildren)) {
                super.updateBatchById(updateChildren);
            }
            if (CommonUtils.isNotEmpty(addChildren)) {
                super.saveBatch(addChildren);
            }
            if (CommonUtils.isNotEmpty(children)) {
                List<Long> delIds = children.stream().filter(r -> !updateIds.contains(r.getId())).map(Dict::getId).collect(Collectors.toList());
                super.removeByIds(delIds);
            }
        } else {
            super.remove(super.lambdaQuery().eq(Dict::getPid, entity.getId()));
        }
        return flag;
    }
}
