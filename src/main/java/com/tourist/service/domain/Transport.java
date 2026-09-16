package com.tourist.service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * 旅游交通车信息。
 */
@Entity
@Table(name = "transport")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    /** 类型：观光车 / 旅游大巴 / 游船 / 索道等 */
    @Column(length = 50)
    private String type;

    /** 运行线路 */
    @Column(length = 300)
    private String route;

    /** 班次/发车时间 */
    @Column(length = 100)
    private String schedule;

    /** 班次间隔 */
    @Column(length = 50)
    private String frequency;

    private BigDecimal price;

    @Column(length = 1000)
    private String description;
}