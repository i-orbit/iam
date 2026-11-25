package com.inmaytide.orbit.iam.module.role.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inmaytide.orbit.iam.module.role.domain.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Mapper
public interface RoleRepository extends BaseMapper<Role> {
}
