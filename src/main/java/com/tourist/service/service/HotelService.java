package com.tourist.service.service;

import com.tourist.service.domain.*;
import com.tourist.service.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomInfoRepository roomInfoRepository;
    private final HotelMarketingRepository marketingRepository;
    private final UserRepository userRepository;

    public List<Hotel> listByType(HotelType type) {
        return hotelRepository.findByHotelTypeOrderById(type);
    }

    public List<Hotel> queryByName(HotelType type, String name) {
        if (name == null || name.isBlank()) {
            return listByType(type);
        }
        return hotelRepository.findByHotelTypeAndNameContainingIgnoreCase(type, name);
    }

    public Hotel getById(Long id) {
        return hotelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("酒店不存在"));
    }

    public List<RoomInfo> listRooms(Long hotelId) {
        return roomInfoRepository.findByHotelId(hotelId);
    }

    public RoomInfo getRoom(Long roomId) {
        return roomInfoRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("房间信息不存在"));
    }

    @Transactional
    public RoomInfo saveRoom(Long hotelId, String roomType, BigDecimal price, Integer totalRooms,
                             Integer availableRooms, Long operatorId) {
        Hotel hotel = getById(hotelId);
        RoomInfo room = RoomInfo.builder()
                .hotel(hotel)
                .roomType(roomType)
                .price(price)
                .totalRooms(totalRooms)
                .availableRooms(availableRooms)
                .updatedBy(userRepository.getReferenceById(operatorId))
                .updatedAt(LocalDateTime.now())
                .build();
        return roomInfoRepository.save(room);
    }

    @Transactional
    public RoomInfo updateRoom(Long roomId, String roomType, BigDecimal price, Integer totalRooms,
                               Integer availableRooms, Long operatorId) {
        RoomInfo room = getRoom(roomId);
        room.setRoomType(roomType);
        room.setPrice(price);
        room.setTotalRooms(totalRooms);
        room.setAvailableRooms(availableRooms);
        room.setUpdatedBy(userRepository.getReferenceById(operatorId));
        room.setUpdatedAt(LocalDateTime.now());
        return roomInfoRepository.save(room);
    }

    @Transactional
    public void deleteRoom(Long roomId) {
        roomInfoRepository.deleteById(roomId);
    }

    public List<HotelMarketing> listMarketing(Long hotelId) {
        return marketingRepository.findByHotelIdOrderByCreatedAtDesc(hotelId);
    }

    @Transactional
    public HotelMarketing addMarketing(Long hotelId, Long operatorId, String trafficNote, String content) {
        HotelMarketing m = HotelMarketing.builder()
                .hotel(getById(hotelId))
                .operator(userRepository.getReferenceById(operatorId))
                .trafficNote(trafficNote)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
        return marketingRepository.save(m);
    }
}