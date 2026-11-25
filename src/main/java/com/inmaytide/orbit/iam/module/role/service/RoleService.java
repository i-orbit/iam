package com.inmaytide.orbit.iam.module.role.service;

import com.inmaytide.orbit.framework.core.domain.dto.PageResult;
import com.inmaytide.orbit.iam.module.role.domain.RoleQuery;
import com.inmaytide.orbit.iam.module.role.domain.RoleVO;

import java.util.Optional;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
public interface RoleService {

    String RESOURCE_TYPE = "Role";

    PageResult<RoleVO> pagination(RoleQuery query);

    Optional<RoleVO> get(String id);

}
