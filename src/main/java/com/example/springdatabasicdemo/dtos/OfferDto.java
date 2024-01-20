package com.example.springdatabasicdemo.dtos;

import com.example.springdatabasicdemo.models.enums.Engine;
import com.example.springdatabasicdemo.models.enums.Transmission;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public class OfferDto {
    private UUID id;
    private String description;
    private Engine engine;
    private String imageUrl;
    @NotNull(message = "Цена не может быть пустой")
    @Min(value = 1)
    private int mileage;
    @NotNull(message = "Цена не может быть пустой")
    @Min(value = 1)
    private int price;
    private Transmission transmission;
    private LocalDate year;
    private LocalDate modified;
    private ModelDto model;
    private UserDto user;

    public OfferDto(){}

    public OfferDto(UUID id, String description, Engine engine, String imageUrl, int mileage, int price, Transmission transmission, ModelDto model, UserDto user){
       this.id = id;
       this.description = description;
       this.engine = engine;
       this.imageUrl = imageUrl;
       this.mileage = mileage;
       this.price = price;
       this.transmission = transmission;
       this.model = model;
       this.user = user;
    }

    public UUID getId(){return id;}
    public void setId(UUID id){this.id = id;}
    public String getDescription(){return description;}
    public void setDescription(String description){this.description=description;}
    public Engine getEngine(){return engine;}
    public void setEngine(Engine engine){this.engine=engine;}
    public String getImageUrl(){return imageUrl;}
    public void setImageUrl(String imageUrl){this.imageUrl=imageUrl;}
    public int getMileage(){return mileage;}
    public void setMileage(int mileage){this.mileage=mileage;}
    public int getPrice(){return price;}
    public void setPrice(int price){this.price=price;}
    public Transmission getTransmission(){return transmission;}
    public void setTransmission(Transmission transmission){this.transmission=transmission;}
    public ModelDto getModel(){return model;}
    public void setModel(ModelDto model){this.model=model;}
    public UserDto getUser(){return user;}
    public void setUser(UserDto user){this.user=user;}
    @Override
    public String toString() {
        return "OfferDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", engine=" + engine +
                ", imageUrl='" + imageUrl + '\'' +
                ", mileage=" + mileage +
                ", price=" + price +
                ", transmission=" + transmission +
                ", year=" + year +
                ", modified=" + modified +
                ", model=" + model +
                ", user=" + user +
                '}';
    }
}
