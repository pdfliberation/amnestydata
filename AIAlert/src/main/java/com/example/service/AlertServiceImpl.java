package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.AIAlert;

import javax.persistence.*;
import javax.persistence.criteria.*;

import java.util.List;

@Service
public class AlertServiceImpl implements AlertService {

    @PersistenceContext
    EntityManager em;
        
    @Transactional
    public void addAlert(AIAlert Alert) {
        em.persist(Alert);
    }

    @Transactional
    public List<AIAlert> listAlerts() {
        CriteriaQuery<AIAlert> c = em.getCriteriaBuilder().createQuery(AIAlert.class);
        c.from(AIAlert.class);
        return em.createQuery(c).getResultList();
    }

    @Transactional
    public void removeAlert(Integer id) {
        AIAlert Alert = em.find(AIAlert.class, id);
        if (null != Alert) {
            em.remove(Alert);
        }
    }


}
