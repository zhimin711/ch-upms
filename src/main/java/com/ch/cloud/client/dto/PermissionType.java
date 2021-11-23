package com.ch.cloud.client.dto;

/**
 * <p>
 * desc: 1.目录 2.菜单 3.按钮 5.接口 2#0.隐藏菜单
 * </p>
 *
 * @author zhimin.ma
 * @since 2021/11/22
 */
public enum PermissionType {
    CATALOG("1"), MENU("2"), MENU_HIDE("2#0"), BUTTON("3"), INTERFACE("5");

    private final String code;

    PermissionType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static PermissionType from(String code) {
        return from(code, null);
    }

    public static PermissionType from(String code, Boolean hidden) {
        PermissionType t = PermissionType.BUTTON;
        if (code == null || code.trim().length() == 0) {
            return t;
        }
        PermissionType[] arr = PermissionType.values();
        for (PermissionType type : arr) {
            if (type.code.equals(code)) {
                t = type;
                break;
            }
        }
        if (t == MENU && hidden != null && hidden) {
            t = PermissionType.MENU_HIDE;
        }
        return t;
    }
}
