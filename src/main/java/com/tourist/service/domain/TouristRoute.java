package com.tourist.service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * 旅游线路。
 */
@Entity
@Table(name = "tourist_route")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TouristRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    /** 途经景点，逗号分隔 */
    @Column(length = 500)
    private String spots;

    @Column(length = 100)
    private String duration;

    private BigDecimal price;

    @Column(length = 2000)
    private String description;
}
