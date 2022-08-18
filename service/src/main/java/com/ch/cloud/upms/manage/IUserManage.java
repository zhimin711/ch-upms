package com.ch.cloud.upms.manage;

import com.ch.cloud.upms.dto.UserDto;

/**
 * <p>
 * desc: IUserManage
 * </p>
 *
 * @author Zhimin.Ma
 * @since 2022/8/18
 */
public interface IUserManage {
    
    UserDto getById(Long id);
    
    UserDto getByUserId(String userId);
    
    UserDto getByUsername(String username);
}
