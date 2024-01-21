package com.example.springdatabasicdemo.controllers;

import com.example.springdatabasicdemo.dtos.UserDto;
import com.example.springdatabasicdemo.dtos.UserRoleDto;
import com.example.springdatabasicdemo.services.ModelService;
import com.example.springdatabasicdemo.services.OfferService;
import com.example.springdatabasicdemo.services.UserRoleService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
public class WebController {
    private final UserRoleService userRoleService;
    private final UserService userService;
    private final OfferService offerService;
    private final ModelService modelService;

    @Autowired
    public WebController(UserRoleService userRoleService,UserService userService, OfferService offerService, ModelService modelService) {
        this.userRoleService = userRoleService;
        this.userService = userService;
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/adminboard/users")
    public String updateUser(@RequestParam UUID id, @RequestParam(required = false) UUID roleId, RedirectAttributes redirectAttributes) throws Throwable {
        UserDto userDto = (UserDto) userService.findUser(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        userService.update(id, userDto);
        redirectAttributes.addFlashAttribute("successMessage", "User updated successfully");
        return "redirect:/adminboard/users";
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
    public String deleteModel(@PathVariable UUID id) {
        modelService.delete(id);
        return "redirect:/adminboard/models";
    }
}

