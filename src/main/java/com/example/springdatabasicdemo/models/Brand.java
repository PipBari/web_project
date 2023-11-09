package com.example.springdatabasicdemo.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="Brands")
public class Brand extends  TimeBaseEntity{
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "brand", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Model> models;

    public Brand(){}

    public List<Model> getModels() {
        return models;
    }
    public void setModels(List<Model> models) {
        this.models = models;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
}
