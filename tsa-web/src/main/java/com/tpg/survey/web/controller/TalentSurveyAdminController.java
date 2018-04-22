/**
 * 
 */
package com.tpg.survey.web.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tpg.survey.domain.Survey;
import com.tpg.survey.service.LaunchService;

/**
 * @author amit.bharti
 *
 */
@RequestMapping("/admin")
@Controller
public class TalentSurveyAdminController {
	
	@Inject
	private LaunchService launchService;
	
	// Launching Survey
	@PostMapping("/launchSurvey")
	public String launchSurvey(@ModelAttribute Survey survey) {
		launchService.initiateSurvey(survey);
		return "surveyInitiated";
	}
	
	// Starting Survey
	@GetMapping("/startSurvey")
	public String initiateSurvey(Model model) {
		model.addAttribute("survey", new Survey());
		return "startSurvey";
	}
}