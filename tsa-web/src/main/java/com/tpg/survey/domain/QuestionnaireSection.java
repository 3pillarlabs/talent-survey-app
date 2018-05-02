package com.tpg.survey.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "questionnaire_section")
public class QuestionnaireSection {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "section_id")
	private String sectionId;
	
	@Column(name = "section_title")
	private String sectionTitle;
	
	@ManyToOne
	@JoinColumn (name = "survey_id", nullable = false)
	private Survey survey;
	
	@OneToMany (mappedBy = "section")
	private Set<QuestionnaireElement> elements;

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionTitle() {
		return sectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Set<QuestionnaireElement> getElements() {
		return elements;
	}

	public void setElements(Set<QuestionnaireElement> elements) {
		this.elements = elements;
	}

}
