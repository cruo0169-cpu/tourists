package com.tourist.service.service;

import com.tourist.service.domain.Consultation;
import com.tourist.service.domain.ConsultationStatus;
import com.tourist.service.domain.User;
import com.tourist.service.repo.ConsultationRepository;
import com.tourist.service.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository repository;
    private final UserRepository userRepository;

    @Transactional
    public Consultation submit(Long touristId, String title, String content) {
        Consultation c = Consultation.builder()
                .tourist(userRepository.getReferenceById(touristId))
                .title(title)
                .content(content)
                .status(ConsultationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        return repository.save(c);
    }

    @Transactional
    public Consultation answer(Long id, Long answererId, String answer) {
        Consultation c = get(id);
        c.setAnswer(answer);
        c.setAnswerer(userRepository.getReferenceById(answererId));
        c.setStatus(ConsultationStatus.ANSWERED);
        c.setAnsweredAt(LocalDateTime.now());
        return repository.save(c);
    }

    public List<Consultation> listForTourist(Long touristId) {
        return repository.findByTouristIdOrderByCreatedAtDesc(touristId);
    }

    public List<Consultation> listAll() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    public List<Consultation> listPending() {
        return repository.findByStatusOrderByCreatedAtDesc(ConsultationStatus.PENDING);
    }

    public Consultation get(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("咨询不存在"));
    }
}
