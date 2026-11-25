package com.inmaytide.orbit.iam.module.tenant.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Mapper
public interface TenantOrderRepository extends BaseMapper<TenantOrder> {
}
