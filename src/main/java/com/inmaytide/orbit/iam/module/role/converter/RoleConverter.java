package com.inmaytide.orbit.iam.module.role.converter;

import com.inmaytide.orbit.iam.module.role.domain.CreateOrUpdateUserRequest;
import com.inmaytide.orbit.iam.module.role.domain.Role;
import com.inmaytide.orbit.iam.module.role.domain.RoleVO;
import org.mapstruct.Mapper;

/**
 * @author inmaytide
 * @since 2026/7/7
 */
@Mapper(componentModel = "spring")
public interface RoleConverter {

    RoleVO toVO(Role user);

    Role toEntity(CreateOrUpdateUserRequest request);

}
