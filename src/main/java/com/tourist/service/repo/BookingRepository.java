package com.tourist.service.repo;

import com.tourist.service.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByTouristIdOrderByCreatedAtDesc(Long touristId);
    List<Booking> findByRoomHotelIdOrderByCreatedAtDesc(Long hotelId);
    List<Booking> findAllByOrderByCreatedAtDesc();
}