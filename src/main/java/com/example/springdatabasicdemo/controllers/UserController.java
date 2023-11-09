package com.example.springdatabasicdemo.controllers;

import com.example.springdatabasicdemo.dtos.UserDto;
import com.example.springdatabasicdemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users/list";
    }

    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "users/add";
    }

    @PostMapping
    public String createUser(@ModelAttribute UserDto userDto) {
        userService.add(userDto);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable UUID id, Model model) throws Throwable {
        UserDto userDto = (UserDto) userService.findUser(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
        model.addAttribute("user", userDto);
        return "users/edit";
    }

    @GetMapping("/view/{id}")
    public String viewUser(@PathVariable UUID id, Model model) throws Throwable {
        UserDto userDto = (UserDto) userService.findUser(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
        model.addAttribute("user", userDto);
        return "users/view"; // предполагается, что у вас есть шаблон view.html в директории /resources/templates/users/
    }


    @PostMapping("/{id}")
    public String updateUser(@PathVariable UUID id, @ModelAttribute UserDto userDto) {
        userService.update(id, userDto);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable UUID id) {
        userService.deactivateAccount(id);
        return "redirect:/users";
    }
}
