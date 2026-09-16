package com.tourist.service.repo;

import com.tourist.service.domain.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, Long> {
    List<WeatherInfo> findAllByOrderByForecastDateAsc();
    List<WeatherInfo> findByAreaContainingIgnoreCase(String area);
}