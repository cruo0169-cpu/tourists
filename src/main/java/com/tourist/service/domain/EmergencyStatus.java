package com.tourist.service.domain;

/**
 * 旅游应急信息状态。
 */
public enum EmergencyStatus {
    /** 待审批 */
    PENDING_APPROVAL("待审批"),
    /** 已驳回 */
    REJECTED("已驳回"),
    /** 已发布（审批通过、对游客可见） */
    PUBLISHED("已发布"),
    /** 已过期 */
    EXPIRED("已过期");

    private final String label;

    EmergencyStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
