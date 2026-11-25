package com.inmaytide.orbit.iam.module.tenant.license;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Data
@Component
@ConfigurationProperties(prefix = "application.tenant.license")
public class TenantLicenseProperties {

    private boolean enabled;

    private String file;

    private String publicKey;

    private String machineCode;

}
