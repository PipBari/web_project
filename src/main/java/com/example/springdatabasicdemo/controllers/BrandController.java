package com.example.springdatabasicdemo.controllers;

import com.example.springdatabasicdemo.dtos.BrandDto;
import com.example.springdatabasicdemo.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public String listBrands(Model model) {
        model.addAttribute("brands", brandService.getAll());
        return "brands/list";
    }

    @GetMapping("/add")
    public String addBrandForm(Model model) {
        model.addAttribute("brand", new BrandDto());
        return "brands/add";
    }

    @PostMapping
    public String addBrand(@ModelAttribute BrandDto brandDto) {
        brandService.add(brandDto);
        return "redirect:/brands";
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
        brandService.delete(brandDto);
        return "redirect:/brands";
    }

    @GetMapping("/{id}/edit")
    public String editBrandForm(@PathVariable UUID id, Model model) throws Throwable {
        BrandDto brandDto = (BrandDto) brandService.findBrand(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid brand Id:" + id));
        model.addAttribute("brand", brandDto);
        return "brands/edit";
    }
    @PostMapping("/{id}/update")
    public String updateBrand(@PathVariable UUID id, @ModelAttribute BrandDto brandDto) {
        brandService.update(id, brandDto);
        return "redirect:/brands";
    }

}
