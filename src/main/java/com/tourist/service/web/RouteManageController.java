package com.tourist.service.web;

import com.tourist.service.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class RouteManageController {

    private final RouteService routeService;

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @GetMapping("/routes/manage")
    public String manage(Model model) {
        model.addAttribute("routes", routeService.listAll());
        return "routes/manage";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @GetMapping("/routes/new")
    public String newForm(Model model) {
        model.addAttribute("route", new com.tourist.service.domain.TouristRoute());
        return "routes/form";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @PostMapping("/routes/new")
    public String create(@RequestParam String name,
                         @RequestParam String spots,
                         @RequestParam String duration,
                         @RequestParam BigDecimal price,
                         @RequestParam String description) {
        routeService.save(name, spots, duration, price, description);
        return "redirect:/routes/manage";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @GetMapping("/routes/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("route", routeService.get(id));
        return "routes/form";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @PostMapping("/routes/{id}/edit")
    public String edit(@PathVariable Long id,
                       @RequestParam String name,
                       @RequestParam String spots,
                       @RequestParam String duration,
                       @RequestParam BigDecimal price,
                       @RequestParam String description) {
        routeService.update(id, name, spots, duration, price, description);
        return "redirect:/routes/manage";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @PostMapping("/routes/{id}/delete")
    public String delete(@PathVariable Long id) {
        routeService.delete(id);
        return "redirect:/routes/manage";
    }
}