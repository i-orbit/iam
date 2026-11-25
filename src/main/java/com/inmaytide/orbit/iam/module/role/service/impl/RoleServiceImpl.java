package com.inmaytide.orbit.iam.module.role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.inmaytide.orbit.framework.core.domain.dto.PageResult;
import com.inmaytide.orbit.iam.module.role.converter.RoleConverter;
import com.inmaytide.orbit.iam.module.role.domain.Role;
import com.inmaytide.orbit.iam.module.role.domain.RoleQuery;
import com.inmaytide.orbit.iam.module.role.domain.RoleVO;
import com.inmaytide.orbit.iam.module.role.repository.RoleRepository;
import com.inmaytide.orbit.iam.module.role.service.RoleService;
import com.inmaytide.orbit.iam.module.tenant.support.TenantScope;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleConverter converter;

    @Override
    public PageResult<RoleVO> pagination(RoleQuery query) {
        LambdaQueryWrapper<Role> wrapper = Wrappers.lambdaQuery(Role.class)
                .eq(Objects.nonNull(query.getEnableStatus()), Role::getStatus, query.getEnableStatus())
                .and(StringUtils.isNotBlank(query.getKeywords()), w -> {
                    w.like(Role::getName, query.getKeywords())
                            .or().like(Role::getCode, query.getKeywords())
                            .or().like(Role::getDescription, query.getKeywords());
                })
                .orderByAsc(Role::getShowOrder);
        TenantScope.applyTenantFilter(wrapper, Role::getTenantId, query.getTenantId());
        PageDTO<Role> pi = roleRepository.selectPage(new PageDTO<>(query.getPageNumber(), query.getPageSize()), wrapper);
        return PageResult.<RoleVO>withQuery(query)
                .total(pi.getTotal())
                .elements(pi.getRecords().stream().map(converter::toVO).toList())
                .build();
    }

    @Override
    public Optional<RoleVO> get(String id) {
        Role role = roleRepository.selectById(id);
        if (role == null) {
            return Optional.empty();
        }
        TenantScope.checkOwnership(role.getTenantId());
        return Optional.of(converter.toVO(role));
    }

}
