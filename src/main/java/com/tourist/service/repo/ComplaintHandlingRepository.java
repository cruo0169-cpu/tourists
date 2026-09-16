package com.tourist.service.repo;

import com.tourist.service.domain.ComplaintHandling;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComplaintHandlingRepository extends JpaRepository<ComplaintHandling, Long> {
    Optional<ComplaintHandling> findByComplaintId(Long complaintId);
}
