package com.example.springdatabasicdemo.dtos;

import java.util.List;

public class BrandData {
    private List<BrandDto> brands;

    public BrandData(){}

    public List<BrandDto> getBrands(){
        return brands;
    }
    public void setBrands(List<BrandDto> brands){
        this.brands = brands;
    }
}
