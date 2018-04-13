package com.tpg.survey.web.data;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tpg.survey.web.pojos.Element;

@Repository
public interface SurveyQuestionData {
	
	public Map<String, List<Element>> getQuestions (String fileName);
	public void save(Map<String, String> response, String fileName);

}
