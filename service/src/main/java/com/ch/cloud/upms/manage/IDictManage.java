package com.ch.cloud.upms.manage;

import com.ch.cloud.upms.model.Dict;

/**
 * <p>
 * desc: IDictManage
 * </p>
 *
 * @author Zhimin.Ma
 * @since 2022/7/28
 */
public interface IDictManage {
    Dict findByCode(String code);
}
