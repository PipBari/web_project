package com.example.springdatabasicdemo.models.enums;

public enum Engine {
    GASOLINE(0),
    DIESEL(1),
    ELECTRIC(2),
    HYBRID(3);
    private int num;

    Engine(int num){
        this.num=num;
    }
}
