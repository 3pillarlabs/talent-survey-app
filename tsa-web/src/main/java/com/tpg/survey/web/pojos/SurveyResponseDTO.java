package com.tpg.survey.web.pojos;

import java.util.Map;

public class SurveyResponseDTO {
	
	private String officeLocation;
	private String department;
	private String manager;
	private Map<String, String> questionIdVsAnswers;
	public String getOfficeLocation() {
		return officeLocation;
	}
	public void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public Map<String, String> getQuestionIdVsAnswers() {
		return questionIdVsAnswers;
	}
	public void setQuestionIdVsAnswers(Map<String, String> questionIdVsAnswers) {
		this.questionIdVsAnswers = questionIdVsAnswers;
	}
	@Override
	public String toString() {
		return "SurveyResponseDTO [officeLocation=" + officeLocation + ", department=" + department + ", manager="
				+ manager + ", questionIdVsAnswers=" + questionIdVsAnswers + "]";
	}
	
	

}
