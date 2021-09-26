package com.ch.cloud.upms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.model.Tenant;
import com.ch.cloud.upms.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.cloud.upms.pojo.DepartmentDuty;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-25
 */
public interface IUserService extends IService<User> {
    User findByUsername(String username);

    int updatePassword(User user);

    int assignRole(Long id, List<Long> roleIds);

    List<User> findByLikeUserId(String userId);

    List<User> findByLikeRealName(String realName);

    List<User> findByLikeUsername(String username);

    List<User> findAllValid();

    Page<User> page(User record, int pageNum, int pageSize);

    List<DepartmentDuty> findDepartmentDuty(Long id);

    List<Tenant> findTenantsByUsername(String username);
}
