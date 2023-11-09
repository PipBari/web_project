package com.example.springdatabasicdemo.models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;

@MappedSuperclass
public abstract class TimeBaseEntity extends BaseEntity{
    @Column(name="created")
    private LocalDate created;
    @Column(name="modified")
    private LocalDate modified;

    public TimeBaseEntity(){}

    public LocalDate getCreated(){return created;}
    public void setCreated(LocalDate created){this.created=created;}
    public LocalDate getModified(){return modified;}
    public void setModified(LocalDate modified){this.modified=modified;}
}
