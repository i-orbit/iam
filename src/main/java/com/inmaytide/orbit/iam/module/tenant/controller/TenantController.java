package com.inmaytide.orbit.iam.module.tenant.controller;

import com.inmaytide.orbit.framework.core.domain.dto.PageResult;
import com.inmaytide.orbit.framework.core.domain.dto.ResponseWrapper;
import com.inmaytide.orbit.framework.core.domain.group.Update;
import com.inmaytide.orbit.framework.log.OperationType;
import com.inmaytide.orbit.framework.log.annotation.OperationLog;
import com.inmaytide.orbit.framework.security.SecurityUtils;
import com.inmaytide.orbit.iam.module.tenant.domain.CreateOrUpdateTenantRequest;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantQuery;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantVO;
import com.inmaytide.orbit.iam.module.tenant.domain.UpdateTenantModulesRequest;
import com.inmaytide.orbit.iam.module.tenant.domain.UpdateTenantStatusRequest;
import com.inmaytide.orbit.iam.module.tenant.service.TenantModuleService;
import com.inmaytide.orbit.iam.module.tenant.service.TenantService;
import com.inmaytide.orbit.iam.module.tenant.support.TenantScope;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@RestController
@Tag(name = "租户管理")
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
public class TenantController {

    private static final String PLATFORM_OPERATOR_PERMISSION = "hasAnyRole('" + SecurityUtils.ROLE_SUPER_ADMIN + "','" + SecurityUtils.ROLE_ROBOT + "')";

    private final TenantService service;

    private final TenantModuleService tenantModuleService;

    @GetMapping
    @Operation(summary = "分页查询租户信息")
    @OperationLog(operationType = OperationType.QUERY, resourceType = TenantService.RESOURCE_TYPE)
    @PreAuthorize(PLATFORM_OPERATOR_PERMISSION)
    public ResponseWrapper<PageResult<TenantVO>> pagination(@ModelAttribute TenantQuery query) {
        return ResponseWrapper.success(service.pagination(query));
    }

    @GetMapping("current")
    @Operation(summary = "查询当前登录用户所属租户")
    public ResponseWrapper<TenantVO> current() {
        return ResponseWrapper.success(service.getCurrentTenant().orElse(null));
    }

    @GetMapping("{id}")
    @Operation(summary = "查询指定租户信息")
    @OperationLog(operationType = OperationType.QUERY, resourceType = TenantService.RESOURCE_TYPE)
    @PreAuthorize(PLATFORM_OPERATOR_PERMISSION)
    public ResponseWrapper<TenantVO> get(@PathVariable String id) {
        return service.get(id)
                .map(ResponseWrapper::success)
                .orElseGet(() -> ResponseWrapper.failure(404, "tenant_not_found", "未找到ID为[%s]的租户信息".formatted(id)));
    }

    @PostMapping
    @Operation(summary = "创建租户")
    @OperationLog(operationType = OperationType.CREATE, resourceType = TenantService.RESOURCE_TYPE)
    @PreAuthorize(PLATFORM_OPERATOR_PERMISSION)
    public ResponseWrapper<TenantVO> create(@RequestBody @Validated CreateOrUpdateTenantRequest request) {
        return ResponseWrapper.success(service.create(request));
    }

    @PutMapping
    @Operation(summary = "修改租户")
    @OperationLog(operationType = OperationType.UPDATE, resourceType = TenantService.RESOURCE_TYPE)
    @PreAuthorize(PLATFORM_OPERATOR_PERMISSION)
    public ResponseWrapper<TenantVO> update(@RequestBody @Validated(Update.class) CreateOrUpdateTenantRequest request) {
        return ResponseWrapper.success(service.update(request));
    }

    @PutMapping("{id}/status")
    @Operation(summary = "启用/禁用租户")
    @OperationLog(operationType = OperationType.UPDATE, resourceType = TenantService.RESOURCE_TYPE)
    @PreAuthorize(PLATFORM_OPERATOR_PERMISSION)
    public ResponseWrapper<TenantVO> updateStatus(@PathVariable String id,
                                                  @RequestBody @Validated UpdateTenantStatusRequest request) {
        return ResponseWrapper.success(service.updateStatus(id, request.getStatus()));
    }

    @GetMapping("current/modules")
    @Operation(summary = "查询当前租户已开通模块")
    public ResponseWrapper<List<String>> currentModules() {
        return ResponseWrapper.success(tenantModuleService.listEnabled(TenantScope.requireCurrentTenantId()));
    }

    @GetMapping("{id}/modules")
    @Operation(summary = "查询指定租户已开通模块")
    @OperationLog(operationType = OperationType.QUERY, resourceType = TenantService.RESOURCE_TYPE)
    @PreAuthorize(PLATFORM_OPERATOR_PERMISSION)
    public ResponseWrapper<List<String>> modules(@PathVariable String id) {
        return ResponseWrapper.success(tenantModuleService.listEnabled(id));
    }

    @PutMapping("{id}/modules")
    @Operation(summary = "设置租户已开通模块")
    @OperationLog(operationType = OperationType.UPDATE, resourceType = TenantService.RESOURCE_TYPE)
    @PreAuthorize(PLATFORM_OPERATOR_PERMISSION)
    public ResponseWrapper<List<String>> updateModules(@PathVariable String id,
                                                       @RequestBody @Validated UpdateTenantModulesRequest request) {
        tenantModuleService.replaceModules(id, request.getModules());
        return ResponseWrapper.success(tenantModuleService.listEnabled(id));
    }

}
