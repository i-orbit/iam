package com.inmaytide.orbit.iam.module.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.inmaytide.orbit.framework.clients.module.core.api.DictionaryService;
import com.inmaytide.orbit.framework.core.domain.AuthenticatedUser;
import com.inmaytide.orbit.framework.security.SecurityUtils;
import com.inmaytide.orbit.iam.module.tenant.support.TenantScope;
import com.inmaytide.orbit.iam.module.user.converter.UserConverter;
import com.inmaytide.orbit.iam.module.user.domain.CreateOrUpdateUserRequest;
import com.inmaytide.orbit.iam.module.user.domain.User;
import com.inmaytide.orbit.iam.module.user.domain.UserVO;
import com.inmaytide.orbit.iam.module.user.mapper.UserMapper;
import com.inmaytide.orbit.iam.module.user.service.UserService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author inmaytide
 * @since 2026/7/7
 */
@Primary
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper baseMapper;

    private final UserConverter converter;

    private final DictionaryService dictionaryService;

    public UserServiceImpl(UserMapper baseMapper, UserConverter converter, DictionaryService dictionaryService) {
        this.baseMapper = baseMapper;
        this.converter = converter;
        this.dictionaryService = dictionaryService;
    }

    @Override
    public Map<String, String> findFieldValues(List<String> ids, SFunction<User, String> valueColumn) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyMap();
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getId, valueColumn);
        wrapper.in(User::getId, ids);
        TenantScope.applyTenantFilter(wrapper, User::getTenantId, null);
        return baseMapper.selectList(wrapper)
                .stream()
                .collect(Collectors.toMap(User::getId, valueColumn));

    }

    @Override
    public UserVO get(String id) {
        User user = baseMapper.selectById(id);
        if (user == null) {
            throw new IllegalArgumentException("notfound user with id: " + id);
        }
        TenantScope.checkOwnership(user.getTenantId());
        UserVO res = converter.toVO(user);
        Map<String, String> dictionaryShowValues = dictionaryService.findShowValueByIds(Arrays.asList(user.getGender(), user.getEmploymentStatus()));
        res.setGenderShowValue(dictionaryShowValues.get(user.getGender()));
        res.setEmploymentStatusShowValue(dictionaryShowValues.get(user.getEmploymentStatus()));
        return res;
    }

    @Override
    public UserVO create(CreateOrUpdateUserRequest request) {
        return null;
    }

    @Override
    public UserVO update(CreateOrUpdateUserRequest request) {
        return null;
    }

    @Override
    public Optional<AuthenticatedUser> getCurrentAuthenticatedUser() {
        return SecurityUtils.getCurrentAuthenticatedUserId().map(id -> {
           AuthenticatedUser authenticatedUser = new AuthenticatedUser();
           User user = baseMapper.selectById(id);
           authenticatedUser.setId(user.getId());
           authenticatedUser.setTenantId(user.getTenantId());
           authenticatedUser.setName(user.getName());
           return authenticatedUser;
        });
    }
}
