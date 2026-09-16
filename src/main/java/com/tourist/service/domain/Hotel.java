package com.tourist.service.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * 酒店（星级 / 非星级 / 乡村旅游）。
 */
@Entity
@Table(name = "hotel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private HotelType hotelType;

    /** 星级酒店星数，非星级为 null */
    private Integer starLevel;

    @Column(length = 200)
    private String address;

    @Column(length = 50)
    private String phone;

    @Column(length = 2000)
    private String description;

    private String image;
}
