package com.tpg.survey.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tpg.survey.domain.QuestionnaireSection;
import com.tpg.survey.service.ElementService;
import com.tpg.survey.service.SurveyResponseService;
import com.tpg.survey.web.data.SurveyQuestionData;
import com.tpg.survey.web.data.SurveyQuestionDataImpl;
import com.tpg.survey.web.pojos.Element;

@RestController
public class TalentSurveyController {
	
	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		return "welcome";
	}
	
	@Value("${talent.survey.questions.file.name}")
	private String questionarrieFile;
	
	@Value("${talent.survey.response.file.name}")
	private String responseFile;
	
	@Autowired
	private SurveyQuestionData surveyQuestionData;
	
	@Autowired
	private ElementService elementService;
	
	@Autowired
	private SurveyResponseService responseService;
	
	@CrossOrigin
	@RequestMapping (value = "/getQuestions", method = RequestMethod.GET)
	public Map<String, List<Element>> retrieveSurveyQuestionsFromExcel (){
		Map<String, List<Element>> allQuestions = surveyQuestionData.getQuestions(questionarrieFile);
		System.out.println(SurveyQuestionDataImpl.getIdQuestionMap());
		return allQuestions;
	}
	
	@CrossOrigin
	@RequestMapping (value = "/getQuestions2", method = RequestMethod.GET)
	public List<QuestionnaireSection> retrieveSurveyQuestions (){
		List<QuestionnaireSection> list = elementService.getAllSections();
		return list;
	}
	
	@CrossOrigin
	@RequestMapping (value = "/persistSurveyResponse", method = RequestMethod.POST)
	public ResponseEntity<String> saveSurvey (@RequestBody Map<String, String> response){
		try {
			// add validation of response
			surveyQuestionData.save(response, responseFile);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
		}
	}
}