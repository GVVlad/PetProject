package com.petproject.controller;

import com.petproject.security.SecurityUser;
import com.petproject.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    private final UserService userService;
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "home"})
    public String home(@AuthenticationPrincipal SecurityUser securityUser) {
        if (securityUser.getRole().equals("ADMIN")) {
            return "redirect:/users/all";
        }
        else {
            return "redirect:/todos/all/users/" + securityUser.getId();
        }
    }
}
