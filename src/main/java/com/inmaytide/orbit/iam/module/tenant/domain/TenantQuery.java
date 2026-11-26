package com.inmaytide.orbit.iam.module.tenant.domain;

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
@Schema(name = "租户查询参数")
public class TenantQuery extends PageQuery {

    @Schema(description = "状态, 1启用/0禁用")
    private Integer status;

    @Schema(description = "部署模式: SAAS/PRIVATE")
    private String deploymentMode;

}
