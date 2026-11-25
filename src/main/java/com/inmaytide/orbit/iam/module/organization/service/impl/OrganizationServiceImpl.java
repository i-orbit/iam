package com.inmaytide.orbit.iam.module.organization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.inmaytide.orbit.iam.module.organization.domain.Organization;
import com.inmaytide.orbit.iam.module.organization.repository.OrganizationRepository;
import com.inmaytide.orbit.iam.module.organization.service.OrganizationService;
import com.inmaytide.orbit.iam.module.tenant.support.TenantScope;
import com.inmaytide.orbit.iam.module.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author inmaytide
 * @since 2026/7/17
 */
@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository repository;

    @Override
    public List<String> listAuthorizedIdsByUserId(String userId) {
        return List.of();
    }

    @Override
    public Map<String, String> findFieldValues(List<String> ids, SFunction<Organization, String> valueColumn) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyMap();
        }
        LambdaQueryWrapper<Organization> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Organization::getId, valueColumn);
        wrapper.in(Organization::getId, ids);
        TenantScope.applyTenantFilter(wrapper, Organization::getTenantId, null);
        return repository.selectList(wrapper)
                .stream()
                .collect(Collectors.toMap(Organization::getId, valueColumn));
    }

}
