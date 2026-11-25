package com.inmaytide.orbit.iam.module.tenant.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.inmaytide.orbit.framework.core.domain.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_tenant_module")
public class TenantModule extends AuditableEntity {

    private String tenantId;

    private String moduleCode;

    private Integer enabled;

}
