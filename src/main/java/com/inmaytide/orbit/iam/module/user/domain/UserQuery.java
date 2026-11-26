package com.inmaytide.orbit.iam.module.user.domain;

import com.inmaytide.orbit.framework.core.domain.dto.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author inmaytide
 * @since 2026/7/7
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "用户查询参数")
public class UserQuery extends PageQuery {
}
