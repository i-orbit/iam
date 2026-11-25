package com.inmaytide.orbit.iam.module.tenant.support;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.inmaytide.orbit.framework.security.SecurityUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * 显式租户过滤与归属校验。
 *
 * @author inmaytide
 * @since 2026/11/25
 */
public final class TenantScope {

    private TenantScope() {

    }

    public static boolean isPlatformOperator() {
        return SecurityUtils.isSuperAdministrator() || SecurityUtils.isRobot();
    }

    public static Optional<String> getCurrentTenantId() {
        return SecurityUtils.getCurrentTenantId();
    }

    public static String requireCurrentTenantId() {
        return getCurrentTenantId()
                .orElseThrow(() -> new AccessDeniedException("当前用户未绑定租户, 无法访问租户数据"));
    }

    public static <T> void applyTenantFilter(LambdaQueryWrapper<T> wrapper,
                                             SFunction<T, String> tenantColumn,
                                             String requestedTenantId) {
        if (isPlatformOperator()) {
            if (StringUtils.hasText(requestedTenantId)) {
                wrapper.eq(tenantColumn, requestedTenantId);
            }
            return;
        }
        wrapper.eq(tenantColumn, requireCurrentTenantId());
    }

    public static void checkOwnership(String entityTenantId) {
        if (isPlatformOperator()) {
            return;
        }
        if (!Objects.equals(requireCurrentTenantId(), entityTenantId)) {
            throw new AccessDeniedException("无权访问其他租户的数据");
        }
    }

}
