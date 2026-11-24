package com.inmaytide.orbit.iam.module.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inmaytide.orbit.iam.module.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author inmaytide
 * @since 2026/7/7
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
