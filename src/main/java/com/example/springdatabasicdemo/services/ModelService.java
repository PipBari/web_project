package com.example.springdatabasicdemo.services;

import com.example.springdatabasicdemo.dtos.ModelDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModelService<ID>{
    ModelDto add(ModelDto modelDto);

    List<ModelDto> getAll();

    Optional<ModelDto> findModel(UUID id);

    ModelDto delete(ModelDto modelDto);

    ModelDto update(UUID id, ModelDto modelDto);
}
