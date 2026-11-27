package com.inmaytide.orbit.iam.module.tenant.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.inmaytide.orbit.framework.core.exception.ProblemDetailsException;
import com.inmaytide.orbit.iam.constants.IamErrorCode;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantModule;
import com.inmaytide.orbit.iam.module.tenant.repository.TenantModuleRepository;
import com.inmaytide.orbit.iam.module.tenant.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Service
@RequiredArgsConstructor
public class TenantModuleService {

    private final TenantModuleRepository repository;

    private final TenantRepository tenantRepository;

    public List<String> listEnabled(String tenantId) {
        return repository.selectList(Wrappers.<TenantModule>lambdaQuery()
                        .eq(TenantModule::getTenantId, tenantId)
                        .eq(TenantModule::getEnabled, 1)
                        .orderByAsc(TenantModule::getModuleCode))
                .stream()
                .map(TenantModule::getModuleCode)
                .toList();
    }

    public boolean isEnabled(String tenantId, String moduleCode) {
        if (StringUtils.isAnyBlank(tenantId, moduleCode)) {
            return false;
        }
        Long count = repository.selectCount(Wrappers.<TenantModule>lambdaQuery()
                .eq(TenantModule::getTenantId, tenantId)
                .eq(TenantModule::getModuleCode, moduleCode)
                .eq(TenantModule::getEnabled, 1));
        return count != null && count > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public void replaceModules(String tenantId, List<String> moduleCodes) {
        if (StringUtils.isBlank(tenantId)) {
            throw new IllegalArgumentException("租户ID不能为空");
        }
        if (tenantRepository.selectById(tenantId) == null) {
            throw new ProblemDetailsException(IamErrorCode.TENANT_NOT_FOUND,
                    "租户不存在: " + tenantId);
        }
        repository.delete(Wrappers.<TenantModule>lambdaQuery().eq(TenantModule::getTenantId, tenantId));
        if (moduleCodes == null || moduleCodes.isEmpty()) {
            return;
        }
        Set<String> codes = moduleCodes.stream()
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        for (String code : codes) {
            TenantModule module = new TenantModule();
            module.setTenantId(tenantId);
            module.setModuleCode(code);
            module.setEnabled(1);
            repository.insert(module);
        }
    }

}
