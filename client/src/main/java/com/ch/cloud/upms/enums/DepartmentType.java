package com.ch.cloud.upms.enums;

/**
 * <p>
 * desc: DepartmentType
 * </p>
 *
 * @author Zhimin.Ma
 * @since 2022/7/26
 */
public enum DepartmentType {
    ORG(0,"集团"),
    COMPANY(1,"公司"),
    DEPARTMENT(2,"部门"),
    TEAM(3,"团队")
    ;

    private final int code;

    private final String name;

    DepartmentType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static boolean isTeam(Integer code) {
        if(code == null) return false;
        return code.equals(TEAM.code);
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
