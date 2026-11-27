package com.inmaytide.orbit.iam.module.role.controller;

import com.inmaytide.orbit.framework.core.domain.dto.PageResult;
import com.inmaytide.orbit.framework.core.exception.ProblemDetailsException;
import com.inmaytide.orbit.framework.log.OperationType;
import com.inmaytide.orbit.framework.log.annotation.OperationLog;
import com.inmaytide.orbit.iam.constants.IamErrorCode;
import com.inmaytide.orbit.iam.module.role.domain.RoleQuery;
import com.inmaytide.orbit.iam.module.role.domain.RoleVO;
import com.inmaytide.orbit.iam.module.role.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@RestController
@Tag(name = "用户管理")
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;

    @GetMapping
    @Operation(summary = "查询当前用户所属租户的角色信息列表")
    @OperationLog(operationType = OperationType.QUERY, resourceType = RoleService.RESOURCE_TYPE)
    public PageResult<RoleVO> pagination(@ModelAttribute RoleQuery query) {
        return service.pagination(query);
    }

    @GetMapping("{id}")
    @Operation(summary = "查询指定角色详细信息")
    @OperationLog(operationType = OperationType.QUERY, resourceType = RoleService.RESOURCE_TYPE)
    public RoleVO get(@PathVariable String id) {
        return service.get(id)
                .orElseThrow(() -> new ProblemDetailsException(
                        IamErrorCode.ROLE_NOT_FOUND,
                        "未找到ID为[%s]的角色信息".formatted(id)));

    }

}
