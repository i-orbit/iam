package com.inmaytide.orbit.iam.module.user.domain;

import com.inmaytide.orbit.framework.core.domain.group.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * @author inmaytide
 * @since 2026/7/7
 */
@Data
@Schema(description = "创建/修改用户信息请求实体")
public class CreateOrUpdateUserRequest {

    @Schema(description = "主键标识")
    @NotBlank(groups = Update.class)
    private String id;

    @Schema(description = "所属租户")
    private String tenantId;

    @Schema(description = "员工编码")
    private String employeeNumber;

    @NotBlank
    @Schema(description = "登录账户名")
    private String username;

    @NotBlank
    @Schema(description = "用户姓名")
    private String name;

    @Schema(description = "头像图片存储地址")
    private String avatar;

    @Schema(description = "性别, 引用数据字典ID")
    private String gender;

    @Schema(description = "生日")
    private Instant birthday;

    @Schema(description = "邮箱地址")
    private String email;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "用户状态")
    private Integer status;

    @Schema(description = "备注说明")
    private String remark;

    @Schema(description = "入职日期")
    private Instant hiredAt;

    @Schema(description = "转正日期")
    private Instant regularizedAt;

    @Schema(description = "离职日期")
    private Instant terminatedAt;

    @Schema(description = "人事状态, 引用数据字典ID")
    private String employmentStatus;

    @Schema(description = "默认组织")
    private String defaultOrganizationId;

    @Schema(description = "默认岗位")
    private String defaultPositionId;

    @Schema(description = "所属组织")
    private List<String> organizationIds;

    @Schema(description = "所属角色")
    private List<String> roleIds;

    @Schema(description = "所属岗位")
    private List<String> positionIds;

}
