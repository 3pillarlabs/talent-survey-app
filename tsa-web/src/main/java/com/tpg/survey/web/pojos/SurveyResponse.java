package com.tpg.survey.web.pojos;

import java.util.Date;
import java.util.Map;

public class SurveyResponse {
	
	
	private Date timeStamp;
	private Map<String, String> elementWiseResponse;
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public Map<String, String> getElementWiseResponse() {
		return elementWiseResponse;
	}
	public void setElementWiseResponse(Map<String, String> elementWiseResponse) {
		this.elementWiseResponse = elementWiseResponse;
	}
	@Override
	public String toString() {
		return "SurveyResponse [timeStamp=" + timeStamp + ", elementWiseResponse=" + elementWiseResponse + "]";
	}
	

}
