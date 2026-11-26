package com.inmaytide.orbit.iam.module.tenant.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Data
@Schema(name = "修改租户状态请求实体")
public class UpdateTenantStatusRequest {

    @NotNull
    @Min(0)
    @Max(1)
    @Schema(description = "状态, 1启用/0禁用")
    private Integer status;

}
