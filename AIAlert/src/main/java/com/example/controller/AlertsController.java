package com.example.controller;


import com.example.model.Alert;
import com.example.model.Alerts;
import com.example.service.AlertsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Path(AlertsController.ALERTS_URL)
public class AlertsController {
	public static final String ALERTS_URL = "/";
    @Autowired
    private AlertsService alertService;
	
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("alerts")
    public ModelAndView viewAll() {
    	// forward to the "alerts" view, with a request attribute named
    	// "alerts" that has all of the existing contacts
        return new ModelAndView("alerts", "alerts", alertService.listAlerts());
    }

//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    
    @GET
    @Produces("application/json; charset=UTF-8")
    @Path("data")
    public String get(@QueryParam("country") String country, @QueryParam("date") String date) throws Exception {
    	Alerts alerts = alertService.getAlerts(country, date);
//    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
//    	BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( baos, "UTF-8" ) );
//    	writer.close();
    	return new String(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsBytes(alerts), "UTF-8");
    }

    @PUT
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @Path("alerts")
    public ModelAndView saveAlertForm(@Form Alert alert)
    	throws URISyntaxException 
    {
    	alertService.saveAlert(alert);
    	return viewAll();
    }

    @PUT
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @Path("alerts/delete")
    public ModelAndView delete(@Form Alert alert)
    {
    	alertService.removeAlert(alert.getId());
    	return viewAll();
    }

}
