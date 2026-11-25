package com.inmaytide.orbit.iam.module.tenant.service;

import com.inmaytide.orbit.framework.core.domain.dto.PageResult;
import com.inmaytide.orbit.iam.module.tenant.domain.CreateOrUpdateTenantOrderRequest;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantOrderQuery;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantOrderVO;

import java.util.Optional;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
public interface TenantOrderService {

    String RESOURCE_TYPE = "TenantOrder";

    PageResult<TenantOrderVO> pagination(TenantOrderQuery query);

    Optional<TenantOrderVO> get(String id);

    TenantOrderVO create(CreateOrUpdateTenantOrderRequest request);

    TenantOrderVO update(CreateOrUpdateTenantOrderRequest request);

}
