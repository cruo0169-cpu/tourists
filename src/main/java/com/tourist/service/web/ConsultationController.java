package com.tourist.service.web;

import com.tourist.service.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;
    private final CurrentUser currentUser;

    /** 游客：我的咨询 */
    @PreAuthorize("hasRole('TOURIST')")
    @GetMapping("/consultation")
    public String list(Model model) {
        model.addAttribute("items", consultationService.listForTourist(currentUser.id()));
        return "consultation/list";
    }

    /** 游客：提交咨询 */
    @PreAuthorize("hasRole('TOURIST')")
    @GetMapping("/consultation/new")
    public String newForm() {
        return "consultation/form";
    }

    @PreAuthorize("hasRole('TOURIST')")
    @PostMapping("/consultation/new")
    public String submit(@RequestParam String title, @RequestParam String content) {
        consultationService.submit(currentUser.id(), title, content);
        return "redirect:/consultation";
    }

    /** 平台管理人员：咨询处理 */
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @GetMapping("/consultation/manage")
    public String manage(Model model) {
        model.addAttribute("items", consultationService.listAll());
        return "consultation/manage";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @GetMapping("/consultation/{id}/reply")
    public String replyForm(@PathVariable Long id, Model model) {
        model.addAttribute("c", consultationService.get(id));
        return "consultation/reply";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @PostMapping("/consultation/{id}/reply")
    public String reply(@PathVariable Long id, @RequestParam String answer) {
        consultationService.answer(id, currentUser.id(), answer);
        return "redirect:/consultation/manage";
    }
}
