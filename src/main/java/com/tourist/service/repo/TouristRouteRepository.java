package com.tourist.service.repo;

import com.tourist.service.domain.TouristRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TouristRouteRepository extends JpaRepository<TouristRoute, Long> {
    List<TouristRoute> findAllByOrderById();
    List<TouristRoute> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String desc);
    List<TouristRoute> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrSpotsContainingIgnoreCase(
            String name, String desc, String spots);
}