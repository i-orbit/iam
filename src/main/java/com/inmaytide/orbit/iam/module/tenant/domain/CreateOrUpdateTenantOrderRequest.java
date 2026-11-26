package com.inmaytide.orbit.iam.module.tenant.domain;

import com.inmaytide.orbit.framework.core.domain.group.Update;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Data
@Schema(name = "创建/修改租户订单请求实体")
public class CreateOrUpdateTenantOrderRequest {

    @Schema(description = "主键标识")
    @NotBlank(groups = Update.class)
    private String id;

    @NotBlank(groups = {Default.class, Update.class})
    @Schema(description = "租户ID")
    private String tenantId;

    @NotBlank(groups = {Default.class, Update.class})
    @Schema(description = "订单编号")
    private String orderNo;

    @Schema(description = "采购合同编号")
    private String contractNo;

    @Schema(description = "采购合同名称")
    private String contractName;

    @DecimalMin(value = "0", groups = {Default.class, Update.class})
    @Schema(description = "合同金额")
    private BigDecimal contractAmount;

    @Schema(description = "币种, 默认CNY")
    private String currency;

    @Schema(description = "订单类型(NEW/RENEW/UPGRADE)")
    private String orderType;

    @Schema(description = "订单状态(PENDING/PAID/CANCELLED/REFUNDED)")
    private String status;

    @Schema(description = "合同签订时间")
    private Instant signedAt;

    @Schema(description = "服务开始时间")
    private Instant startAt;

    @Schema(description = "服务结束时间")
    private Instant endAt;

    @Schema(description = "支付时间")
    private Instant paidAt;

    @Schema(description = "支付方式")
    private String paymentMethod;

    @Schema(description = "合同附件地址")
    private String contractUrl;

    @Schema(description = "备注")
    private String remark;

}
