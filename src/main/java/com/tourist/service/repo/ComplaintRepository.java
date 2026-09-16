package com.tourist.service.repo;

import com.tourist.service.domain.Complaint;
import com.tourist.service.domain.ComplaintStatus;
import com.tourist.service.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByTouristIdOrderByCreatedAtDesc(Long touristId);
    List<Complaint> findAllByOrderByCreatedAtDesc();
    List<Complaint> findByStatusOrderByCreatedAtDesc(ComplaintStatus status);
    List<Complaint> findByHandlerIdOrderByCreatedAtDesc(Long handlerId);
    List<Complaint> findByTourist(User tourist);
}
