package com.example.springdatabasicdemo.controllers;

import com.example.springdatabasicdemo.dtos.ModelDto;
import com.example.springdatabasicdemo.dtos.OfferDto;
import com.example.springdatabasicdemo.dtos.UserDto;
import com.example.springdatabasicdemo.repositories.ModelRepository;
import com.example.springdatabasicdemo.repositories.UserRepository;
import com.example.springdatabasicdemo.services.ModelService;
import com.example.springdatabasicdemo.services.OfferService;
import com.example.springdatabasicdemo.services.UserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/offers")
public class OfferController {

    private OfferService offerService;
    private UserService userService;
    private ModelService modelService;
    private ModelMapper modelMapper;

    private static final Logger LOG = LogManager.getLogger(Controller.class);
    @Autowired
    public void SetOfferService(OfferService offerService, UserService userService, ModelService modelService, ModelMapper modelMapper) {
        this.offerService=offerService;
        this.userService = userService;
        this.modelService = modelService;
        this.modelMapper=modelMapper;
    }

    @ModelAttribute("allUsers")
    public List<UserDto> populateUsers() {
        return (List<UserDto>) userService.getAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @ModelAttribute("allModels")
    public List<ModelDto> populateModels() {
        return (List<ModelDto>) modelService.getAll().stream()
                .map(model -> modelMapper.map(model, ModelDto.class))
                .collect(Collectors.toList());
    }


    @GetMapping("/list")
    public String listOffers(Principal principal, Model model) {
        LOG.log(Level.INFO, "Show all offers " + (principal != null ? principal.getName() : "Anonymous"));
        model.addAttribute("offersInfos", offerService.getAll());
        List<OfferDto> offers = offerService.getAll();
        model.addAttribute("offers", offers);
        model.addAttribute("isLoggedIn", principal != null);
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        return "offers/list";
    }

    @GetMapping("/create")
    public String createOfferForm(Model model) {
        model.addAttribute("offer", new OfferDto());
        model.addAttribute("allUsers", populateUsers());
        model.addAttribute("allModels", populateModels());
        return "offers/create";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable UUID id, Model model) throws Throwable {
        OfferDto offer = (OfferDto) offerService.findOffer(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid offer Id:" + id));
        model.addAttribute("offer", offer);
        model.addAttribute("allModels", populateModels());
        model.addAttribute("allUsers", populateUsers());
        return "offers/edit";
    }

    @GetMapping("/{id}")
    public String viewOffer(@PathVariable UUID id, Model model) throws Throwable {
        OfferDto offerDto = (OfferDto) offerService.findOffer(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid offer Id:" + id));
        model.addAttribute("offer", offerDto);
        return "offers/view";
    }

    @PostMapping("/{id}/update")
    public String updateOffer(@PathVariable UUID id, @ModelAttribute("offer") OfferDto offerDto) {
        offerService.update(offerDto);
        return "redirect:/offers";
    }

    @PostMapping
    public String createOffer(@ModelAttribute("offer") @Valid OfferDto offerDto) {
        offerService.add(offerDto);
        return "redirect:/offers";
    }

    @GetMapping("/delete/{id}")
    public String deleteOffer(@PathVariable UUID id) {
        offerService.delete(id);
        return "redirect:/";
    }
}
