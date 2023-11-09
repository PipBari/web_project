package com.example.springdatabasicdemo.dtos;

import com.example.springdatabasicdemo.models.Brand;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BrandDto {
    private UUID id;
    private String name;
    public BrandDto(){}

    public BrandDto(UUID id, String name){
        this.id = id;
        this.name = name;
    }

    public UUID getId(){
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
