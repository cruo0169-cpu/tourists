package com.tourist.service.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 投诉处理记录。
 */
@Entity
@Table(name = "complaint_handling")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplaintHandling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "handler_id")
    private User handler;

    /** 处理意见 */
    @Column(length = 2000)
    private String handleOpinion;

    /** 处理结果 */
    @Column(length = 2000)
    private String handleResult;

    /** 处理上传图片，逗号分隔 */
    @Column(length = 2000)
    private String images;

    private String video;

    private LocalDateTime handledAt;
}
