package com.example.springdatabasicdemo.models;

import com.example.springdatabasicdemo.models.enums.Role;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Roles")
public class UserRole extends BaseEntity{
    @Column(name = "name")
    private Role role;
    @OneToMany(mappedBy = "userRole", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<User> users;

    public UserRole(){}

    public void setUsers(List<User> users){
        this.users=users;
    }
    public Role getRole(){
        return role;
    }
    public void setRole(Role role){
        this.role=role;
    }
    public List<User> getUsers(){
        return users;
    }
}
