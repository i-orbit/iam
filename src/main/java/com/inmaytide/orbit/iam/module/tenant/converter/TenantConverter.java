package com.inmaytide.orbit.iam.module.tenant.converter;

import com.inmaytide.orbit.iam.module.tenant.domain.CreateOrUpdateTenantRequest;
import com.inmaytide.orbit.iam.module.tenant.domain.Tenant;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantVO;
import org.mapstruct.Mapper;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Mapper(componentModel = "spring")
public interface TenantConverter {

    TenantVO toVO(Tenant tenant);

    Tenant toEntity(CreateOrUpdateTenantRequest request);

}
