package com.inmaytide.orbit.iam.module.tenant.controller;

import com.inmaytide.orbit.framework.core.domain.dto.PageResult;
import com.inmaytide.orbit.framework.core.domain.dto.ResponseWrapper;
import com.inmaytide.orbit.framework.core.domain.group.Update;
import com.inmaytide.orbit.framework.log.OperationType;
import com.inmaytide.orbit.framework.log.annotation.OperationLog;
import com.inmaytide.orbit.framework.security.SecurityUtils;
import com.inmaytide.orbit.iam.module.tenant.domain.CreateOrUpdateTenantOrderRequest;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantOrderQuery;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantOrderVO;
import com.inmaytide.orbit.iam.module.tenant.service.TenantOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@RestController
@Tag(name = "租户订单")
@RequestMapping("/api/tenant-orders")
@RequiredArgsConstructor
public class TenantOrderController {

    private static final String PLATFORM_OPERATOR_PERMISSION = "hasAnyRole('" + SecurityUtils.ROLE_SUPER_ADMIN + "','" + SecurityUtils.ROLE_ROBOT + "')";

    private final TenantOrderService service;

    @GetMapping
    @Operation(summary = "分页查询租户订单")
    @OperationLog(operationType = OperationType.QUERY, resourceType = TenantOrderService.RESOURCE_TYPE)
    public ResponseWrapper<PageResult<TenantOrderVO>> pagination(@ModelAttribute TenantOrderQuery query) {
        return ResponseWrapper.success(service.pagination(query));
    }

    @GetMapping("{id}")
    @Operation(summary = "查询指定租户订单")
    @OperationLog(operationType = OperationType.QUERY, resourceType = TenantOrderService.RESOURCE_TYPE)
    public ResponseWrapper<TenantOrderVO> get(@PathVariable String id) {
        return service.get(id)
                .map(ResponseWrapper::success)
                .orElseGet(() -> ResponseWrapper.failure(404, "tenant_order_not_found",
                        "未找到ID为[%s]的租户订单".formatted(id)));
    }

    @PostMapping
    @Operation(summary = "创建租户订单")
    @OperationLog(operationType = OperationType.CREATE, resourceType = TenantOrderService.RESOURCE_TYPE)
    @PreAuthorize(PLATFORM_OPERATOR_PERMISSION)
    public ResponseWrapper<TenantOrderVO> create(@RequestBody @Validated CreateOrUpdateTenantOrderRequest request) {
        return ResponseWrapper.success(service.create(request));
    }

    @PutMapping
    @Operation(summary = "修改租户订单")
    @OperationLog(operationType = OperationType.UPDATE, resourceType = TenantOrderService.RESOURCE_TYPE)
    @PreAuthorize(PLATFORM_OPERATOR_PERMISSION)
    public ResponseWrapper<TenantOrderVO> update(
            @RequestBody @Validated(Update.class) CreateOrUpdateTenantOrderRequest request) {
        return ResponseWrapper.success(service.update(request));
    }

}
