package com.ch.cloud.upms.enums;

import com.ch.utils.CommonUtils;

/**
 * <p>
 * desc: 角色 类型
 * </p>
 *
 * @author zhimin.ma
 * @since 2021/11/11
 */
public enum RoleType {
    MGR, DEV, TEST, OPS, VISITOR;

    public static RoleType fromName(String name) {
        if (CommonUtils.isEmpty(name)) return VISITOR;
        for (RoleType role : values()) {
            if (role.name().equalsIgnoreCase(name)) {
                return role;
            }
        }
        return VISITOR;
    }
    
    public static boolean isVisitor(String role){
        return fromName(role) == VISITOR;
    }
}
