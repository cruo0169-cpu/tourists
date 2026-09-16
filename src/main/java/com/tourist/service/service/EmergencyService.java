package com.tourist.service.service;

import com.tourist.service.domain.EmergencyInfo;
import com.tourist.service.domain.EmergencyStatus;
import com.tourist.service.domain.User;
import com.tourist.service.repo.EmergencyInfoRepository;
import com.tourist.service.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmergencyService {

    private final EmergencyInfoRepository repository;
    private final UserRepository userRepository;

    @Transactional
    public EmergencyInfo publish(Long publisherId, String title, String content, String category,
                                 LocalDate validFrom, LocalDate validTo) {
        EmergencyInfo info = EmergencyInfo.builder()
                .title(title)
                .content(content)
                .category(category)
                .validFrom(validFrom)
                .validTo(validTo)
                .status(EmergencyStatus.PENDING_APPROVAL)
                .publisher(userRepository.getReferenceById(publisherId))
                .createdAt(LocalDateTime.now())
                .build();
        return repository.save(info);
    }

    @Transactional
    public EmergencyInfo update(Long id, String title, String content, String category,
                                LocalDate validFrom, LocalDate validTo) {
        EmergencyInfo info = get(id);
        info.setTitle(title);
        info.setContent(content);
        info.setCategory(category);
        info.setValidFrom(validFrom);
        info.setValidTo(validTo);
        info.setStatus(EmergencyStatus.PENDING_APPROVAL);
        info.setApprover(null);
        info.setApproveRemark(null);
        return repository.save(info);
    }

    @Transactional
    public EmergencyInfo approve(Long id, Long approverId, String remark) {
        EmergencyInfo info = get(id);
        info.setStatus(EmergencyStatus.PUBLISHED);
        info.setApprover(userRepository.getReferenceById(approverId));
        info.setApproveRemark(remark);
        info.setPublishedAt(LocalDateTime.now());
        return repository.save(info);
    }

    @Transactional
    public EmergencyInfo reject(Long id, Long approverId, String remark) {
        EmergencyInfo info = get(id);
        info.setStatus(EmergencyStatus.REJECTED);
        info.setApprover(userRepository.getReferenceById(approverId));
        info.setApproveRemark(remark);
        return repository.save(info);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(get(id));
    }

    public List<EmergencyInfo> queryPublished(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return repository.findByStatusOrderByCreatedAtDesc(EmergencyStatus.PUBLISHED);
        }
        return repository.findByStatusAndTitleContainingIgnoreCaseOrStatusAndContentContainingIgnoreCase(
                EmergencyStatus.PUBLISHED, keyword, EmergencyStatus.PUBLISHED, keyword);
    }

    /** 平台管理人员通过关键字查询 */
    public List<EmergencyInfo> listByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return repository.findAllByOrderByCreatedAtDesc();
        }
        return repository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);
    }

    public List<EmergencyInfo> listAll() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    public List<EmergencyInfo> listPendingApproval() {
        return repository.findByStatusOrderByCreatedAtDesc(EmergencyStatus.PENDING_APPROVAL);
    }

    public EmergencyInfo get(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("应急信息不存在"));
    }
}