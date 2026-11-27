package com.inmaytide.orbit.iam.module.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.inmaytide.orbit.framework.core.domain.dto.PageResult;
import com.inmaytide.orbit.framework.core.exception.CommonErrorCode;
import com.inmaytide.orbit.framework.core.exception.ProblemDetailsException;
import com.inmaytide.orbit.framework.security.SecurityUtils;
import com.inmaytide.orbit.iam.constants.IamErrorCode;
import com.inmaytide.orbit.iam.module.tenant.converter.TenantConverter;
import com.inmaytide.orbit.iam.module.tenant.domain.CreateOrUpdateTenantRequest;
import com.inmaytide.orbit.iam.module.tenant.domain.DeploymentMode;
import com.inmaytide.orbit.iam.module.tenant.domain.Tenant;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantQuery;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantVO;
import com.inmaytide.orbit.iam.module.tenant.repository.TenantRepository;
import com.inmaytide.orbit.iam.module.tenant.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository repository;

    private final TenantConverter converter;

    @Override
    public PageResult<TenantVO> pagination(TenantQuery query) {
        LambdaQueryWrapper<Tenant> wrapper = Wrappers.lambdaQuery(Tenant.class)
                .eq(query.getStatus() != null, Tenant::getStatus, query.getStatus())
                .eq(StringUtils.isNotBlank(query.getDeploymentMode()),
                        Tenant::getDeploymentMode, query.getDeploymentMode())
                .and(StringUtils.isNotBlank(query.getKeywords()), w -> w
                        .like(Tenant::getCode, query.getKeywords())
                        .or().like(Tenant::getName, query.getKeywords()))
                .orderByAsc(Tenant::getCode);
        PageDTO<Tenant> page = repository.selectPage(
                new PageDTO<>(query.getPageNumber(), query.getPageSize()), wrapper);
        return PageResult.<TenantVO>withQuery(query)
                .total(page.getTotal())
                .elements(page.getRecords().stream().map(converter::toVO).toList())
                .build();
    }

    @Override
    public Optional<TenantVO> get(String id) {
        return Optional.ofNullable(repository.selectById(id)).map(converter::toVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TenantVO create(CreateOrUpdateTenantRequest request) {
        validate(request, null);
        Tenant tenant = converter.toEntity(request);
        tenant.setId(null);
        tenant.setCode(request.getCode().trim());
        tenant.setName(request.getName().trim());
        if (tenant.getStatus() == null) {
            tenant.setStatus(1);
        }
        if (StringUtils.isBlank(tenant.getDeploymentMode())) {
            tenant.setDeploymentMode(DeploymentMode.PRIVATE.name());
        }
        try {
            repository.insert(tenant);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("租户编码已存在: " + tenant.getCode());
        }
        return converter.toVO(tenant);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TenantVO update(CreateOrUpdateTenantRequest request) {
        Tenant existing = repository.selectById(request.getId());
        if (existing == null) {
            throw new ProblemDetailsException(IamErrorCode.TENANT_NOT_FOUND,
                    "租户不存在: " + request.getId());
        }
        validate(request, request.getId());
        Tenant tenant = converter.toEntity(request);
        tenant.setCode(request.getCode().trim());
        tenant.setName(request.getName().trim());
        tenant.setVersion(existing.getVersion());
        try {
            if (repository.updateById(tenant) == 0) {
                throw new ProblemDetailsException(CommonErrorCode.CONFLICT, "租户信息已被修改, 请刷新后重试");
            }
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("租户编码已存在: " + tenant.getCode());
        }
        return converter.toVO(repository.selectById(tenant.getId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TenantVO updateStatus(String id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new IllegalArgumentException("租户状态只能为 0 或 1");
        }
        Tenant existing = repository.selectById(id);
        if (existing == null) {
            throw new ProblemDetailsException(IamErrorCode.TENANT_NOT_FOUND, "租户不存在: " + id);
        }
        existing.setStatus(status);
        if (repository.updateById(existing) == 0) {
            throw new ProblemDetailsException(CommonErrorCode.CONFLICT, "租户信息已被修改, 请刷新后重试");
        }
        return converter.toVO(repository.selectById(id));
    }

    @Override
    public Optional<TenantVO> getCurrentTenant() {
        return SecurityUtils.getCurrentTenantId().flatMap(this::get);
    }

    private void validate(CreateOrUpdateTenantRequest request, String excludeId) {
        if (StringUtils.isBlank(request.getCode())) {
            throw new IllegalArgumentException("租户编码不能为空");
        }
        if (StringUtils.isBlank(request.getName())) {
            throw new IllegalArgumentException("租户名称不能为空");
        }
        String code = request.getCode().trim();
        Long count = repository.selectCount(Wrappers.<Tenant>lambdaQuery()
                .eq(Tenant::getCode, code)
                .ne(StringUtils.isNotBlank(excludeId), Tenant::getId, excludeId));
        if (count != null && count > 0) {
            throw new IllegalArgumentException("租户编码已存在: " + code);
        }
        if (StringUtils.isNotBlank(request.getDeploymentMode())) {
            try {
                DeploymentMode.valueOf(request.getDeploymentMode().trim());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("不支持的部署模式: " + request.getDeploymentMode());
            }
        }
        if (request.getStatus() != null && request.getStatus() != 0 && request.getStatus() != 1) {
            throw new IllegalArgumentException("租户状态只能为 0 或 1");
        }
        if (request.getMaxUsers() != null && request.getMaxUsers() < 0) {
            throw new IllegalArgumentException("用户数上限不能为负数");
        }
        if (request.getMaxOnlineUsers() != null && request.getMaxOnlineUsers() < 0) {
            throw new IllegalArgumentException("在线数上限不能为负数");
        }
    }

}
