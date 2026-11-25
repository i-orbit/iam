package com.inmaytide.orbit.iam.module.role.domain;

import com.inmaytide.orbit.framework.core.domain.dto.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleQuery extends PageQuery {

    private Integer enableStatus;

    private String tenantId;

}
