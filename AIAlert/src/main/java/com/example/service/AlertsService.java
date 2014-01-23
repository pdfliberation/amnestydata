package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Alert;
import com.example.model.Alerts;

import javax.persistence.*;
import javax.persistence.criteria.*;

@Service
public class AlertsService {

    @PersistenceContext
    EntityManager em;
        
    @Transactional
    public void addAlert(Alert alert) {
        em.persist(alert);
    }

    @Transactional
    public Alerts listAlerts() {
        CriteriaQuery<Alert> c = em.getCriteriaBuilder().createQuery(Alert.class);
        c.from(Alert.class);
        return new Alerts(em.createQuery(c).getResultList());
    }

    @Transactional
    public void removeAlert(Integer id) {
        Alert Alert = em.find(Alert.class, id);
        if (null != Alert) {
            em.remove(Alert);
        }
    }

    @Transactional
    public void saveAlert(Alert alert) {
        em.persist(alert);
    }
    
    @Transactional
    public void saveAlerts(Alerts alerts) {
		for ( Alert alert: alerts.getAlerts() ) {
			alert.setId(null);
	        em.persist(alert);
		}
    }

    public Alerts getAlerts(String country, String year) {
    	CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Alert> c = cb.createQuery(Alert.class);
    	Root<Alert> alert = c.from(Alert.class);
    	
    	c.select(alert);
    	
    	List<Predicate> criteria = new ArrayList<Predicate>(); 
    	if ( country != null && !country.isEmpty() ) {
    		ParameterExpression<String> p =
    				cb.parameter(String.class, "country");
    				criteria.add(cb.equal(alert.get("country"), p));
    	}
    	if ( year != null && !year.isEmpty() ) {
    		ParameterExpression<String> p =
    				cb.parameter(String.class, "date");
    				criteria.add(cb.equal(alert.get("date"), p));
    	}
    			
    	if (criteria.size() == 1) {
    		c.where(criteria.get(0));
		} else if (criteria.size() >= 1) {
    		c.where(cb.and(criteria.toArray(new Predicate[0])));
		}
    	
    	TypedQuery<Alert> q = em.createQuery(c);
    	if ( country != null && !country.isEmpty() ) { q.setParameter("country", country); }
    	if ( year != null && !year.isEmpty() ) { q.setParameter("date", year); }
    	
    	return new Alerts(q.getResultList());
    }
}
