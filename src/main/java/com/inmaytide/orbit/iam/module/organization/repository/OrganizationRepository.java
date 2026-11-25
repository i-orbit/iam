package com.inmaytide.orbit.iam.module.organization.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inmaytide.orbit.iam.module.organization.domain.Organization;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author inmaytide
 * @since 2026/7/7
 */
@Mapper
public interface OrganizationRepository extends BaseMapper<Organization> {
}
