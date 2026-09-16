package com.tourist.service.web;

import com.tourist.service.domain.*;
import com.tourist.service.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;
    private final UserService userService;
    private final UploadService uploadService;
    private final CurrentUser currentUser;

    // ---------- 游客 ----------

    @PreAuthorize("hasRole('TOURIST')")
    @GetMapping("/complaint/new")
    public String newComplaint(Model model) {
        model.addAttribute("complaint", new Complaint());
        return "complaint/form";
    }

    @PreAuthorize("hasRole('TOURIST')")
    @PostMapping("/complaint/new")
    public String submit(@RequestParam String title,
                         @RequestParam String content,
                         @RequestParam(value = "image", required = false) MultipartFile image,
                         @RequestParam(value = "video", required = false) MultipartFile video) {
        complaintService.submit(currentUser.id(), title, content,
                uploadService.save(image), uploadService.save(video));
        return "redirect:/complaint/mine";
    }

    @PreAuthorize("hasRole('TOURIST')")
    @GetMapping("/complaint/mine")
    public String mine(Model model) {
        model.addAttribute("complaints", complaintService.listForTourist(currentUser.id()));
        return "complaint/mine";
    }

    @GetMapping("/complaint/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Complaint c = complaintService.getById(id);
        User u = currentUser.get();
        boolean owner = u != null && c.getTourist() != null && c.getTourist().getId().equals(u.getId());
        model.addAttribute("c", c);
        model.addAttribute("replies", complaintService.getReplies(id));
        model.addAttribute("handling", complaintService.getHandling(id));
        model.addAttribute("feedback", complaintService.getFeedback(id));
        model.addAttribute("owner", owner);
        model.addAttribute("canRate", owner && c.getStatus() == ComplaintStatus.HANDLED);
        return "complaint/detail";
    }

    @PreAuthorize("hasRole('TOURIST')")
    @PostMapping("/complaint/{id}/reply")
    public String reply(@PathVariable Long id, @RequestParam String content) {
        complaintService.reply(id, currentUser.id(), content);
        return "redirect:/complaint/" + id;
    }

    @PreAuthorize("hasRole('TOURIST')")
    @PostMapping("/complaint/{id}/rate")
    public String rate(@PathVariable Long id,
                       @RequestParam(required = false, defaultValue = "5") Integer rating,
                       @RequestParam(required = false) String feedback) {
        complaintService.confirmAndRate(id, currentUser.id(), rating, feedback);
        return "redirect:/complaint/" + id;
    }

    // ---------- 平台管理人员 ----------

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @GetMapping("/complaint/list-all")
    public String listAll(Model model) {
        model.addAttribute("complaints", complaintService.listAll());
        return "complaint/listAll";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @GetMapping("/complaint/approval")
    public String approvalList(Model model) {
        model.addAttribute("complaints", complaintService.listPendingApproval());
        return "complaint/approvalList";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @GetMapping("/complaint/{id}/approve")
    public String approveForm(@PathVariable Long id, Model model) {
        model.addAttribute("c", complaintService.getById(id));
        model.addAttribute("handlers", userService.listByRole(Role.COMPLAINT_HANDLER));
        return "complaint/approveForm";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @PostMapping("/complaint/{id}/approve")
    public String approve(@PathVariable Long id,
                          @RequestParam(required = false) Long handlerId,
                          @RequestParam String remark) {
        complaintService.approve(id, currentUser.id(), handlerId, remark);
        return "redirect:/complaint/approval";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @PostMapping("/complaint/{id}/reject")
    public String reject(@PathVariable Long id, @RequestParam String remark) {
        complaintService.reject(id, currentUser.id(), remark);
        return "redirect:/complaint/approval";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @GetMapping("/complaint/closing")
    public String closingList(Model model) {
        model.addAttribute("complaints", complaintService.listForClosing());
        return "complaint/closingList";
    }

    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    @PostMapping("/complaint/{id}/close")
    public String close(@PathVariable Long id) {
        complaintService.close(id, currentUser.id());
        return "redirect:/complaint/closing";
    }

    // ---------- 投诉处理人员 ----------

    @PreAuthorize("hasRole('COMPLAINT_HANDLER')")
    @GetMapping("/complaint/handling")
    public String handlingList(Model model) {
        model.addAttribute("complaints", complaintService.listForHandler(currentUser.id()));
        return "complaint/handlingList";
    }

    @PreAuthorize("hasRole('COMPLAINT_HANDLER')")
    @GetMapping("/complaint/{id}/handle")
    public String handleForm(@PathVariable Long id, Model model) {
        model.addAttribute("c", complaintService.getById(id));
        return "complaint/handleForm";
    }

    @PreAuthorize("hasRole('COMPLAINT_HANDLER')")
    @PostMapping("/complaint/{id}/handle")
    public String handle(@PathVariable Long id,
                         @RequestParam String handleOpinion,
                         @RequestParam String handleResult,
                         @RequestParam(value = "image", required = false) MultipartFile image,
                         @RequestParam(value = "video", required = false) MultipartFile video) {
        complaintService.handle(id, currentUser.id(), handleOpinion, handleResult,
                uploadService.save(image), uploadService.save(video));
        return "redirect:/complaint/handling";
    }
}
