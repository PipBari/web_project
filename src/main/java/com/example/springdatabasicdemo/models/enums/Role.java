package com.example.springdatabasicdemo.models.enums;

public enum Role {
    USER(0),
    ADMIN(1);

    private int num;
    Role(int num){
        this.num=num;
    }
}
