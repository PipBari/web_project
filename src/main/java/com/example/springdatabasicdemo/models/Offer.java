package com.example.springdatabasicdemo.models;

import com.example.springdatabasicdemo.models.enums.Engine;
import com.example.springdatabasicdemo.models.enums.Transmission;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="Offers")
public class Offer extends TimeBaseEntity{
    @Column(name = "description")
    private String description;
    @Column(name = "engine")
    private Engine engine;
    @Column(name = "imageUrl")
    private String imageUrl;
    @Column(name = "mileage")
    private int mileage;
    @Column(name = "price")
    private int price;
    @Column(name = "transmission")
    private Transmission transmission;
    @Column(name = "year")
    private LocalDate year;
    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;
    @ManyToOne
    @JoinColumn(name="seller_id")
    private User user;

    public Offer(){}

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Model getModel() {
        return model;
    }
    public void setModel(Model model) {
        this.model = model;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public Engine getEngine(){
        return engine;
    }
    public void setEngine(Engine engine){
        this.engine=engine;
    }
    public String getimageUrl(){
        return imageUrl;
    }
    public void setimageUrl(String imageUrl){
        this.imageUrl=imageUrl;
    }
    public int getMileage(){
        return mileage;
    }
    public void setMileage(int mileage){
        this.mileage=mileage;
    }
    public int getPrice(){
        return price;
    }
    public void setPrice(int price){
        this.price=price;
    }
    public Transmission getTransmission(){
        return transmission;
    }
    public void setTransmission(Transmission transmission){
        this.transmission=transmission;
    }
    public LocalDate getYear(){
        return year;
    }
    public void setYear(LocalDate year){
        this.year=year;
    }
}
