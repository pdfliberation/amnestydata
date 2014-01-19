package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.model.AIAlert;
import com.example.service.AlertService;

import java.util.Map;

@Controller
public class AlertController {

    @Autowired
    private AlertService AlertService;

    @RequestMapping("/")
    public String listAlert(Map<String, Object> map) {

        map.put("alert", new AIAlert());
        map.put("alertList", AlertService.listAlerts());

        return "alert";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addAlert(@ModelAttribute("Alert") AIAlert Alert, BindingResult result) {

        AlertService.addAlert(Alert);

        return "redirect:/alert/";
    }

    @RequestMapping("/delete/{AlertId}")
    public String deleteAlert(@PathVariable("AlertId") Integer AlertId) {

        AlertService.removeAlert(AlertId);

        return "redirect:/alert/";
    }
}
