package com.inmaytide.orbit.iam.module.role.domain;

import com.inmaytide.orbit.framework.core.domain.dto.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "角色查询参数")
public class RoleQuery extends PageQuery {

    @Schema(description = "启用状态")
    private Integer enableStatus;

    @Schema(description = "所属租户ID")
    private String tenantId;

}
