package com.ch.cloud.upms.user.vo;

import lombok.Data;

/**
 * desc:
 *
 * @author zhimin
 * @since 2022/5/30 21:27
 */
@Data
public class TenantChangeVO {

    private Long id;

    private boolean setDefault = false;
}
