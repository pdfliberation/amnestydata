package com.example.model;

import javax.persistence.*;
import javax.ws.rs.FormParam;

//something

@Entity
public class Alert {

    @Id
    @GeneratedValue
    private Integer id;

    private String country;

    private String date;
    
	@Column(columnDefinition="TEXT")
    private String description;
    
    private String notes;
    
    public Alert() {}
    public Alert(String country, String date, String description) {
    	this.country = country;
    	this.date = date;
    	this.description = description;
    }

    @FormParam("id")
    public void setId(Integer id) {this.id = id;}
    public Integer getId() {return id;}

    @FormParam("country")
    public void setCountry(String country) {this.country = country;}
    public String getCountry() {return country;}

    @FormParam("date")
    public void setDate(String date) {this.date = date;}
    public String getDate() {return date;}

    @FormParam("description")
    public void setDescription(String Description) {this.description = Description;}
    public String getDescription() {return description;}
	public String getNotes() {
		return notes;
	}
    @FormParam("notes")
	public void setNotes(String method) {
		this.notes = method;
	}

}
