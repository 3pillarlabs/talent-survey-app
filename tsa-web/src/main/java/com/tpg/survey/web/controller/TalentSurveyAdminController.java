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
import com.tpg.survey.exception.SurveyAlreadyRunningException;
import com.tpg.survey.service.SurveyService;

/**
 * @author amit.bharti
 *
 */
@RequestMapping("/admin")
@Controller
public class TalentSurveyAdminController {

	@Inject
	private SurveyService surveyService;

	// Launching Survey
	@PostMapping("/launchSurvey")
	public String launchSurvey(@ModelAttribute Survey survey) {
		String viewName = "surveyInitiated";

		try {
			Survey persistedSurvey = surveyService.save(survey);

			if (persistedSurvey.getSurveyId() > 0) {
				// put logic to return success
			}

		} catch (SurveyAlreadyRunningException e) {
			e.printStackTrace();
		}

		return viewName;
	}

	// Starting Survey
	@GetMapping("/startSurvey")
	public String initiateSurvey(Model model) {
		model.addAttribute("survey", new Survey());
		return "startSurvey";
	}
	
	@GetMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}
}