package com.inmaytide.orbit.iam.module.user.converter;

import com.inmaytide.orbit.iam.module.user.domain.CreateOrUpdateUserRequest;
import com.inmaytide.orbit.iam.module.user.domain.User;
import com.inmaytide.orbit.iam.module.user.domain.UserVO;
import org.mapstruct.Mapper;

/**
 * @author inmaytide
 * @since 2026/7/7
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    UserVO toVO(User user);

    User toEntity(CreateOrUpdateUserRequest request);

}
