package com.inmaytide.orbit.iam.module.user.controller;

import com.inmaytide.orbit.framework.core.domain.AuthenticatedUser;
import com.inmaytide.orbit.framework.core.domain.dto.AffectedResult;
import com.inmaytide.orbit.framework.core.domain.dto.BatchIdsRequest;
import com.inmaytide.orbit.framework.core.domain.dto.PageResult;
import com.inmaytide.orbit.framework.core.domain.group.Update;
import com.inmaytide.orbit.framework.core.utils.MoreStrings;
import com.inmaytide.orbit.iam.module.user.domain.CreateOrUpdateUserRequest;
import com.inmaytide.orbit.iam.module.user.domain.User;
import com.inmaytide.orbit.iam.module.user.domain.UserQuery;
import com.inmaytide.orbit.iam.module.user.domain.UserVO;
import com.inmaytide.orbit.iam.module.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author inmaytide
 * @since 2026/7/7
 */
@RestController
@Tag(name = "用户管理")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    @Operation(summary = "查询指定用户的详细信息")
    public UserVO get(@PathVariable String id) {
        return userService.get(id);
    }

    @GetMapping
    @Operation(summary = "分页查询用户信息列表")
    public PageResult<UserVO> list(@ModelAttribute UserQuery query) {
        return null;
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除指定用户信息")
    public AffectedResult deleteById(@RequestParam String id) {
        return null;
    }

    @DeleteMapping
    @Operation(summary = "批量删除指定用户信息")
    public AffectedResult deleteByIds(@RequestBody BatchIdsRequest request) {
        return null;
    }

    @PostMapping
    @Operation(summary = "创建用户信息")
    public UserVO create(@RequestBody @Validated CreateOrUpdateUserRequest request) {
        return null;
    }

    @PutMapping
    @Operation(summary = "修改用户信息")
    public UserVO update(@RequestBody @Validated(Update.class) CreateOrUpdateUserRequest request) {
        return null;
    }

    @GetMapping("/names")
    @Operation(summary = "批量查询指定用户的姓名")
    public Map<String, String> findNamesByIds(@RequestParam String ids) {
        return userService.findFieldValues(MoreStrings.splitByCommas(ids), User::getName);
    }

    @GetMapping("/current")
    @Operation(summary = "获取当前请求认证信息关联的用户详细信息")
    public AuthenticatedUser getCurrentAuthenticatedUser() {
        return userService.getCurrentAuthenticatedUser().orElse(null);
    }

}
