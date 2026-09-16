package com.tourist.service.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * 营业性演出团体。
 */
@Entity
@Table(name = "performance_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 50)
    private String category;

    @Column(length = 200)
    private String address;

    @Column(length = 50)
    private String contact;

    @Column(length = 2000)
    private String description;
}
