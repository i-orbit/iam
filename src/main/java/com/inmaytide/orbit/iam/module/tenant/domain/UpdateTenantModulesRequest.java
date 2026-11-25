package com.inmaytide.orbit.iam.module.tenant.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Data
@Schema(description = "设置租户模块请求实体")
public class UpdateTenantModulesRequest {

    @NotNull
    @Schema(description = "已开通模块编码列表")
    private List<String> modules;

}
