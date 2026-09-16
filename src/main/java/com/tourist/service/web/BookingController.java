package com.tourist.service.web;

import com.tourist.service.service.BookingService;
import com.tourist.service.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final HotelService hotelService;
    private final CurrentUser currentUser;

    /** 游客：我的入住 */
    @PreAuthorize("hasRole('TOURIST')")
    @GetMapping("/booking")
    public String myBookings(Model model) {
        model.addAttribute("items", bookingService.listForTourist(currentUser.id()));
        return "booking/list";
    }

    /** 游客：选择入住 */
    @PreAuthorize("hasRole('TOURIST')")
    @GetMapping("/room/{roomId}/booking/new")
    public String bookForm(@PathVariable Long roomId, Model model) {
        model.addAttribute("room", hotelService.getRoom(roomId));
        return "booking/form";
    }

    @PreAuthorize("hasRole('TOURIST')")
    @PostMapping("/room/{roomId}/booking/new")
    public String book(@PathVariable Long roomId,
                       @RequestParam String guestName,
                       @RequestParam String phone,
                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
                       Model model) {
        try {
            bookingService.create(roomId, currentUser.id(), guestName, phone, checkIn, checkOut);
            return "redirect:/booking";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("room", hotelService.getRoom(roomId));
            model.addAttribute("guestName", guestName);
            model.addAttribute("phone", phone);
            model.addAttribute("checkIn", checkIn);
            model.addAttribute("checkOut", checkOut);
            return "booking/form";
        }
    }

    @PreAuthorize("hasRole('TOURIST')")
    @PostMapping("/booking/{id}/cancel")
    public String cancel(@PathVariable Long id) {
        bookingService.cancel(id);
        return "redirect:/booking";
    }

    /** 酒店管理人员：入住管理 */
    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @GetMapping("/booking/manage")
    public String manage(Model model) {
        model.addAttribute("items", bookingService.listAll());
        return "booking/manage";
    }

    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @PostMapping("/booking/{id}/confirm")
    public String confirm(@PathVariable Long id) {
        bookingService.confirm(id, currentUser.id());
        return "redirect:/booking/manage";
    }

    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @PostMapping("/booking/{id}/reject")
    public String reject(@PathVariable Long id) {
        bookingService.cancel(id);
        return "redirect:/booking/manage";
    }
}