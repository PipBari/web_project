package com.example.springdatabasicdemo.services;

import com.example.springdatabasicdemo.dtos.BrandDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BrandService<ID>{
    BrandDto add(BrandDto brandDto);

    List<BrandDto> getAll();

    Optional<BrandDto> findBrand(UUID id);

    BrandDto delete(UUID id);

    BrandDto update(UUID id, BrandDto brandDto);
}
