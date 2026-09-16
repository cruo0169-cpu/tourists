package com.tourist.service.domain;

/**
 * 餐饮娱乐类型。
 */
public enum CateringType {
    /** 餐饮 */
    DINING("餐饮"),
    /** 娱乐 */
    ENTERTAINMENT("娱乐");

    private final String label;

    CateringType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
