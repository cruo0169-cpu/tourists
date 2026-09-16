package com.tourist.service.repo;

import com.tourist.service.domain.HotelMarketing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelMarketingRepository extends JpaRepository<HotelMarketing, Long> {
    List<HotelMarketing> findByHotelIdOrderByCreatedAtDesc(Long hotelId);
    List<HotelMarketing> findAllByOrderByCreatedAtDesc();
}
