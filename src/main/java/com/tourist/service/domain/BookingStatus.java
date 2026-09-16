package com.tourist.service.domain;

/**
 * 入住预订状态。
 */
public enum BookingStatus {
    /** 待确认 */
    PENDING("待确认"),
    /** 已确认 */
    CONFIRMED("已确认"),
    /** 已取消/已拒绝 */
    CANCELLED("已取消");

    private final String label;

    BookingStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}