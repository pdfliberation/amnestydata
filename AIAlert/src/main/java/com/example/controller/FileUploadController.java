package com.example.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Alerts;
import com.example.service.AlertsService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/fileupload")
public class FileUploadController {

	@Autowired
    private AlertsService alertService;
	@Autowired
    private AlertsController alertController;

	@RequestMapping(method=RequestMethod.GET)
	public void fileUploadForm() {
	}

	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView processUpload(@RequestParam MultipartFile file, Model model) throws IOException {
//		model.addAttribute("message", "File '" + file.getOriginalFilename() + "' uploaded successfully");
		ObjectMapper mapper =  new ObjectMapper();
		
		BufferedReader reader = new BufferedReader( new InputStreamReader (file.getInputStream(), Charset.forName("UTF-8")) );
		Alerts alerts = mapper.readValue(file.getInputStream(), Alerts.class);
		reader.close();

		alertService.saveAlerts(alerts);
		alertController.setDirty();
		
        return alertController.viewAll();
	}
	
}
