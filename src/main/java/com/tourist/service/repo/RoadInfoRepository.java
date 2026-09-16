package com.tourist.service.repo;

import com.tourist.service.domain.RoadInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoadInfoRepository extends JpaRepository<RoadInfo, Long> {
    List<RoadInfo> findAllByOrderByUpdatedAtDesc();
    List<RoadInfo> findByRoadNameContainingIgnoreCaseOrSectionContainingIgnoreCase(String road, String section);
}
