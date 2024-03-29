package com.example.springdatabasicdemo.controllers;

import com.example.springdatabasicdemo.dtos.OfferDto;
import com.example.springdatabasicdemo.dtos.UserDto;
import com.example.springdatabasicdemo.services.OfferService;
import com.example.springdatabasicdemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private OfferService offerService;

    @Autowired
    public void SetUserController(UserService userService, OfferService offerService) {
        this.userService = userService;
        this.offerService = offerService;
    }

    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users/list";
    }

    @GetMapping("/register")
    public String createUserForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "users/register";
    }

    @PostMapping
    public String createUser(@ModelAttribute UserDto user, BindingResult result) {
        if (result.hasErrors()) {
            return "users/register";
        }
       userService.add(user);
       return "redirect:/users/login";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable UUID id, Model model) throws Throwable {
        UserDto userDto = (UserDto) userService.findUser(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
        model.addAttribute("user", userDto);
        return "users/edit";
    }

    @PostMapping
    public String updateUser(@PathVariable UUID id, @ModelAttribute UserDto userDto) {
        userService.update(id, userDto);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable UUID id) {
        userService.deactivateAccount(id);
        return "redirect:/users";
    }

    @GetMapping("/profile/{username}")
    public String userProfile(@PathVariable String username, Model model) throws Throwable {
        UserDto userDto = (UserDto) userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        model.addAttribute("user", userDto);
        return "users/view";
    }

    @GetMapping("/profile/{username}/user_offers")
    public String userOffers(@PathVariable String username, Model model) throws Throwable {
        UserDto userDto = (UserDto) userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        List<OfferDto> userOffers = offerService.getOffersByUser(username);
        model.addAttribute("user", userDto);
        model.addAttribute("offers", userOffers);
        return "users/user_offers";
    }
}
