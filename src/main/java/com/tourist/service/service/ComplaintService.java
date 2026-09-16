package com.tourist.service.service;

import com.tourist.service.domain.*;
import com.tourist.service.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final ComplaintReplyRepository replyRepository;
    private final ComplaintHandlingRepository handlingRepository;
    private final ComplaintFeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    @Transactional
    public Complaint submit(Long touristId, String title, String content, String images, String video) {
        User tourist = userRepository.getReferenceById(touristId);
        Complaint complaint = Complaint.builder()
                .tourist(tourist)
                .title(title)
                .content(content)
                .images(images)
                .video(video)
                .status(ComplaintStatus.PENDING_APPROVAL)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return complaintRepository.save(complaint);
    }

    @Transactional
    public ComplaintReply reply(Long complaintId, Long userId, String content) {
        Complaint complaint = complaintRepository.getReferenceById(complaintId);
        User user = userRepository.getReferenceById(userId);
        ComplaintReply reply = ComplaintReply.builder()
                .complaint(complaint)
                .user(user)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
        return replyRepository.save(reply);
    }

    @Transactional
    public Complaint approve(Long complaintId, Long approverId, Long handlerId, String remark) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new IllegalArgumentException("投诉不存在"));
        complaint.setStatus(ComplaintStatus.IN_HANDLING);
        complaint.setApprover(userRepository.getReferenceById(approverId));
        if (handlerId != null) {
            complaint.setHandler(userRepository.getReferenceById(handlerId));
        }
        complaint.setApproveRemark(remark);
        complaint.setUpdatedAt(LocalDateTime.now());
        return complaintRepository.save(complaint);
    }

    @Transactional
    public Complaint reject(Long complaintId, Long approverId, String remark) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new IllegalArgumentException("投诉不存在"));
        complaint.setStatus(ComplaintStatus.REJECTED);
        complaint.setApprover(userRepository.getReferenceById(approverId));
        complaint.setApproveRemark(remark);
        complaint.setUpdatedAt(LocalDateTime.now());
        return complaintRepository.save(complaint);
    }

    @Transactional
    public ComplaintHandling handle(Long complaintId, Long handlerId, String handleOpinion,
                                    String handleResult, String images, String video) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new IllegalArgumentException("投诉不存在"));
        ComplaintHandling handling = ComplaintHandling.builder()
                .complaint(complaint)
                .handler(userRepository.getReferenceById(handlerId))
                .handleOpinion(handleOpinion)
                .handleResult(handleResult)
                .images(images)
                .video(video)
                .handledAt(LocalDateTime.now())
                .build();
        handling = handlingRepository.save(handling);
        complaint.setStatus(ComplaintStatus.HANDLED);
        complaint.setUpdatedAt(LocalDateTime.now());
        complaintRepository.save(complaint);
        return handling;
    }

    @Transactional
    public Complaint confirmAndRate(Long complaintId, Long touristId, Integer rating, String feedback) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new IllegalArgumentException("投诉不存在"));
        complaint.setStatus(ComplaintStatus.CONFIRMED);
        complaint.setUpdatedAt(LocalDateTime.now());
        complaintRepository.save(complaint);

        Optional<ComplaintFeedback> existing = feedbackRepository.findByComplaintId(complaintId);
        ComplaintFeedback fb = existing.orElseGet(ComplaintFeedback::new);
        fb.setComplaint(complaint);
        fb.setRating(rating);
        fb.setFeedback(feedback);
        fb.setRatedAt(LocalDateTime.now());
        feedbackRepository.save(fb);
        return complaint;
    }

    @Transactional
    public Complaint close(Long complaintId, Long managerId) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new IllegalArgumentException("投诉不存在"));
        complaint.setStatus(ComplaintStatus.CLOSED);
        complaint.setUpdatedAt(LocalDateTime.now());
        return complaintRepository.save(complaint);
    }

    public List<Complaint> listForTourist(Long touristId) {
        return complaintRepository.findByTouristIdOrderByCreatedAtDesc(touristId);
    }

    public List<Complaint> listPendingApproval() {
        return complaintRepository.findByStatusOrderByCreatedAtDesc(ComplaintStatus.PENDING_APPROVAL);
    }

    public List<Complaint> listForClosing() {
        return complaintRepository.findByStatusOrderByCreatedAtDesc(ComplaintStatus.CONFIRMED);
    }

    public List<Complaint> listForHandler(Long handlerId) {
        return complaintRepository.findByHandlerIdOrderByCreatedAtDesc(handlerId);
    }

    public List<Complaint> listAll() {
        return complaintRepository.findAllByOrderByCreatedAtDesc();
    }

    public Complaint getById(Long id) {
        return complaintRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("投诉不存在"));
    }

    public List<ComplaintReply> getReplies(Long complaintId) {
        return replyRepository.findByComplaintIdOrderByCreatedAtAsc(complaintId);
    }

    public ComplaintHandling getHandling(Long complaintId) {
        return handlingRepository.findByComplaintId(complaintId).orElse(null);
    }

    public ComplaintFeedback getFeedback(Long complaintId) {
        return feedbackRepository.findByComplaintId(complaintId).orElse(null);
    }

    public List<ComplaintStatus> statuses() {
        return List.of(ComplaintStatus.values());
    }
}
