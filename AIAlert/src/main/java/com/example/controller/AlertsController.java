package com.example.controller;


import com.example.model.Alert;
import com.example.model.Alerts;
import com.example.model.ViewModel;
import com.example.service.AlertsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
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
    private Collection<String> countries;
    private Collection<String> years;
    private String country;
    private String year;
    private static boolean dirty;
    
    public AlertsController() {
    	dirty = true;
    }
    
    public void setDirty() {
    	dirty = true;
    }
    
    @PostConstruct
    public void postConstruct() {
    	countries = new TreeSet<String>();
    	years = new TreeSet<String>();
    	Alerts alerts = alertService.listAlerts();
    	for ( Alert alert: alerts.getAlerts() ) {
    		if ( alert.getCountry() != null ) countries.add(alert.getCountry());
    		if ( alert.getDate() != null ) years.add(alert.getDate());
    	}
    	year = null;
    	country = null;
    	dirty = false;
    }
	
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("alerts")
    public ModelAndView viewAll() {
    	// forward to the "alerts" view, with a request attribute named
    	// "alerts" that has all of the existing contacts
    	if ( dirty ) postConstruct();
        return new ModelAndView("alerts", "model", new ViewModel(
        		alertService.getAlerts(country, year), 
        		countries, 
        		years, 
        		country, 
        		year
        	)
        );
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
    @Produces(MediaType.TEXT_HTML)
    @Path("clear")
    public ModelAndView clear() throws URISyntaxException 
    {
    	alertService.clearDatabase();
    	setDirty();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");
        return mav;
    }

    @PUT
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @Path("alerts/update")
    public ModelAndView delete(@Form Alert alert)
    {
    	alertService.updateNotes(alert);
    	return viewAll();
    }

/*    
    @PUT
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    @Path("alerts")
    @RequestMapping(value="UPDATE", method=RequestMethod.POST)
    public ModelAndView update(@Form Alert alert)
    {
    	alertService.updateNotes(alert);
    	return viewAll();
    }
*/
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("alerts/filter")
    public ModelAndView filter(@QueryParam("country") String country, @QueryParam("year") String year)
    {
    	this.country = country;
    	this.year = null;
    	return viewAll();
    }
}
