package com.tourist.service.web;

import com.tourist.service.domain.EmergencyInfo;
import com.tourist.service.service.EmergencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class EmergencyController {

    private final EmergencyService emergencyService;
    private final CurrentUser currentUser;

    /** 游客查询应急信息 */
    @GetMapping("/emergency")
    public String query(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("infos", emergencyService.queryPublished(keyword));
        model.addAttribute("keyword", keyword);
        return "emergency/list";
    }

    // ---------- 平台管理人员（发布/修改/删除/查询） ----------

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @GetMapping("/emergency/manage")
    public String manage(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("infos", emergencyService.listByKeyword(keyword));
        model.addAttribute("keyword", keyword);
        return "emergency/manage";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @GetMapping("/emergency/new")
    public String newForm(Model model) {
        model.addAttribute("info", new EmergencyInfo());
        return "emergency/form";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @PostMapping("/emergency/new")
    public String create(@RequestParam String title,
                         @RequestParam String content,
                         @RequestParam(required = false) String category,
                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate validFrom,
                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate validTo) {
        emergencyService.publish(currentUser.id(), title, content, category, validFrom, validTo);
        return "redirect:/emergency/manage";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @GetMapping("/emergency/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("info", emergencyService.get(id));
        return "emergency/form";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @PostMapping("/emergency/{id}/edit")
    public String edit(@PathVariable Long id,
                       @RequestParam String title,
                       @RequestParam String content,
                       @RequestParam(required = false) String category,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate validFrom,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate validTo) {
        emergencyService.update(id, title, content, category, validFrom, validTo);
        return "redirect:/emergency/manage";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @PostMapping("/emergency/{id}/delete")
    public String delete(@PathVariable Long id) {
        emergencyService.delete(id);
        return "redirect:/emergency/manage";
    }

    // ---------- 审批人员 ----------

    @PreAuthorize("hasRole('APPROVER')")
    @GetMapping("/emergency/approval")
    public String approvalList(Model model) {
        model.addAttribute("infos", emergencyService.listPendingApproval());
        return "emergency/approvalList";
    }

    @PreAuthorize("hasRole('APPROVER')")
    @GetMapping("/emergency/{id}/approve")
    public String approveForm(@PathVariable Long id, Model model) {
        model.addAttribute("info", emergencyService.get(id));
        return "emergency/approveForm";
    }

    @PreAuthorize("hasRole('APPROVER')")
    @PostMapping("/emergency/{id}/approve")
    public String approve(@PathVariable Long id, @RequestParam(required = false) String remark) {
        emergencyService.approve(id, currentUser.id(), remark);
        return "redirect:/emergency/approval";
    }

    @PreAuthorize("hasRole('APPROVER')")
    @PostMapping("/emergency/{id}/reject")
    public String reject(@PathVariable Long id, @RequestParam(required = false) String remark) {
        emergencyService.reject(id, currentUser.id(), remark);
        return "redirect:/emergency/approval";
    }
}