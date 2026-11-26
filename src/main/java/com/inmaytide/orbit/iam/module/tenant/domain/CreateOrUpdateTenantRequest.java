package com.inmaytide.orbit.iam.module.tenant.domain;

import com.inmaytide.orbit.framework.core.domain.group.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.Instant;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Data
@Schema(name = "创建/修改租户请求实体")
public class CreateOrUpdateTenantRequest {

    @Schema(description = "主键标识")
    @NotBlank(groups = Update.class)
    private String id;

    @NotBlank(groups = {Default.class, Update.class})
    @Schema(description = "租户编码")
    private String code;

    @NotBlank(groups = {Default.class, Update.class})
    @Schema(description = "租户名称")
    private String name;

    @Schema(description = "状态, 1启用/0禁用")
    private Integer status;

    @Schema(description = "部署模式: SAAS/PRIVATE")
    private String deploymentMode;

    @Schema(description = "版本")
    private String edition;

    @Schema(description = "授权到期时间")
    private Instant expiredAt;

    @Min(value = 0, groups = {Default.class, Update.class})
    @Schema(description = "用户数上限, 空表示不限")
    private Integer maxUsers;

    @Min(value = 0, groups = {Default.class, Update.class})
    @Schema(description = "在线数上限, 空表示不限")
    private Integer maxOnlineUsers;

    @Schema(description = "联系人")
    private String contactName;

    @Schema(description = "联系电话")
    private String contactPhone;

    @Schema(description = "联系邮箱")
    private String contactEmail;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "备注")
    private String description;

}
