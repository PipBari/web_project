package com.example.springdatabasicdemo.controllers;

import com.example.springdatabasicdemo.dtos.OfferDto;
import com.example.springdatabasicdemo.services.OfferService;
import com.example.springdatabasicdemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class WebController {
    private final UserService userService;
    private final OfferService offerService;

    @Autowired
    public WebController(UserService userService, OfferService offerService) {
        this.userService = userService;
        this.offerService=offerService;
    }

    @GetMapping("/index")
    public String index(@AuthenticationPrincipal UserDetails userDetails, ModelMap model) {
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }
        return "index";
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/adminboard")
    public String adminBoard(@AuthenticationPrincipal UserDetails userDetails, ModelMap model) {
        if (userDetails != null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            model.addAttribute("username", userDetails.getUsername());
            List<UUID> offerIds = offerService.getAllOfferIds();
            model.addAttribute("offerIds", offerIds);
            return "adminboard";
        } else {
            return "redirect:/index";
        }
    }
    @PostMapping("/deleteOffer/{id}")
    public String deleteOffer(@PathVariable UUID id) {
        offerService.delete(id);
        return "redirect:/adminboard";
    }

    @GetMapping("/viewOffer/{id}")
    public String viewOffer(@PathVariable UUID id, Model model) {
        Optional<OfferDto> offerDto = offerService.findOffer(id);
        if (!offerDto.isPresent()) {
            return "redirect:/errorPage";
        }
        model.addAttribute("offer", offerDto.get());
        return "offers/view";
    }
}

