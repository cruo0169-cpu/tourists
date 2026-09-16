package com.tourist.service.repo;

import com.tourist.service.domain.CateringPlace;
import com.tourist.service.domain.CateringType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CateringPlaceRepository extends JpaRepository<CateringPlace, Long> {
    List<CateringPlace> findAllByOrderById();
    List<CateringPlace> findByTypeOrderById(CateringType type);
    List<CateringPlace> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String desc);
}