package com.inmaytide.orbit.iam.module.tenant.domain;

import com.inmaytide.orbit.framework.core.domain.dto.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantQuery extends PageQuery {

    private Integer status;

    private String deploymentMode;

}
