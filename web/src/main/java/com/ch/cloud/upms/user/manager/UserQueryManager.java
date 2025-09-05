package com.ch.cloud.upms.user.manager;

import com.ch.cloud.upms.user.pojo.UserInfo;

import java.util.List;

/**
 * <p>
 * desc: UserQueryManager
 * </p>
 *
 * @author zhimin
 * @since 2025/6/25 10:47
 */
public interface UserQueryManager {
    
    List<UserInfo> findAllValid(String userIdOrUsernameOrRealName);
}
