package com.example.springdatabasicdemo.models.enums;

public enum Transmission {
    MANUAL(0),
    AUTOMATIC(1);

    private int num;
    Transmission(int num){
        this.num=num;
    }
}
