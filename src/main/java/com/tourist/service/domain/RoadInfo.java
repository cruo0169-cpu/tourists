package com.tourist.service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 路况信息。
 */
@Entity
@Table(name = "road_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoadInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String roadName;

    @Column(length = 200)
    private String section;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RoadStatus status;

    @Column(length = 1000)
    private String description;

    private LocalDateTime updatedAt;
}
