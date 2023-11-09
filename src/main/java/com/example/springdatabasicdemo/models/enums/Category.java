package com.example.springdatabasicdemo.models.enums;

public enum Category {
    Car(0),
    Buss(1),
    Truck(2),
    Motorcycle(3);

    private int num;

    Category(int num){
        this.num=num;
    }
}
