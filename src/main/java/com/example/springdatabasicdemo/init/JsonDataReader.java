package com.example.springdatabasicdemo.init;

import com.example.springdatabasicdemo.dtos.BrandDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class JsonDataReader {
    private final ResourceLoader resourceLoader;

    public JsonDataReader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<BrandDto> readBrandData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = resourceLoader.getResource("classpath:init/brands.json");
        return objectMapper.readValue(resource.getInputStream(), new TypeReference<List<BrandDto>>() {});
    }
}
