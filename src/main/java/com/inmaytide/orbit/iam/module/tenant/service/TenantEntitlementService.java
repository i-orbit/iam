package com.inmaytide.orbit.iam.module.tenant.service;

import com.inmaytide.orbit.iam.module.tenant.domain.Tenant;
import com.inmaytide.orbit.iam.module.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * 租户授权状态与功能模块校验入口。
 *
 * @author inmaytide
 * @since 2026/11/25
 */
@Service
@RequiredArgsConstructor
public class TenantEntitlementService {

    private final TenantRepository repository;

    private final TenantModuleService tenantModuleService;

    public boolean isActive(String tenantId) {
        if (StringUtils.isBlank(tenantId)) {
            return false;
        }
        return isActive(repository.selectById(tenantId));
    }

    public boolean hasModule(String tenantId, String moduleCode) {
        if (StringUtils.isAnyBlank(tenantId, moduleCode)) {
            return false;
        }
        return isActive(repository.selectById(tenantId)) && tenantModuleService.isEnabled(tenantId, moduleCode);
    }

    private boolean isActive(Tenant tenant) {
        return tenant != null
                && tenant.getStatus() != null
                && tenant.getStatus() == 1
                && (tenant.getExpiredAt() == null || tenant.getExpiredAt().isAfter(Instant.now()));
    }

}
