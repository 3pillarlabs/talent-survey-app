package com.tpg.survey.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table (name = "tsa_response")
public class SurveyResponse extends BaseDomain{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "responseId", column = @Column(name = "response_id", nullable = false)),
            @AttributeOverride(name = "questionId", column = @Column(name = "question_id", nullable = false)) })
	private SurveyResponseId surveyResponseId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id", nullable = false, insertable = false, updatable = false)
	private SurveyElement surveyElement; // cpk + fk
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "timestamp")
	private Date timestamp;
	
	@Column(name = "answer")
	private String answer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "survey_id", nullable = false, insertable = false, updatable = false)
	private Survey survey;

	public SurveyResponseId getSurveyResponseId() {
		return surveyResponseId;
	}

	public void setSurveyResponseId(SurveyResponseId surveyResponseId) {
		this.surveyResponseId = surveyResponseId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public SurveyElement getSurveyElement() {
		return surveyElement;
	}

	public void setSurveyElement(SurveyElement surveyElement) {
		this.surveyElement = surveyElement;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	@Override
	public String toString() {
		return "SurveyResponse [surveyResponseId=" + surveyResponseId + ", timestamp=" + timestamp + ", surveyElement="
				+ surveyElement + ", answer=" + answer + ", survey=" + survey + "]";
	}

}
