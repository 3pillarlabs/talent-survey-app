/**
 * 
 */
package com.tpg.survey.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author amit.bharti
 *
 */
@Entity
@Table(name = "survey")
public class Survey extends BaseDomain {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "survey_id")
	private int surveyId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "start_date")
	private Date surveyStartDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "end_date")
	private Date surveyEndDate;
	
	@Column(name = "is_active")
	private boolean isActive = Boolean.TRUE;
	
	@Column(name = "is_launched")
	private boolean isLaunched = Boolean.FALSE;
	
	@OneToMany (mappedBy = "survey")
	@JsonBackReference
	private Set<QuestionnaireSection> sections;
	
	public Date getSurveyStartDate() {
		return surveyStartDate;
	}

	public void setSurveyStartDate(Date surveyStartDate) {
		this.surveyStartDate = surveyStartDate;
	}

	public Date getSurveyEndDate() {
		return surveyEndDate;
	}

	public void setSurveyEndDate(Date surveyEndDate) {
		this.surveyEndDate = surveyEndDate;
	}

	public int getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(int surveyId) {
		this.surveyId = surveyId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isLaunched() {
		return isLaunched;
	}

	public void setLaunched(boolean isLaunched) {
		this.isLaunched = isLaunched;
	}

	public Set<QuestionnaireSection> getSections() {
		return sections;
	}

	public void setSections(Set<QuestionnaireSection> sections) {
		this.sections = sections;
	}

}