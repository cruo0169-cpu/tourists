package com.tourist.service.domain;

/**
 * 路况状态。
 */
public enum RoadStatus {
    SMOOTH("通畅"),
    SLOW("缓行"),
    CONGESTED("拥堵"),
    CONTROLLED("交通管制");

    private final String label;

    RoadStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
