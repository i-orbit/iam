package com.inmaytide.orbit.iam.module.tenant.service;

import com.inmaytide.orbit.framework.core.domain.dto.PageResult;
import com.inmaytide.orbit.iam.module.tenant.domain.CreateOrUpdateTenantRequest;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantQuery;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantVO;

import java.util.Optional;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
public interface TenantService {

    String RESOURCE_TYPE = "Tenant";

    PageResult<TenantVO> pagination(TenantQuery query);

    Optional<TenantVO> get(String id);

    TenantVO create(CreateOrUpdateTenantRequest request);

    TenantVO update(CreateOrUpdateTenantRequest request);

    TenantVO updateStatus(String id, Integer status);

    Optional<TenantVO> getCurrentTenant();

}
