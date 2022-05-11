package com.ch.cloud.upms.pojo;

import lombok.Data;

/**
 * <p>
 * desc:
 * </p>
 *
 * @author zhimin.ma
 * @since 2021/10/9
 */
@Data
public class UserProjectDto {

    private String userId;
    private Long   projectId;
    private String role;
}
