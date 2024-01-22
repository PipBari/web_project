package com.example.springdatabasicdemo.controllers;

import com.example.springdatabasicdemo.dtos.BrandDto;
import com.example.springdatabasicdemo.services.BrandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/brands")
public class BrandController {

    private BrandService brandService;

    @Autowired
    public void SetBrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/list")
    public String listBrands(Model model) {
        model.addAttribute("brands", brandService.getAll());
        return "brands/list";
    }

    @GetMapping("/add")
    public String addBrandForm(Model model) {
        model.addAttribute("brand", new BrandDto());
        return "brands/add";
    }

    @PostMapping("/add")
    public String addBrand(@ModelAttribute("brand") @Valid BrandDto brandDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("brand", brandDto);
            return "brands/add";
        }
        brandService.add(brandDto);
        return "redirect:/brands/list";
    }

    @GetMapping("/{id}")
    public String viewBrand(@PathVariable UUID id, Model model) {
        brandService.findBrand(id).ifPresent(brand -> model.addAttribute("brand", brand));
        return "brands/view";
    }

    @PostMapping("/{id}/delete")
    public String deleteBrand(@PathVariable UUID id) {
        BrandDto brandDto = new BrandDto();
        brandDto.setId(id);
        brandService.delete(id);
        return "redirect:/brands/list";
    }
}

