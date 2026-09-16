package com.tourist.service.service;

import com.tourist.service.domain.*;
import com.tourist.service.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryService {

    private final ScenicSpotRepository spotRepository;
    private final TouristRouteRepository routeRepository;
    private final CateringPlaceRepository cateringRepository;
    private final PerformanceGroupRepository performanceRepository;
    private final WeatherInfoRepository weatherRepository;
    private final RoadInfoRepository roadRepository;
    private final TransportRepository transportRepository;

    public List<ScenicSpot> listSpots(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return spotRepository.findAllByOrderById();
        }
        return spotRepository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(keyword, keyword);
    }

    public List<TouristRoute> listRoutes(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return routeRepository.findAllByOrderById();
        }
        return routeRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrSpotsContainingIgnoreCase(
                keyword, keyword, keyword);
    }

    public TouristRoute getRoute(Long id) {
        return routeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("线路不存在"));
    }

    public List<CateringPlace> listCatering(CateringType type, String keyword) {
        if (type == null) {
            if (keyword == null || keyword.isBlank()) {
                return cateringRepository.findAllByOrderById();
            }
            return cateringRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
        }
        if (keyword == null || keyword.isBlank()) {
            return cateringRepository.findByTypeOrderById(type);
        }
        return cateringRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
                .stream().filter(c -> c.getType() == type).toList();
    }

    public List<PerformanceGroup> listPerformance(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return performanceRepository.findAllByOrderById();
        }
        return performanceRepository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(keyword, keyword);
    }

    public List<WeatherInfo> listWeather(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return weatherRepository.findAllByOrderByForecastDateAsc();
        }
        return weatherRepository.findByAreaContainingIgnoreCase(keyword);
    }

    public List<RoadInfo> listRoad(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return roadRepository.findAllByOrderByUpdatedAtDesc();
        }
        return roadRepository.findByRoadNameContainingIgnoreCaseOrSectionContainingIgnoreCase(keyword, keyword);
    }

    public List<Transport> listTransport(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return transportRepository.findAllByOrderById();
        }
        return transportRepository.findByNameContainingIgnoreCaseOrTypeContainingIgnoreCaseOrRouteContainingIgnoreCase(
                keyword, keyword, keyword);
    }
}