package com.ch.cloud.upms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ch.cloud.upms.mapper2.UserProjectNamespaceMapper;
import com.ch.cloud.upms.model.ApplyRecord;
import com.ch.cloud.upms.mapper.ApplyRecordMapper;
import com.ch.cloud.upms.service.IApplyRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.s.ApproveStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 业务-申请记录表 服务实现类
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-11-12
 */
@Service
public class ApplyRecordServiceImpl extends ServiceImpl<ApplyRecordMapper, ApplyRecord> implements IApplyRecordService {

    @Resource
    private UserProjectNamespaceMapper userProjectNamespaceMapper;

    @Override
    public Boolean approveNacos(ApplyRecord record) {
        if (ApproveStatus.fromValue(record.getStatus()) != ApproveStatus.SUCCESS) {
            return super.updateById(record);
        }
        JSONObject object = JSONObject.parseObject(record.getContent());
        String userId = object.getString("userId");
        Long projectId = object.getLong("projectId");
        JSONArray array = object.getJSONArray("namespaceIds");
        array.forEach(e -> userProjectNamespaceMapper.insert(projectId, userId, e.toString()));
        return super.updateById(record);
    }
}
