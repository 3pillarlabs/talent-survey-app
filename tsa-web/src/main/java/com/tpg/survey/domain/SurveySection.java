package com.tpg.survey.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table (name = "tsa_section")
public class SurveySection extends BaseDomain {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "section_id")
	private Long sectionId;
	
	@Column(name = "section_title")
	private String sectionTitle;
	
	@ManyToMany (mappedBy = "sections")
	@JsonManagedReference
	private Set<Survey> surveys;
	
	@OneToMany( mappedBy = "surveySection", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true
	)
	@JsonManagedReference
	private Set<SurveyElement> elements;

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionTitle() {
		return sectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	public Set<Survey> getSurveys() {
		return surveys;
	}

	public void setSurveys(Set<Survey> surveys) {
		this.surveys = surveys;
	}

	public Set<SurveyElement> getElements() {
		return elements;
	}

	public void setElements(Set<SurveyElement> elements) {
		this.elements = elements;
	}

	@Override
	public String toString() {
		return "QuestionnaireSection [sectionId=" + sectionId + ", sectionTitle=" + sectionTitle + ", surveys="
				+ surveys + ", elements=" + elements + "]";
	}

}
