package com.tourist.service.service;

import com.tourist.service.domain.*;
import com.tourist.service.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomInfoRepository roomInfoRepository;
    private final UserRepository userRepository;

    @Transactional
    public Booking create(Long roomId, Long touristId, String guestName, String phone,
                          LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null || !checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("入住日期需晚于退房日期");
        }
        RoomInfo room = roomInfoRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("房型不存在"));
        if (room.getAvailableRooms() != null && room.getAvailableRooms() <= 0) {
            throw new IllegalArgumentException("该房型已满房");
        }
        Booking b = Booking.builder()
                .room(room)
                .tourist(userRepository.getReferenceById(touristId))
                .guestName(guestName)
                .phone(phone)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .status(BookingStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        return bookingRepository.save(b);
    }

    @Transactional
    public Booking confirm(Long id, Long managerId) {
        Booking b = get(id);
        RoomInfo room = b.getRoom();
        if (room.getAvailableRooms() != null && room.getAvailableRooms() <= 0) {
            throw new IllegalArgumentException("该房型已满房，无法确认");
        }
        b.setStatus(BookingStatus.CONFIRMED);
        b.setConfirmedBy(userRepository.getReferenceById(managerId));
        b.setConfirmedAt(LocalDateTime.now());
        if (room.getAvailableRooms() != null && room.getAvailableRooms() > 0) {
            room.setAvailableRooms(room.getAvailableRooms() - 1);
            room.setUpdatedAt(LocalDateTime.now());
            roomInfoRepository.save(room);
        }
        return bookingRepository.save(b);
    }

    @Transactional
    public Booking cancel(Long id) {
        Booking b = get(id);
        b.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(b);
    }

    public List<Booking> listForTourist(Long touristId) {
        return bookingRepository.findByTouristIdOrderByCreatedAtDesc(touristId);
    }

    public List<Booking> listForHotel(Long hotelId) {
        return bookingRepository.findByRoomHotelIdOrderByCreatedAtDesc(hotelId);
    }

    public List<Booking> listAll() {
        return bookingRepository.findAllByOrderByCreatedAtDesc();
    }

    public Booking get(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("预订不存在"));
    }
}