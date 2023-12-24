package com.example.springdatabasicdemo.controllers;

import com.example.springdatabasicdemo.models.Model;
import com.example.springdatabasicdemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class WebController {
    public UserService userService;
    @Autowired
    public void SetUserService(UserService userService){this.userService=userService;}
    @GetMapping("/index")
    public String index(@AuthenticationPrincipal UserDetails userDetails, ModelMap model) {
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }
        return "index";
    }
}
