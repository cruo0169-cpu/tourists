package com.tourist.service.domain;

/**
 * 游客咨询状态。
 */
public enum ConsultationStatus {
    /** 待回复 */
    PENDING("待回复"),
    /** 已回复 */
    ANSWERED("已回复");

    private final String label;

    ConsultationStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
