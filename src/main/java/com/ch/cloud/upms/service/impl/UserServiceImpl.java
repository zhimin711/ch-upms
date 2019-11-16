package com.ch.cloud.upms.service.impl;

import com.ch.StatusS;
import com.ch.cloud.upms.mapper.StUserMapper;
import com.ch.cloud.upms.model.StRole;
import com.ch.cloud.upms.model.StUser;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.service.IUserService;
import com.ch.mybatis.service.BaseService;
import com.ch.mybatis.utils.ExampleUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import com.ch.utils.EncryptUtils;
import com.ch.utils.SQLUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 01370603
 * @date 2018/12/21 18:06
 */
@Service
public class UserServiceImpl extends BaseService<Long, StUser> implements IUserService {

    @Resource
    private StUserMapper userMapper;

    @Override
    protected Mapper<StUser> getMapper() {
        return userMapper;
    }

    @Autowired
    private IRoleService roleService;

    @Override
    public StUser findByUsername(String username) {
        if (CommonUtils.isEmpty(username)) return null;
        StUser record = new StUser();
        record.setUsername(username);
        return getMapper().selectOne(record);
    }

    @Override
    public int updatePassword(StUser record) {
        if (record != null && CommonUtils.isNotEmpty(record.getId(), record.getPassword())) {
            StUser r = new StUser();
            r.setId(record.getId());
            r.setPassword(record.getPassword());
            r.setUpdateBy(record.getUpdateBy());
            r.setUpdateAt(DateUtils.current());
            return super.update(r);
        }
        return 0;
    }

    @Override
    public int assignRole(Long id, List<Long> roleIds) {
        List<StRole> roleList = roleService.findByUserId(id);

        List<Long> uRoleIds = roleList.stream().filter(r -> !CommonUtils.isEquals(r.getType(), StatusS.DISABLED)).map(StRole::getId).collect(Collectors.toList());
        List<Long> uRoleIds2 = roleList.stream().filter(r -> CommonUtils.isEquals(r.getType(), StatusS.DISABLED)).map(StRole::getId).collect(Collectors.toList());

        AtomicInteger c = new AtomicInteger();
        if (!roleIds.isEmpty()) {
            roleIds.stream().filter(r -> !uRoleIds.contains(r) && !uRoleIds2.contains(r)).forEach(r -> c.getAndAdd(userMapper.insertAssignRole(id, r)));
            uRoleIds.stream().filter(r -> !roleIds.contains(r) &&  !uRoleIds2.contains(r)).forEach(r -> c.getAndAdd(userMapper.deleteAssignRole(id, r)));
        } else if (!uRoleIds.isEmpty()) {
            uRoleIds.forEach(r -> c.getAndAdd(userMapper.deleteAssignRole(id, r)));
        }
        return c.get();
    }

    @Override
    public List<StUser> findByLikeUserId(String userId) {
        Example e = Example.builder(StUser.class).andWhere(Sqls.custom().andLike("userId", SQLUtils.likeAny(userId))).build();
        return getMapper().selectByExample(e);
    }

    @Override
    public List<StUser> findByLikeRealname(String realname) {
        Example e = Example.builder(StUser.class).andWhere(Sqls.custom().andLike("realName", SQLUtils.likeAny(realname))
                .andEqualTo("status", StatusS.ENABLED)).build();
        return getMapper().selectByExample(e);
    }

    @Override
    public List<StUser> findByLikeUsername(String username) {
        Example e = Example.builder(StUser.class).andWhere(Sqls.custom().andLike("username", SQLUtils.likeAny(username))
                .andEqualTo("status", StatusS.ENABLED)).build();
        return getMapper().selectByExample(e);
    }

    @Override
    public List<StUser> findAllValid() {
        Example e = Example.builder(StUser.class).andWhere(Sqls.custom().andEqualTo("status", StatusS.ENABLED)).build();
        return getMapper().selectByExample(e);
    }

    @Override
    public int save(StUser record) {
        if (record != null) {
            if (CommonUtils.isEmpty(record.getPassword())) {
                record.setPassword(EncryptUtils.generate());
            }
//            record.setPassword(passwordService.encryptPassword(record.getPassword()));
            record.setUserId(RandomStringUtils.randomNumeric(10));
        }
        return super.save(record);
    }

    @Override
    public int update(StUser record) {
        record.setUserId(null);
        record.setUsername(null);
        //不更新密码
        record.setPassword(null);
        return super.update(record);
    }

    @Override
    public PageInfo<StUser> findPage(StUser record, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example e = getExample();
        ExampleUtils.dynCond(e.createCriteria(), record);
        e.orderBy("userId").asc();
        List<StUser> records = getMapper().selectByExample(e);
        return new PageInfo<>(records);
    }
}
