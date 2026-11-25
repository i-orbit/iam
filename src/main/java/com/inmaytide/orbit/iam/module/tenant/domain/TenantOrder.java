package com.inmaytide.orbit.iam.module.tenant.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.inmaytide.orbit.framework.core.domain.TombstoneEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_tenant_order")
public class TenantOrder extends TombstoneEntity {

    private String tenantId;

    private String orderNo;

    private String contractNo;

    private String contractName;

    private BigDecimal contractAmount;

    private String currency;

    private String orderType;

    private String status;

    private Instant signedAt;

    private Instant startAt;

    private Instant endAt;

    private Instant paidAt;

    private String paymentMethod;

    private String contractUrl;

    private String remark;

}
