package com.tourist.service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * 餐饮娱乐场所。
 */
@Entity
@Table(name = "catering_place")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CateringPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CateringType type;

    @Column(length = 200)
    private String address;

    @Column(length = 50)
    private String phone;

    private BigDecimal avgPrice;

    @Column(length = 2000)
    private String description;

    private String image;
}
