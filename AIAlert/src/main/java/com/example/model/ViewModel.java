package com.example.model;

import java.util.Collection;

public class ViewModel {

	private Alerts alerts;
	private Collection<String> countries;
	private Collection<String> years;
	private String country;
	private String year;
	
	public ViewModel(Alerts listAlerts, Collection<String> countries, Collection<String> years, String country, String year) {
		this.alerts = listAlerts;
		this.countries = countries;
		this.years = years;
		this.country = country;
		this.year = year;
	}

	public Alerts getAlerts() {
		return alerts;
	}
	public void setAlerts(Alerts alerts) {
		this.alerts = alerts;
	}
	public Collection<String> getCountries() {
		return countries;
	}
	public void setCountries(Collection<String> countries) {
		this.countries = countries;
	}
	public Collection<String> getYears() {
		return years;
	}
	public void setYears(Collection<String> years) {
		this.years = years;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
