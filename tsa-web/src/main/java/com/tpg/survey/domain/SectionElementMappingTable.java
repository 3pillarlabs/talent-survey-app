package com.tpg.survey.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity 
@Table (name = "section_element")
public class SectionElementMappingTable {
	
	@EmbeddedId
	private SectionElementId sectionElementId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("sectionId")
    private SurveySection surveySection;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("elementId")
    private SurveyElement surveyElement;
	
	@Column (name = "is_mandatory")
	private boolean isMandatory;

	public SectionElementId getSectionElementId() {
		return sectionElementId;
	}

	public void setSectionElementId(SectionElementId sectionElementId) {
		this.sectionElementId = sectionElementId;
	}

	public SurveySection getSurveySection() {
		return surveySection;
	}

	public void setSurveySection(SurveySection surveySection) {
		this.surveySection = surveySection;
	}

	public SurveyElement getSurveyElement() {
		return surveyElement;
	}

	public void setSurveyElement(SurveyElement surveyElement) {
		this.surveyElement = surveyElement;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	@Override
	public String toString() {
		return "SectionElementMappingTable [sectionElementId=" + sectionElementId + ", surveySection=" + surveySection
				+ ", surveyElement=" + surveyElement + ", isMandatory=" + isMandatory + "]";
	}
	
}
