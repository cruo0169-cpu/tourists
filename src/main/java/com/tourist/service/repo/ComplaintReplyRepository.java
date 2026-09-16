package com.tourist.service.repo;

import com.tourist.service.domain.ComplaintReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintReplyRepository extends JpaRepository<ComplaintReply, Long> {
    List<ComplaintReply> findByComplaintIdOrderByCreatedAtAsc(Long complaintId);
}
