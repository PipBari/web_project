package com.example.springdatabasicdemo.services.impl;

import com.example.springdatabasicdemo.dtos.ModelDto;
import com.example.springdatabasicdemo.models.Brand;
import com.example.springdatabasicdemo.models.Model;
import com.example.springdatabasicdemo.repositories.BrandRepository;
import com.example.springdatabasicdemo.repositories.ModelRepository;
import com.example.springdatabasicdemo.services.ModelService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ModelServiceImpl implements ModelService<Integer> {

    private final ModelRepository modelRepository;
    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    public ModelServiceImpl(ModelRepository modelRepository, BrandRepository brandRepository, ModelMapper modelMapper){
        this.modelRepository=modelRepository;
        this.brandRepository=brandRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public ModelDto add(ModelDto modelDto) {
        Model model = modelMapper.map(modelDto, Model.class);
        model.setCreated(LocalDate.now());
        if (modelDto.getBrand().getId() != null) {
            Brand brand = brandRepository.findById(modelDto.getBrand().getId()).orElse(null);
            model.setBrand(brand);
        }
        return modelMapper.map(modelRepository.save(model), ModelDto.class);
    }
    @Override
    public List<ModelDto> getAll() {
        return modelRepository.findAll().stream().map((c) -> modelMapper.map(c, ModelDto.class)).collect(Collectors.toList());
    }
    @Override
    public Optional<ModelDto> findModel(UUID id){
        return Optional.ofNullable(modelMapper.map(modelRepository.findById(id), ModelDto.class));
    }

    @Override
    public ModelDto delete(UUID id) {
        modelRepository.deleteById(id);
        return null;
    }

    @Override
    public ModelDto update(UUID id, ModelDto modelDto) {
        Model model = modelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Model not found with id: " + id));
        model.setName(modelDto.getName());
        model.setCategory(modelDto.getCategory());
        model.setimageUrl(modelDto.getImageUrl());
        model.setstartYear(modelDto.getStartYear());
        model.setendYear(modelDto.getEndYear());
        if (modelDto.getBrand() != null && modelDto.getBrand().getId() != null) {
            Brand brand = brandRepository.findById(modelDto.getBrand().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Brand not found with id: " + modelDto.getBrand().getId()));
            model.setBrand(brand);
        }
        model.setModified(LocalDate.now());
        Model updatedModel = modelRepository.save(model);
        return modelMapper.map(updatedModel, ModelDto.class);
    }
}
