package com.inmaytide.orbit.iam.module.user.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.inmaytide.orbit.framework.core.domain.TombstoneEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

/**
 * @author inmaytide
 * @since 2026/7/7
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user")
public class User extends TombstoneEntity {

    private String tenantId;

    private String employeeNumber;

    private String username;

    private String password;

    private String name;

    private String avatar;

    private String gender;

    private Instant birthday;

    private String email;

    private String phone;

    private Integer status;

    private String remark;

    private Instant hiredAt;

    private Instant regularizedAt;

    private Instant terminatedAt;

    private String employmentStatus;

}
