package com.example.springdatabasicdemo.controllers;

import com.example.springdatabasicdemo.dtos.ModelDto;
import com.example.springdatabasicdemo.dtos.OfferDto;
import com.example.springdatabasicdemo.dtos.UserDto;
import com.example.springdatabasicdemo.repositories.ModelRepository;
import com.example.springdatabasicdemo.repositories.UserRepository;
import com.example.springdatabasicdemo.services.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;
    private final UserRepository userRepository;
    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OfferController(OfferService offerService, UserRepository userRepository, ModelRepository modelRepository, ModelMapper modelMapper) {
        this.offerService=offerService;
        this.userRepository = userRepository;
        this.modelRepository = modelRepository;
        this.modelMapper=modelMapper;
    }

    @ModelAttribute("allUsers")
    public List<UserDto> populateUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @ModelAttribute("allModels")
    public List<ModelDto> populateModels() {
        return modelRepository.findAll().stream()
                .map(model -> modelMapper.map(model, ModelDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping
    public String listOffers(Model model) {
        List<OfferDto> offers = offerService.getAll();
        model.addAttribute("offers", offers);
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
        model.addAttribute("allModels", populateModels()); // Используйте уже созданный метод
        model.addAttribute("allUsers", populateUsers()); // Используйте уже созданный метод
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
        offerService.update(offerDto); // Ensure you have an update method in your service
        return "redirect:/offers";
    }

    @PostMapping
    public String createOffer(@ModelAttribute OfferDto offerDto) {
        offerService.add(offerDto);
        return "redirect:/offers";
    }

    @GetMapping("/delete/{id}")
    public String deleteOffer(@PathVariable UUID id) {
        offerService.delete(new OfferDto(id, null, null, null, 0, 0, null, null, null));
        return "redirect:/offers";
    }
}
