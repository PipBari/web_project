package com.example.springdatabasicdemo.models;

import com.example.springdatabasicdemo.models.enums.Category;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="Models")
public class Model extends TimeBaseEntity{
    @Column(name = "name")
    private String name;
    @Column(name="category")
    private Category category;
    @Column(name = "imageUrl")
    private String imageUrl;
    @Column(name = "startYear")
    private int startYear;
    @Column(name = "endYear")
    private int endYear;
    @OneToMany(mappedBy = "model", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Offer> offers;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public Model(){
    }
    public List<Offer> getOffers() {
        return offers;
    }
    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public Category getCategory(){
        return category;
    }
    public void setCategory(Category category){
        this.category=category;
    }
    public String getimageUrl(){
        return imageUrl;
    }
    public void setimageUrl(String imageUrl){
        this.imageUrl=imageUrl;
    }
    public int getstartYear(){
        return startYear;
    }
    public void setstartYear(int startYear){
        this.startYear=startYear;
    }
    public int getendYear(){
        return endYear;
    }
    public void setendYear(int endYear){
        this.endYear=endYear;
    }
    public Brand getBrand(){
        return brand;
    }
    public void setBrand(Brand brand){
        this.brand=brand;
    }
}
