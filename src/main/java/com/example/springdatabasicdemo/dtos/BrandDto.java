package com.example.springdatabasicdemo.dtos;

import com.example.springdatabasicdemo.models.Brand;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BrandDto {
    private UUID id;
    @NotNull(message = "Имя не может быть пустым")
    @Size(min = 2, max = 20, message = "Должно быть от 2 до 20 символов")
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
