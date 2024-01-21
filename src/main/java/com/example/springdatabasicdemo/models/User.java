package com.example.springdatabasicdemo.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Users")
public class User extends TimeBaseEntity{
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "isActive")
    private Boolean isActive;
    @Column(name = "imageUrl")
    private String imageUrl;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Offer> offers;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private UserRole userRole;

    public User(){}

    public UserRole getUserRole() {
        return userRole;
    }
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
    public List<Offer> getOffers(){
        return offers;
    }
    public void setOffers(List<Offer> offers){
        this.offers=offers;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public String getFirstname(){
        return firstname;
    }
    public void setFirstname(String firstname){
        this.firstname=firstname;
    }
    public String getLastname(){
        return lastname;
    }
    public void setLastname(String lastname){
        this.lastname=lastname;
    }
    public Boolean getisActive(){
        return isActive;
    }
    public void setIsActive(Boolean isActive){
        this.isActive=isActive;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl=imageUrl;
    }
}
