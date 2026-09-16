package com.tourist.service.repo;

import com.tourist.service.domain.ComplaintFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComplaintFeedbackRepository extends JpaRepository<ComplaintFeedback, Long> {
    Optional<ComplaintFeedback> findByComplaintId(Long complaintId);
}
