package com.inmaytide.orbit.iam.module.role.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.inmaytide.orbit.framework.core.domain.TombstoneEntity;
import com.inmaytide.orbit.framework.core.enums.EnableStatus;
import lombok.Data;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Data
@TableName("t_role")
public class Role extends TombstoneEntity {

    private String tenantId;

    private String code;

    private String name;

    private String description;

    private EnableStatus status;

    private Integer showOrder;

}
