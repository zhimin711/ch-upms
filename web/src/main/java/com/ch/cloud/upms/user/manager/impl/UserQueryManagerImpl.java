package com.ch.cloud.upms.user.manager.impl;

import com.ch.cloud.upms.service.IUserService;
import com.ch.cloud.upms.user.manager.UserQueryManager;
import com.ch.cloud.upms.user.model.User;
import com.ch.cloud.upms.user.pojo.UserInfo;
import com.ch.utils.CharUtils;
import com.ch.utils.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * desc: UserQueryManagerImpl
 * </p>
 *
 * @author zhimin
 * @since 2025/6/25 10:47
 */
@Service
public class UserQueryManagerImpl implements UserQueryManager {
    
    @Autowired
    private IUserService userService;
    
    @Override
    public List<UserInfo> findAllValid(String userIdOrUsernameOrRealName){
        
        List<User> users;
        if (CommonUtils.isEmpty(userIdOrUsernameOrRealName)) {
            users = userService.findAllValid();
        } else if (CommonUtils.isNumeric(userIdOrUsernameOrRealName)) {
            users = userService.findByLikeUserId(userIdOrUsernameOrRealName);
        } else if (CharUtils.containsChinese(userIdOrUsernameOrRealName)) {
            users = userService.findByLikeRealName(userIdOrUsernameOrRealName);
        } else {
            users = userService.findByLikeUsername(userIdOrUsernameOrRealName);
        }
        return users.stream().map(r -> {
            UserInfo info = new UserInfo();
            BeanUtils.copyProperties(r, info);
            return info;
        }).collect(Collectors.toList());
    }
}
