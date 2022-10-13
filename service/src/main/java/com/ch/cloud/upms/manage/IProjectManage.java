package com.ch.cloud.upms.manage;

import com.ch.cloud.upms.model.Project;

/**
 * <p>
 * desc: IProjectManage
 * </p>
 *
 * @author Zhimin.Ma
 * @since 2022/10/13
 */
public interface IProjectManage {
    
    Project getById(Long id);
}
