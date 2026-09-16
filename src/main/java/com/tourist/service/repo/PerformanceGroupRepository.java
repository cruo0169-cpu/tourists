package com.tourist.service.repo;

import com.tourist.service.domain.PerformanceGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceGroupRepository extends JpaRepository<PerformanceGroup, Long> {
    List<PerformanceGroup> findAllByOrderById();
    List<PerformanceGroup> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String name, String category);
}
