package com.tourist.service.web;

import com.tourist.service.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAdvice {

    private final CurrentUser currentUser;

    @ModelAttribute
    public void addUser(Model model) {
        User u = currentUser.get();
        model.addAttribute("user", u);
        model.addAttribute("roleLabel", u == null ? null : u.getRole().getLabel());
    }
}
