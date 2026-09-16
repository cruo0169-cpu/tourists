package com.tourist.service.repo;

import com.tourist.service.domain.RoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomInfoRepository extends JpaRepository<RoomInfo, Long> {
    List<RoomInfo> findByHotelId(Long hotelId);
    Optional<RoomInfo> findByIdAndHotelId(Long id, Long hotelId);
}
