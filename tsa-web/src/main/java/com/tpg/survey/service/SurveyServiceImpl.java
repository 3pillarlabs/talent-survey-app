/**
 * 
 */
package com.tpg.survey.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tpg.survey.domain.Survey;
import com.tpg.survey.exception.SurveyAlreadyRunningException;
import com.tpg.survey.repository.SurveyRepository;

/**
 * @author amit.bharti
 *
 */
@Service
public class SurveyServiceImpl implements SurveyService {

	@Inject
	private SurveyRepository surveyRepository;
	
	@Override
	public Survey save(Survey survey) throws SurveyAlreadyRunningException{
		Sort sort = new Sort(Sort.Direction.DESC, "surveyStartDate");
		List<Survey> surveys = surveyRepository.findByIsActiveAndIsLaunched(Boolean.TRUE, Boolean.TRUE, sort);
		
		if (surveys != null && surveys.size() > 0) {
			throw new SurveyAlreadyRunningException("Survey is already in progress");
		}
		
		return surveyRepository.save(survey);
	}
}
