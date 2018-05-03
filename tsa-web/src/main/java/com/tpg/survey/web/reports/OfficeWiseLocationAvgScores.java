package com.tpg.survey.web.reports;

import java.util.List;
import java.util.Map;

public class OfficeWiseLocationAvgScores {
	
	private String officeLocation;
	private List<Map<String, String>> questionWiseAverageScores;
	
	public String getOfficeLocation() {
		return officeLocation;
	}
	public void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}
	public List<Map<String, String>> getQuestionWiseAverageScores() {
		return questionWiseAverageScores;
	}
	public void setQuestionWiseAverageScores(List<Map<String, String>> questionWiseAverageScores) {
		this.questionWiseAverageScores = questionWiseAverageScores;
	}
	
	

}
