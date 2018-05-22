package com.tpg.survey.service;

import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpg.survey.repository.SurveyResponseRepository;
import com.tpg.survey.web.pojos.SurveyResponseDTO;

@Service
public class SurveyResponseServiceImpl implements SurveyResponseService {

	
	@Autowired
	private SurveyResponseRepository surveyResponseRepository;
	
	@Override
	public void save(SurveyResponseDTO response) {

		for(Entry<String, String> quesVsAns : response.getQuestionIdVsAnswers().entrySet()){
			
		}
		
	}

}
