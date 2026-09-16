package com.tourist.service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 酒店营销记录（平台管理人员根据导流情况录入）。
 */
@Entity
@Table(name = "hotel_marketing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelMarketing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id")
    private User operator;

    /** 导流情况说明 */
    @Column(length = 1000)
    private String trafficNote;

    /** 营销内容 */
    @Column(length = 2000)
    private String content;

    private LocalDateTime createdAt;
}
