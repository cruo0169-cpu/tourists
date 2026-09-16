package com.tourist.service.repo;

import com.tourist.service.domain.ScenicSpot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScenicSpotRepository extends JpaRepository<ScenicSpot, Long> {
    List<ScenicSpot> findAllByOrderById();
    List<ScenicSpot> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String name, String category);
}
