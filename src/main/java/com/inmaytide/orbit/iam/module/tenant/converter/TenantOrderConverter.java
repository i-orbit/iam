package com.inmaytide.orbit.iam.module.tenant.converter;

import com.inmaytide.orbit.iam.module.tenant.domain.CreateOrUpdateTenantOrderRequest;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantOrder;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantOrderVO;
import org.mapstruct.Mapper;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Mapper(componentModel = "spring")
public interface TenantOrderConverter {

    TenantOrderVO toVO(TenantOrder order);

    TenantOrder toEntity(CreateOrUpdateTenantOrderRequest request);

}
