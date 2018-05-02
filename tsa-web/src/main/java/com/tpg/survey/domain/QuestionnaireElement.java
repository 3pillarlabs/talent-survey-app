package com.tpg.survey.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tpg.survey.web.enums.ElementType;

@Entity
@Table (name = "questionnaire_element")
public class QuestionnaireElement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "element_id")
	private String elementId;
	
	@Column(name = "element")
	private String element;
	
	@Column(name = "element_type", columnDefinition = "enum('TEXT','RADIOGROUP','HTML','BULLET','RATING')")
	@Enumerated(EnumType.STRING)
	private ElementType type;
	
	@Column(name = "options") // required for RADIOGROUP, RATING, BULLET type
	private String options;
	
	@Column(name = "is_mandatory")
	private boolean isMandatory;
	
	@Column(name = "min_value") // required only for RATING type
	private String minValue;
	
	@Column(name = "max_value") // required only for RATING type
	private String maxValue;
	
	@ManyToOne
	@JoinColumn (name = "section_id", nullable = false)
	private QuestionnaireSection section;

	public String getElementId() {
		return elementId;
	}

	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public ElementType getType() {
		return type;
	}

	public void setType(ElementType type) {
		this.type = type;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public QuestionnaireSection getSection() {
		return section;
	}

	public void setSection(QuestionnaireSection section) {
		this.section = section;
	}
	
	
}
