package com.inmaytide.orbit.iam.module.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.inmaytide.orbit.framework.core.domain.dto.PageResult;
import com.inmaytide.orbit.framework.core.exception.CommonErrorCode;
import com.inmaytide.orbit.framework.core.exception.ProblemDetailsException;
import com.inmaytide.orbit.iam.constants.IamErrorCode;
import com.inmaytide.orbit.iam.module.tenant.converter.TenantOrderConverter;
import com.inmaytide.orbit.iam.module.tenant.domain.CreateOrUpdateTenantOrderRequest;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantOrder;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantOrderQuery;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantOrderStatus;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantOrderType;
import com.inmaytide.orbit.iam.module.tenant.domain.TenantOrderVO;
import com.inmaytide.orbit.iam.module.tenant.repository.TenantOrderRepository;
import com.inmaytide.orbit.iam.module.tenant.repository.TenantRepository;
import com.inmaytide.orbit.iam.module.tenant.service.TenantOrderService;
import com.inmaytide.orbit.iam.module.tenant.support.TenantScope;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * @author inmaytide
 * @since 2026/11/25
 */
@Service
@RequiredArgsConstructor
public class TenantOrderServiceImpl implements TenantOrderService {

    private final TenantOrderRepository repository;

    private final TenantOrderConverter converter;

    private final TenantRepository tenantRepository;

    @Override
    public PageResult<TenantOrderVO> pagination(TenantOrderQuery query) {
        LambdaQueryWrapper<TenantOrder> wrapper = Wrappers.lambdaQuery(TenantOrder.class)
                .eq(StringUtils.isNotBlank(query.getOrderNo()), TenantOrder::getOrderNo, query.getOrderNo())
                .eq(StringUtils.isNotBlank(query.getContractNo()), TenantOrder::getContractNo, query.getContractNo())
                .eq(StringUtils.isNotBlank(query.getStatus()), TenantOrder::getStatus, query.getStatus())
                .eq(StringUtils.isNotBlank(query.getOrderType()), TenantOrder::getOrderType, query.getOrderType())
                .and(StringUtils.isNotBlank(query.getKeywords()), w -> w
                        .like(TenantOrder::getOrderNo, query.getKeywords())
                        .or().like(TenantOrder::getContractNo, query.getKeywords())
                        .or().like(TenantOrder::getContractName, query.getKeywords()))
                .orderByDesc(TenantOrder::getCreatedAt);
        TenantScope.applyTenantFilter(wrapper, TenantOrder::getTenantId, query.getTenantId());
        PageDTO<TenantOrder> page = repository.selectPage(
                new PageDTO<>(query.getPageNumber(), query.getPageSize()), wrapper);
        return PageResult.<TenantOrderVO>withQuery(query)
                .total(page.getTotal())
                .elements(page.getRecords().stream().map(converter::toVO).toList())
                .build();
    }

    @Override
    public Optional<TenantOrderVO> get(String id) {
        TenantOrder order = repository.selectById(id);
        if (order == null) {
            return Optional.empty();
        }
        TenantScope.checkOwnership(order.getTenantId());
        return Optional.of(converter.toVO(order));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TenantOrderVO create(CreateOrUpdateTenantOrderRequest request) {
        validate(request, null);
        TenantOrder order = converter.toEntity(request);
        order.setId(null);
        order.setOrderNo(request.getOrderNo().trim());
        order.setStatus(normalize(order.getStatus(), TenantOrderStatus.PENDING.name()));
        order.setOrderType(normalize(order.getOrderType(), TenantOrderType.NEW.name()));
        order.setCurrency(normalize(order.getCurrency(), "CNY"));
        try {
            repository.insert(order);
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("订单编号已存在: " + order.getOrderNo());
        }
        return converter.toVO(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TenantOrderVO update(CreateOrUpdateTenantOrderRequest request) {
        TenantOrder existing = repository.selectById(request.getId());
        if (existing == null) {
            throw new ProblemDetailsException(IamErrorCode.TENANT_ORDER_NOT_FOUND,
                    "租户订单不存在: " + request.getId());
        }
        TenantScope.checkOwnership(existing.getTenantId());
        if (!Objects.equals(existing.getTenantId(), request.getTenantId())) {
            throw new IllegalArgumentException("不允许变更订单所属租户");
        }
        validate(request, request.getId());
        TenantOrder order = converter.toEntity(request);
        order.setTenantId(existing.getTenantId());
        order.setOrderNo(request.getOrderNo().trim());
        order.setStatus(normalize(order.getStatus(), null));
        order.setOrderType(normalize(order.getOrderType(), null));
        order.setCurrency(normalize(order.getCurrency(), null));
        order.setVersion(existing.getVersion());
        try {
            if (repository.updateById(order) == 0) {
                throw new ProblemDetailsException(CommonErrorCode.CONFLICT, "租户订单已被修改, 请刷新后重试");
            }
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("订单编号已存在: " + order.getOrderNo());
        }
        return converter.toVO(repository.selectById(order.getId()));
    }

    private void validate(CreateOrUpdateTenantOrderRequest request, String excludeId) {
        if (StringUtils.isBlank(request.getTenantId())) {
            throw new IllegalArgumentException("租户ID不能为空");
        }
        if (tenantRepository.selectById(request.getTenantId()) == null) {
            throw new ProblemDetailsException(IamErrorCode.TENANT_NOT_FOUND,
                    "租户不存在: " + request.getTenantId());
        }
        if (StringUtils.isBlank(request.getOrderNo())) {
            throw new IllegalArgumentException("订单编号不能为空");
        }
        Long count = repository.selectCount(Wrappers.<TenantOrder>lambdaQuery()
                .eq(TenantOrder::getOrderNo, request.getOrderNo().trim())
                .ne(StringUtils.isNotBlank(excludeId), TenantOrder::getId, excludeId));
        if (count != null && count > 0) {
            throw new IllegalArgumentException("订单编号已存在: " + request.getOrderNo().trim());
        }
        try {
            if (StringUtils.isNotBlank(request.getStatus())) {
                TenantOrderStatus.valueOf(request.getStatus().trim().toUpperCase());
            }
            if (StringUtils.isNotBlank(request.getOrderType())) {
                TenantOrderType.valueOf(request.getOrderType().trim().toUpperCase());
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("订单状态或类型不合法");
        }
        if (request.getContractAmount() != null && request.getContractAmount().signum() < 0) {
            throw new IllegalArgumentException("合同金额不能为负数");
        }
    }

    private static String normalize(String value, String defaultValue) {
        return StringUtils.isBlank(value) ? defaultValue : value.trim().toUpperCase();
    }

}
