package com.tpg.survey.web.pojos;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SurveyResponse {
	
	private Date timeStamp;
	private String officeLocation;
	private String department;
	private String directManager;
	private List<Map<String, String>> elementWiseResponse;
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
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
	public String getDirectManager() {
		return directManager;
	}
	public void setDirectManager(String directManager) {
		this.directManager = directManager;
	}
	public List<Map<String, String>> getElementWiseResponse() {
		return elementWiseResponse;
	}
	public void setElementWiseResponse(List<Map<String, String>> elementWiseResponse) {
		this.elementWiseResponse = elementWiseResponse;
	}
	@Override
	public String toString() {
		return "SurveyResponse [timeStamp=" + timeStamp + ", officeLocation=" + officeLocation + ", department="
				+ department + ", directManager=" + directManager + ", elementWiseResponse=" + elementWiseResponse
				+ "]";
	}
	
	
	
	

}
