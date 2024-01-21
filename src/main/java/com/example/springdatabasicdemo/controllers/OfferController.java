package com.example.springdatabasicdemo.controllers;

import com.example.springdatabasicdemo.dtos.ModelDto;
import com.example.springdatabasicdemo.dtos.OfferDto;
import com.example.springdatabasicdemo.dtos.UserDto;
import com.example.springdatabasicdemo.services.ModelService;
import com.example.springdatabasicdemo.services.OfferService;
import com.example.springdatabasicdemo.services.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/offers")
public class OfferController {

    private OfferService offerService;
    private UserService userService;
    private ModelService modelService;
    private ModelMapper modelMapper;

    @Autowired
    public void SetOfferService(OfferService offerService, UserService userService, ModelService modelService, ModelMapper modelMapper) {
        this.offerService=offerService;
        this.userService = userService;
        this.modelService = modelService;
        this.modelMapper=modelMapper;
    }

    @ModelAttribute("allModels")
    public List<ModelDto> populateModels() {
        return (List<ModelDto>) modelService.getAll().stream()
                .map(model -> modelMapper.map(model, ModelDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/list")
    public String listOffers(Principal principal, Model model) {
        List<OfferDto> offers = offerService.getAll();
        model.addAttribute("offers", offers);

        if (principal != null) {
            String username = principal.getName();
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("username", username);

            List<UUID> viewedOfferIds = offerService.getViewedOfferIdsByUser(username);
            model.addAttribute("viewedOffers", viewedOfferIds);
        } else {
            model.addAttribute("isLoggedIn", false);
            model.addAttribute("viewedOffers", Collections.emptyList());
        }
        return "offers/list";
    }

    @GetMapping("/viewed")
    public String listViewedOffers(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        List<String> viewedOfferIdStrings = offerService.getViewedOfferIdsByUser(username);
        List<UUID> viewedOfferIds = viewedOfferIdStrings.stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());
        List<Object> viewedOffers = viewedOfferIds.stream()
                .map(offerId -> offerService.findOffer(offerId).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()).reversed();
        model.addAttribute("viewedOffers", viewedOffers);
        return "offers/viewed";
    }

    @GetMapping("/create")
    public String createOffer(Model model) {
        model.addAttribute("offer", new OfferDto());
        model.addAttribute("allModels", populateModels());
        return "offers/create";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable UUID id, Model model) throws Throwable {
        OfferDto offer = (OfferDto) offerService.findOffer(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid offer Id:" + id));
        model.addAttribute("offer", offer);
        model.addAttribute("allModels", populateModels());
        return "offers/edit";
    }

    @GetMapping("/{id}")
    public String viewOffer(@PathVariable UUID id, Principal principal, Model model, RedirectAttributes redirectAttributes) {
        Optional<OfferDto> offerDtoOptional = offerService.findOffer(id);
        if (!offerDtoOptional.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid offer Id: " + id);
            return "redirect:/offers/list";
        }
        OfferDto offerDto = offerDtoOptional.get();
        model.addAttribute("offer", offerDto);
        if (principal != null) {
            offerService.markOfferAsViewed(id, principal.getName());
        }
        return "offers/view";
    }

    @PostMapping("/{id}/update")
    public String updateOffer(@PathVariable UUID id, @ModelAttribute("offer") OfferDto offerDto) {
        offerService.update(offerDto);
        return "redirect:/offers/list";
    }

    @PostMapping("/create")
    public String createOffer(@ModelAttribute OfferDto offerDto, Principal principal) throws Throwable {
        String username = principal.getName();
        UserDto userDto = (UserDto) userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        offerDto.setUser(userDto);
        offerService.add(offerDto);
        return "redirect:/offers/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteOffer(@PathVariable UUID id) {
        offerService.delete(id);
        return "redirect:/offers/myoffers";
    }

    @GetMapping("/myoffers")
    public String listUserOffers(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        List<OfferDto> userOffers = offerService.getOffersByUser(username);
        model.addAttribute("offers", userOffers);
        return "offers/myoffers";
    }
}
