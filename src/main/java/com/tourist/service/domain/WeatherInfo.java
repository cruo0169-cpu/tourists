package com.tourist.service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * 天气状况。
 */
@Entity
@Table(name = "weather_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String area;

    private LocalDate forecastDate;

    @Column(length = 50)
    private String weather;

    @Column(length = 20)
    private String temperature;

    @Column(length = 20)
    private String humidity;

    @Column(length = 20)
    private String wind;

    @Column(length = 500)
    private String suggestion;
}
