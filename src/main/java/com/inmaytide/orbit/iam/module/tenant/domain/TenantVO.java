package com.inmaytide.orbit.iam.module.tenant.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Instant;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Data
@Schema(description = "租户信息VO")
public class TenantVO {

    @Schema(description = "主键标识")
    private String id;

    @Schema(description = "租户编码")
    private String code;

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

    @Schema(description = "用户数上限")
    private Integer maxUsers;

    @Schema(description = "在线数上限")
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

    @Schema(description = "创建时间")
    private Instant createdAt;

}
