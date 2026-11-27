package com.inmaytide.orbit.iam.constants;

import com.inmaytide.orbit.framework.core.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * iam 服务业务错误码。
 *
 * @author inmaytide
 * @since 2026/11/26
 */
@Getter
@RequiredArgsConstructor
public enum IamErrorCode implements ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user_not_found", "未找到指定的用户信息"),
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "role_notFound", "未找到指定的角色信息"),
    TENANT_NOT_FOUND(HttpStatus.NOT_FOUND, "tenant_not_found", "未找到指定的租户信息"),
    TENANT_ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "tenant_order_not_found", "未找到指定的租户订单");

    private final HttpStatus httpStatus;

    private final String code;

    private final String description;

}
