package com.tpg.survey.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tpg.survey.web.data.RetrieveQuestionsData;
import com.tpg.survey.web.pojos.Element;

@RestController
public class SurveyQuestionsController {

	@Value("${talent.survey.questions.file.name}") // Desktop location, needs to be changed to remote location
	private String file;
	
	@Autowired
	private RetrieveQuestionsData retrieveQuestionData;
	
	@RequestMapping (value = "/getQuestions", method = RequestMethod.GET)
	public Map<String, List<Element>> retrieveSurveyQuestions (){
		Map<String, List<Element>> allQuestions = retrieveQuestionData.getQuestions(file);
		return allQuestions;
	}
	
	@RequestMapping (value = "/saveSurvey", method = RequestMethod.POST)
	public Map<String, List<Element>> saveSurvey (){
		Map<String, List<Element>> allQuestions = retrieveQuestionData.getQuestions(file);
		return allQuestions;
	}
}
