package com.inmaytide.orbit.iam.module.organization.domain;

import com.inmaytide.orbit.framework.core.domain.TombstoneEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author inmaytide
 * @since 2026/7/7
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Organization extends TombstoneEntity {

    private String tenantId;        // 所属租户

    private String parentId;        // 上级组织

    private String code;            // 组织编码

    private String name;            // 组织名称

    private String shortName;       // 组织简称

    private Integer type;            // 组织类型

    private Integer status;          // 状态

    private String cover;            // 鸟瞰图存储地址

    private String address;          // 组织地址

    private String description;      // 组织介绍

    private String path;             // 树节点路径

    private String showOrder;        // 排序字段

}
