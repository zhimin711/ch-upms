package com.ch.cloud.upms.manage;

import com.ch.cloud.upms.user.model.Department;

/**
 * <p>
 * desc: IDepartmentManage
 * </p>
 *
 * @author Zhimin.Ma
 * @since 2022/7/28
 */
public interface IDepartmentManage {

    Department get(Long id);

    boolean update(Department record);

    boolean remove(Long id);
}
