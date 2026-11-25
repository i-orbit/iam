package com.inmaytide.orbit.iam.module.organization.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.inmaytide.orbit.iam.module.organization.domain.Organization;

import java.util.List;
import java.util.Map;

/**
 * @author inmaytide
 * @since 2026/7/17
 */
public interface OrganizationService {

    List<String> listAuthorizedIdsByUserId(String userId);

    Map<String, String> findFieldValues(List<String> ids, SFunction<Organization, String> valueColumn);

}
