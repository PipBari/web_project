package com.example.springdatabasicdemo.dtos;

import java.time.LocalDate;
import java.util.UUID;

public class UserDto {
    private UUID id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private Boolean isActive;
    private String imageUrl;
    private LocalDate created;
    private LocalDate modified;
    private UserRoleDto userRoleDto;

    public UserDto(){}

    public UserDto(UUID id, String username, String password, String firstname, String lastname, Boolean isActive, String imageUrl, UserRoleDto userRoleDto){
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
        this.userRoleDto = userRoleDto;
    }

    public UUID getId(){return id;}
    public void setId(UUID id){this.id = id;}
    public String getUsername(){return username;}
    public void setUsername(String username){this.username=username;}
    public String getPassword(){return password;}
    public void setPassword(String password){this.password=password;}
    public String getFirstname(){return firstname;}
    public void setFirstname(String firstname){this.firstname=firstname;}
    public String getLastname(){return lastname;}
    public void setLastname(String lastname){this.lastname=lastname;}
    public Boolean getIsActive(){return isActive;}
    public void setIsActive(Boolean isActive){this.isActive=isActive;}
    public String getImageUrl(){return imageUrl;}
    public void setImageUrl(String imageUrl){this.imageUrl=imageUrl;}
    public UserRoleDto getUserRoleDto(){return userRoleDto;}
    public void setUserRoleDto(UserRoleDto userRoleDto){this.userRoleDto=userRoleDto;}
}
