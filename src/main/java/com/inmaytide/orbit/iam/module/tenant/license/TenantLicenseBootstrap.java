package com.inmaytide.orbit.iam.module.tenant.license;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.inmaytide.orbit.iam.module.tenant.domain.DeploymentMode;
import com.inmaytide.orbit.iam.module.tenant.domain.Tenant;
import com.inmaytide.orbit.iam.module.tenant.repository.TenantRepository;
import com.inmaytide.orbit.iam.module.tenant.service.TenantModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 私有化部署启动时加载 License，并幂等同步租户授权信息。
 *
 * @author inmaytide
 * @since 2026/11/25
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "application.tenant.license", name = "enabled", havingValue = "true")
public class TenantLicenseBootstrap implements ApplicationRunner {

    private final TenantLicenseVerifier verifier;

    private final TenantRepository repository;

    private final TenantModuleService tenantModuleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(ApplicationArguments args) {
        TenantLicensePayload license = verifier.verify();
        Tenant tenant = repository.selectOne(Wrappers.<Tenant>lambdaQuery()
                .eq(Tenant::getCode, license.getTenantCode()));
        boolean created = tenant == null;
        if (created) {
            tenant = new Tenant();
            tenant.setCode(license.getTenantCode());
            tenant.setStatus(1);
        }
        tenant.setName(StringUtils.defaultIfBlank(license.getTenantName(), license.getTenantCode()));
        tenant.setDeploymentMode(StringUtils.defaultIfBlank(license.getDeploymentMode(),
                DeploymentMode.PRIVATE.name()));
        tenant.setEdition(license.getEdition());
        tenant.setMaxUsers(license.getMaxUsers());
        tenant.setMaxOnlineUsers(license.getMaxOnlineUsers());
        tenant.setExpiredAt(license.getExpiredAt());
        if (created) {
            try {
                repository.insert(tenant);
            } catch (DuplicateKeyException e) {
                log.info("【租户-许可】Tenant already created by another instance, {tenantCode: {}}",
                        tenant.getCode());
                return;
            }
        } else {
            repository.updateById(tenant);
        }
        tenantModuleService.replaceModules(tenant.getId(), license.getModules());
        log.info("【租户-许可】License loaded, {tenantCode: {}, edition: {}, expiredAt: {}}",
                tenant.getCode(), tenant.getEdition(), tenant.getExpiredAt());
    }

}
