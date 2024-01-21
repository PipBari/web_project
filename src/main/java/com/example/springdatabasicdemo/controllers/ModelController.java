package com.example.springdatabasicdemo.controllers;

import com.example.springdatabasicdemo.dtos.ModelDto;
import com.example.springdatabasicdemo.models.enums.Category;
import com.example.springdatabasicdemo.services.BrandService;
import com.example.springdatabasicdemo.services.ModelService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/models")
public class ModelController {

    private ModelService modelService;
    private BrandService brandService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    @Autowired
    public void SetModelController(ModelService modelService, BrandService brandService) {
        this.modelService = modelService;
        this.brandService = brandService;
    }

    @GetMapping("/list")
    public String listModels(Principal principal, Model model) {
        LOG.log(Level.INFO, "Show all models " + principal.getName());
        model.addAttribute("offersInfos", modelService.getAll());
        model.addAttribute("models", modelService.getAll());
        return "models/list";
    }

    @GetMapping("/add")
    public String addModel(Model model) {
        model.addAttribute("model", new ModelDto());
        model.addAttribute("categories", Category.values());
        model.addAttribute("brands", brandService.getAll());
        return "models/add";
    }

    @PostMapping("add")
    public String addModel(@ModelAttribute("model") @Valid ModelDto modelDto, BindingResult result) {
        if (result.hasErrors()) {
            return "models/add";
        }
        modelService.add(modelDto);
        return "redirect:/models/list";
    }

    @GetMapping("/view/{id}")
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
        model.addAttribute("brands", brandService.getAll());
        return "models/edit";
    }

    @PostMapping("/{id}/update")
    public String updateModel(@PathVariable UUID id, @ModelAttribute ModelDto modelDto) {
        modelService.update(id, modelDto);
        return "redirect:/models";
    }

    @PostMapping("/{id}/delete")
    public String deleteModel(@PathVariable UUID id) {
        ModelDto modelDto = new ModelDto();
        modelDto.setId(id);
        modelService.delete(id);
        return "redirect:/models";
    }
}
