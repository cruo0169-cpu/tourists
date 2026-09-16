package com.tourist.service.web;

import com.tourist.service.domain.HotelType;
import com.tourist.service.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;
    private final CurrentUser currentUser;

    // ---------- 游客查询 ----------

    @GetMapping("/hotel/star")
    public String star(@RequestParam(required = false) String name, Model model) {
        model.addAttribute("hotels", hotelService.queryByName(HotelType.STAR, name));
        model.addAttribute("type", HotelType.STAR);
        model.addAttribute("keyword", name);
        return "hotel/list";
    }

    @GetMapping("/hotel/non-star")
    public String nonStar(@RequestParam(required = false) String name, Model model) {
        model.addAttribute("hotels", hotelService.queryByName(HotelType.NON_STAR, name));
        model.addAttribute("type", HotelType.NON_STAR);
        model.addAttribute("keyword", name);
        return "hotel/list";
    }

    @GetMapping("/hotel/rural")
    public String rural(@RequestParam(required = false) String name, Model model) {
        model.addAttribute("hotels", hotelService.queryByName(HotelType.RURAL, name));
        model.addAttribute("type", HotelType.RURAL);
        model.addAttribute("keyword", name);
        return "hotel/list";
    }

    @GetMapping("/hotel/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("hotel", hotelService.getById(id));
        model.addAttribute("rooms", hotelService.listRooms(id));
        model.addAttribute("marketings", hotelService.listMarketing(id));
        return "hotel/detail";
    }

    // ---------- 酒店管理人员（房间信息） ----------

    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @GetMapping("/hotel/manager")
    public String manager(Model model) {
        model.addAttribute("starHotels", hotelService.listByType(HotelType.STAR));
        model.addAttribute("nonStarHotels", hotelService.listByType(HotelType.NON_STAR));
        model.addAttribute("ruralHotels", hotelService.listByType(HotelType.RURAL));
        return "hotel/manager";
    }

    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @GetMapping("/hotel/{id}/rooms")
    public String rooms(@PathVariable Long id, Model model) {
        model.addAttribute("hotel", hotelService.getById(id));
        model.addAttribute("rooms", hotelService.listRooms(id));
        return "hotel/rooms";
    }

    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @GetMapping("/hotel/{id}/room/new")
    public String newRoom(@PathVariable Long id, Model model) {
        model.addAttribute("hotel", hotelService.getById(id));
        return "hotel/roomForm";
    }

    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @PostMapping("/hotel/{id}/room/new")
    public String saveRoom(@PathVariable Long id,
                           @RequestParam String roomType,
                           @RequestParam BigDecimal price,
                           @RequestParam Integer totalRooms,
                           @RequestParam Integer availableRooms) {
        hotelService.saveRoom(id, roomType, price, totalRooms, availableRooms, currentUser.id());
        return "redirect:/hotel/" + id + "/rooms";
    }

    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @GetMapping("/room/{roomId}/edit")
    public String editRoom(@PathVariable Long roomId, Model model) {
        model.addAttribute("room", hotelService.getRoom(roomId));
        return "hotel/roomEditForm";
    }

    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @PostMapping("/room/{roomId}/edit")
    public String updateRoom(@PathVariable Long roomId,
                             @RequestParam String roomType,
                             @RequestParam BigDecimal price,
                             @RequestParam Integer totalRooms,
                             @RequestParam Integer availableRooms) {
        Long hotelId = hotelService.getRoom(roomId).getHotel().getId();
        hotelService.updateRoom(roomId, roomType, price, totalRooms, availableRooms, currentUser.id());
        return "redirect:/hotel/" + hotelId + "/rooms";
    }

    @PreAuthorize("hasRole('HOTEL_MANAGER')")
    @PostMapping("/room/{roomId}/delete")
    public String deleteRoom(@PathVariable Long roomId) {
        Long hotelId = hotelService.getRoom(roomId).getHotel().getId();
        hotelService.deleteRoom(roomId);
        return "redirect:/hotel/" + hotelId + "/rooms";
    }

    // ---------- 平台管理人员（营销） ----------

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @GetMapping("/hotel/marketing")
    public String marketing(Model model) {
        model.addAttribute("starHotels", hotelService.listByType(HotelType.STAR));
        model.addAttribute("nonStarHotels", hotelService.listByType(HotelType.NON_STAR));
        model.addAttribute("ruralHotels", hotelService.listByType(HotelType.RURAL));
        return "hotel/marketing";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @PostMapping("/hotel/{id}/marketing")
    public String addMarketing(@PathVariable Long id,
                               @RequestParam String trafficNote,
                               @RequestParam String content) {
        hotelService.addMarketing(id, currentUser.id(), trafficNote, content);
        return "redirect:/hotel/" + id;
    }
}