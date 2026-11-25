package com.inmaytide.orbit.iam.module.tenant.domain;

import com.inmaytide.orbit.framework.core.domain.dto.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantOrderQuery extends PageQuery {

    private String tenantId;

    private String orderNo;

    private String contractNo;

    private String status;

    private String orderType;

}
