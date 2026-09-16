package com.tourist.service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * 旅游景区（点）。
 */
@Entity
@Table(name = "scenic_spot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScenicSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 50)
    private String category;

    @Column(length = 200)
    private String location;

    @Column(length = 50)
    private String openTime;

    private BigDecimal ticketPrice;

    @Column(length = 2000)
    private String description;

    private String image;
}
