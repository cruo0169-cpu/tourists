package com.tourist.service.domain;

/**
 * 酒店类型。
 */
public enum HotelType {
    /** 星级酒店 */
    STAR("星级酒店"),
    /** 非星级酒店 */
    NON_STAR("非星级酒店"),
    /** 乡村旅游酒店 */
    RURAL("乡村旅游酒店");

    private final String label;

    HotelType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
