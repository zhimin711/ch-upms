package com.ch.cloud.upms.manage.impl;

import com.ch.cloud.upms.dto.UserDto;
import com.ch.cloud.upms.manage.IUserManage;
import com.ch.cloud.upms.model.User;
import com.ch.cloud.upms.service.IUserService;
import com.ch.utils.BeanUtilsV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * <p>
 * desc: UserManageImpl
 * </p>
 *
 * @author Zhimin.Ma
 * @since 2022/8/18
 */
@Service
@Cacheable(cacheNames = "user")
public class UserManageImpl implements IUserManage {
    
    @Autowired
    private IUserService userService;
    
    @Override
    public UserDto getById(Long id) {
        User user = userService.getById(id);
        return BeanUtilsV2.clone(user, UserDto.class);
    }
    
    @Override
    public UserDto getByUserId(String userId) {
        User user = userService.getByUserId(userId);
        return BeanUtilsV2.clone(user, UserDto.class);
    }
    
    
//    @Cacheable
    @Override
    public UserDto getByUsername(String username) {
        User user = userService.getDefaultInfo(username);
        return BeanUtilsV2.clone(user, UserDto.class);
    }
}
