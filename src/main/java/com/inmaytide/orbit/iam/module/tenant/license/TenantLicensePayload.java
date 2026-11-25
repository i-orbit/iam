package com.inmaytide.orbit.iam.module.tenant.license;

import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Data
public class TenantLicensePayload {

    private String licenseId;

    private String tenantCode;

    private String tenantName;

    private String deploymentMode;

    private String edition;

    private List<String> modules;

    private Integer maxUsers;

    private Integer maxOnlineUsers;

    private Instant issuedAt;

    private Instant expiredAt;

    private String machineCode;

}
