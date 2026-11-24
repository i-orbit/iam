package com.inmaytide.orbit.iam.module.user.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.inmaytide.orbit.framework.core.service.AuthenticatedUserService;
import com.inmaytide.orbit.iam.module.user.domain.CreateOrUpdateUserRequest;
import com.inmaytide.orbit.iam.module.user.domain.User;
import com.inmaytide.orbit.iam.module.user.domain.UserVO;

import java.util.List;
import java.util.Map;

/**
 * @author inmaytide
 * @since 2026/7/7
 */
public interface UserService extends AuthenticatedUserService {

    Map<String, String> findFieldValues(List<String> ids, SFunction<User, String> valueColumn);

    UserVO get(String id);

    UserVO create(CreateOrUpdateUserRequest request);

    UserVO update(CreateOrUpdateUserRequest request);

}
