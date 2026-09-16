package com.tourist.service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 游客对投诉处理结果反馈与评分。
 */
@Entity
@Table(name = "complaint_feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    /** 满意度 1 - 5 */
    private Integer rating;

    @Column(length = 1000)
    private String feedback;

    private LocalDateTime ratedAt;
}
