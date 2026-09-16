package com.tourist.service.repo;

import com.tourist.service.domain.Consultation;
import com.tourist.service.domain.ConsultationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByTouristIdOrderByCreatedAtDesc(Long touristId);
    List<Consultation> findAllByOrderByCreatedAtDesc();
    List<Consultation> findByStatusOrderByCreatedAtDesc(ConsultationStatus status);
}
