package com.tourist.service.repo;

import com.tourist.service.domain.Hotel;
import com.tourist.service.domain.HotelType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByHotelTypeOrderById(HotelType hotelType);
    List<Hotel> findByHotelTypeAndNameContainingIgnoreCase(HotelType hotelType, String name);
}
