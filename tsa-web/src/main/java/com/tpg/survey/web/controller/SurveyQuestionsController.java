package com.tpg.survey.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tpg.survey.web.data.SurveyQuestionData;
import com.tpg.survey.web.data.SurveyQuestionDataImpl;
import com.tpg.survey.web.pojos.Element;
import com.tpg.survey.web.pojos.SurveyResponse;

@RestController
public class SurveyQuestionsController {

	@Value("${talent.survey.questions.file.name}") // Desktop location, needs to be changed to remote location
	private String file;
	
	@Autowired
	private SurveyQuestionData surveyQuestionData;
	
	@RequestMapping (value = "/getQuestions", method = RequestMethod.GET)
	public Map<String, List<Element>> retrieveSurveyQuestions (){
		Map<String, List<Element>> allQuestions = surveyQuestionData.getQuestions(file);
		System.out.println(SurveyQuestionDataImpl.getIdQuestionMap());
		return allQuestions;
	}
	
	@RequestMapping (value = "/persistSurveyResponse", method = RequestMethod.POST)
	public ResponseEntity<String> saveSurvey (@RequestBody SurveyResponse response){
		try {
			// add validation of response
			surveyQuestionData.save(response);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
		}
	}
}
