package com.example.springdatabasicdemo.models.enums;

public enum Role {
    User(0),
    Admin(1);

    private int num;
    Role(int num){
        this.num=num;
    }
}
