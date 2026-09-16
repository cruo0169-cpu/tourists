package com.tourist.service.domain;

/**
 * 投诉流转状态。
 */
public enum ComplaintStatus {
    /** 待审批 */
    PENDING_APPROVAL("待审批"),
    /** 已驳回 */
    REJECTED("已驳回"),
    /** 处理中 */
    IN_HANDLING("处理中"),
    /** 待游客确认（处理人员已提交结果） */
    HANDLED("待游客确认"),
    /** 游客已确认/评价 */
    CONFIRMED("游客已确认"),
    /** 已结案 */
    CLOSED("已结案");

    private final String label;

    ComplaintStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
