package com.inmaytide.orbit.iam.module.tenant.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.inmaytide.orbit.framework.core.domain.TombstoneEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_tenant")
public class Tenant extends TombstoneEntity {

    private String code;

    private String name;

    private Integer status;

    private String deploymentMode;

    private String edition;

    private Instant expiredAt;

    private Integer maxUsers;

    private Integer maxOnlineUsers;

    private String contactName;

    private String contactPhone;

    private String contactEmail;

    private String address;

    private String description;

}
