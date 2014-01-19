package com.example.model;

import javax.persistence.*;

@Entity
public class AIAlert {

    @Id
    @GeneratedValue
    private Integer id;

    private String country;

    private String date;
    
    @Column(length=1000)
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }

}
