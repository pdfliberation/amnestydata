package com.example.service;


import java.util.List;

import com.example.model.AIAlert;

public interface AlertService {
    
    public void addAlert(AIAlert person);
    public List<AIAlert> listAlerts();
    public void removeAlert(Integer id);
}
