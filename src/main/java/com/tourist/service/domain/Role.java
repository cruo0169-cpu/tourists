package com.tourist.service.domain;

/**
 * 系统角色。
 */
public enum Role {
    /** 游客 */
    TOURIST("游客"),
    /** 平台管理人员 */
    PLATFORM_ADMIN("平台管理人员"),
    /** 审批人员 */
    APPROVER("审批人员"),
    /** 投诉处理人员 */
    COMPLAINT_HANDLER("投诉处理人员"),
    /** 酒店管理人员 */
    HOTEL_MANAGER("酒店管理人员");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
