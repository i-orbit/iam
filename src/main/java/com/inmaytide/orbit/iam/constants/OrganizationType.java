package com.inmaytide.orbit.iam.constants;

/**
 * @author inmaytide
 * @since 2026/7/17
 */
public enum OrganizationType {
    ;

    private final Integer value;

    private final String description;

    OrganizationType(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
