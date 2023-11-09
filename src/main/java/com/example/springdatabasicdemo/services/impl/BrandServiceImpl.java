package com.example.springdatabasicdemo.services.impl;

import com.example.springdatabasicdemo.dtos.BrandDto;
import com.example.springdatabasicdemo.dtos.UserRoleDto;
import com.example.springdatabasicdemo.models.Brand;
import com.example.springdatabasicdemo.repositories.BrandRepository;
import com.example.springdatabasicdemo.services.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService<Integer> {

    private final BrandRepository brandRepository;

    private final ModelMapper modelMapper;

    public BrandServiceImpl(BrandRepository brandRepository, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BrandDto add(BrandDto brandDto) {
        Brand brand = modelMapper.map(brandDto, Brand.class);
        brand.setCreated(LocalDate.now());
        Brand savedBrand = brandRepository.save(brand);
        return modelMapper.map(savedBrand, BrandDto.class);
    }
    @Override
    public List<BrandDto> getAll() {
        return brandRepository.findAll().stream().map((c) -> modelMapper.map(c, BrandDto.class)).collect(Collectors.toList());
    }
    @Override
    public Optional<BrandDto> findBrand(UUID id) {
        return brandRepository.findById(id)
                .map(brand -> modelMapper.map(brand, BrandDto.class));
    }
    @Override
    public BrandDto delete(BrandDto brandDto){
        brandRepository.deleteById(brandDto.getId());
        return brandDto;
    }
    @Override
    public BrandDto update(UUID id, BrandDto brandDto) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found with id: " + id));
        modelMapper.map(brandDto, brand);
        brand.setModified(LocalDate.now());
        Brand updatedBrand = brandRepository.save(brand);
        return modelMapper.map(updatedBrand, BrandDto.class);
    }

}
