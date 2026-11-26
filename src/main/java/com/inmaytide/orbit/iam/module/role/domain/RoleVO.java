package com.inmaytide.orbit.iam.module.role.domain;

import com.inmaytide.orbit.framework.core.enums.EnableStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Data
@Schema(name = "角色信息VO")
public class RoleVO {

    @Schema(description = "主键标识")
    private String id;

    @Schema(description = "所属租户ID")
    private String tenantId;

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色描述")
    private String description;

    @Schema(description = "启用状态")
    private EnableStatus status;

    @Schema(description = "排序字段")
    private Integer showOrder;

    @Schema(description = "关联菜单ID列表")
    private List<String> menuIds = new ArrayList<>();

    @Schema(description = "关联权限ID列表")
    private List<String> permissionIds = new ArrayList<>();

    @Schema(description = "关联区域ID列表")
    private List<String> areaIds = new ArrayList<>();

    @Schema(description = "关联组织ID列表")
    private List<String> organizationIds = new ArrayList<>();

}
