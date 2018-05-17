package com.tpg.survey.domain;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.tpg.survey.domain.BaseDomain;

@Embeddable
public class SurveyResponseId extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "response_id", nullable = false)
	private String responseId;
	
	@Column(name = "question_id", nullable = false)
	private String questionId;

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questionId == null) ? 0 : questionId.hashCode());
		result = prime * result + ((responseId == null) ? 0 : responseId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SurveyResponseId other = (SurveyResponseId) obj;
		if (questionId == null) {
			if (other.questionId != null)
				return false;
		} else if (!questionId.equals(other.questionId))
			return false;
		if (responseId == null) {
			if (other.responseId != null)
				return false;
		} else if (!responseId.equals(other.responseId))
			return false;
		return true;
	}
	
	
}
