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
@Schema(name = "租户订单参数")
public class TenantOrderQuery extends PageQuery {

    @Schema(description = "关联租户ID")
    private String tenantId;

    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "合同编号")
    private String contractNo;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "订单类型")
    private String orderType;

}
