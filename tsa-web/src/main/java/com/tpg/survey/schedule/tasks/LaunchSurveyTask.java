/**
 * 
 */
package com.tpg.survey.schedule.tasks;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tpg.survey.domain.Survey;
import com.tpg.survey.repository.SurveyRepository;
import com.tpg.survey.service.LaunchService;

/**
 * @author amit.bharti
 *
 */
@Component
public class LaunchSurveyTask {
	
	@Inject
	private LaunchService launchService;
	
	@Inject 
	SurveyRepository surveyRepository;
	
	@Scheduled(cron = "*/10 * * * * *")
	public void launchSurvey() {
		// Fetching all active, non launched surveys
		Sort sort = new Sort(Sort.Direction.DESC, "surveyStartDate");
		List<Survey> surveyList = surveyRepository.findByIsActiveAndIsLaunched(Boolean.TRUE, Boolean.FALSE, sort);
		
		if (surveyList != null && surveyList.size() > 0 && surveyList.size() < 2) {
			launchService.initiateSurvey(surveyList.get(0));
		}
	}
}