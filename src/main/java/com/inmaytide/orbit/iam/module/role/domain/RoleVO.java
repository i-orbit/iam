package com.inmaytide.orbit.iam.module.role.domain;

import com.inmaytide.orbit.framework.core.enums.EnableStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Data
public class RoleVO {

    private String id;

    private String tenantId;

    private String code;

    private String name;

    private String description;

    private EnableStatus status;

    private Integer showOrder;

    private List<String> menuIds = new ArrayList<>();

    private List<String> permissionIds = new ArrayList<>();

    private List<String> areaIds = new ArrayList<>();

    private List<String> organizationIds = new ArrayList<>();

}
