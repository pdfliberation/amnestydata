package com.example.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Alerts {
	private Collection<Alert> alerts;
	
	public Alerts() {
		this.alerts = new ArrayList<Alert>();
	}

	public Alerts(Collection<Alert> alerts) {
		this.alerts = alerts;
	}
	
	public Collection<Alert> getAlerts() {return alerts;}
	public void setAlerts(Collection<Alert> alerts) {this.alerts = alerts;}
	
}
