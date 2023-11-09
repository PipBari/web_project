package com.example.springdatabasicdemo.controllers;

import com.example.springdatabasicdemo.dtos.BrandDto;
import com.example.springdatabasicdemo.dtos.ModelDto;
import com.example.springdatabasicdemo.models.enums.Category;
import com.example.springdatabasicdemo.services.BrandService;
import com.example.springdatabasicdemo.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/models")
public class ModelController {

    private final ModelService modelService;
    private BrandService brandService;

    public ModelController(ModelService modelService, BrandService brandService) {
        this.modelService = modelService;
        this.brandService = brandService;
    }

    @GetMapping
    public String listModels(Model model) {
        model.addAttribute("models", modelService.getAll());
        return "models/list";
    }

    @GetMapping("/add")
    public String addModelForm(Model model) {
        model.addAttribute("model", new ModelDto());
        model.addAttribute("categories", Category.values());
        model.addAttribute("brands", brandService.getAll()); // Добавляем список брендов
        return "models/add";
    }

    @PostMapping
    public String addModel(@ModelAttribute ModelDto modelDto) {
        modelService.add(modelDto);
        return "redirect:/models";
    }

    @GetMapping("/{id}")
    public String viewModel(@PathVariable UUID id, Model model) {
        modelService.findModel(id).ifPresent(modelDto -> model.addAttribute("model", modelDto));
        return "models/view";
    }

    @GetMapping("/{id}/edit")
    public String editModelForm(@PathVariable UUID id, Model model) throws Throwable {
        ModelDto modelDto = (ModelDto) modelService.findModel(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Model Id:" + id));
        model.addAttribute("model", modelDto);
        model.addAttribute("categories", Category.values());
        model.addAttribute("brands", brandService.getAll()); // исправлено здесь
        return "models/edit";
    }


    @PostMapping("/{id}/update")
    public String updateModel(@PathVariable UUID id, @ModelAttribute ModelDto modelDto) {
        modelService.update(id, modelDto); // предположим, что такой метод есть в ModelService
        return "redirect:/models";
    }

    @PostMapping("/{id}/delete")
    public String deleteModel(@PathVariable UUID id) {
        ModelDto modelDto = new ModelDto();
        modelDto.setId(id);
        modelService.delete(modelDto);
        return "redirect:/models";
    }
}
