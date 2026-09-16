package com.tourist.service.repo;

import com.tourist.service.domain.EmergencyInfo;
import com.tourist.service.domain.EmergencyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmergencyInfoRepository extends JpaRepository<EmergencyInfo, Long> {
    List<EmergencyInfo> findAllByOrderByCreatedAtDesc();
    List<EmergencyInfo> findByStatusOrderByCreatedAtDesc(EmergencyStatus status);
    List<EmergencyInfo> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
    List<EmergencyInfo> findByStatusAndTitleContainingIgnoreCaseOrStatusAndContentContainingIgnoreCase(
            EmergencyStatus s1, String title, EmergencyStatus s2, String content);
}