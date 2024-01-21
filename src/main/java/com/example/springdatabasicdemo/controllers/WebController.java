package com.example.springdatabasicdemo.controllers;

import com.example.springdatabasicdemo.dtos.BrandDto;
import com.example.springdatabasicdemo.dtos.UserDto;
import com.example.springdatabasicdemo.dtos.UserRoleDto;
import com.example.springdatabasicdemo.services.*;
import com.example.springdatabasicdemo.services.impl.BrandServiceImpl;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
public class WebController {
    private final UserRoleService userRoleService;
    private final UserService userService;
    private final BrandService brandService;
    private final OfferService offerService;
    private final ModelService modelService;

    @Autowired
    public WebController(UserRoleService userRoleService,UserService userService, BrandService brandService ,OfferService offerService, ModelService modelService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.brandService = brandService;
        this.offerService = offerService;
        this.modelService = modelService;
    }

    @GetMapping("/index")
    public String index(@AuthenticationPrincipal UserDetails userDetails, ModelMap model) {
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }
        return "index";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/adminboard")
    public String adminBoard(@AuthenticationPrincipal UserDetails userDetails, ModelMap model) {
        if (userDetails != null && userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            model.addAttribute("username", userDetails.getUsername());
            List<UUID> offerIds = offerService.getAllOfferIds();
            model.addAttribute("offerIds", offerIds);
            return "admins/adminboard";
        } else {
            return "redirect:/index";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/adminboard/users")
    public String listUsers(Model model) {
        List<UserDto> users = userService.getAll();
        List<UserRoleDto> roles = userRoleService.getAll();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "admins/users";
    }

    @PostMapping("/adminboard/updateUserRole")
    public String updateUserRole(@RequestParam UUID userId, @RequestParam UUID roleId, RedirectAttributes redirectAttributes) {
        userService.updateUserRole(userId, roleId);
        redirectAttributes.addFlashAttribute("successMessage", "Role updated successfully");
        return "redirect:/adminboard/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/adminboard/models")
    public String listModels(Model model) {
        model.addAttribute("models", modelService.getAll());
        return "admins/models";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/deleteModel/{id}")
    public String deleteModel(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        try {
            modelService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Модель успешно удалена");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при удалении модели");
        }
        return "redirect:/adminboard/models";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/adminboard/brands")
    public String listBrands(Model model) {
        List<BrandDto> brands = brandService.getAll();
        model.addAttribute("brands", brands);
        return "admins/brands";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/deleteBrand/{id}")
    public String deleteBrand(@PathVariable UUID id) {
        brandService.delete(id);
        return "redirect:/adminboard/brands";
    }

}

