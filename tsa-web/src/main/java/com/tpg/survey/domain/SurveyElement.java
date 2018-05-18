package com.tpg.survey.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tpg.survey.web.enums.ElementType;

@Entity
@Table (name = "tsa_element")
public class SurveyElement extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "element_id")
	private Long elementId;
	
	@Column(name = "element")
	private String element;
	
	@Column(name = "element_type", columnDefinition = "enum('TEXT','RADIOGROUP','HTML','BULLET','RATING')")
	@Enumerated(EnumType.STRING)
	private ElementType type;
	
	@Column(name = "options") // required for RADIOGROUP, RATING, BULLET type
	private String options;
	
	@Column(name = "min_value") // required only for RATING type
	private String minValue;
	
	@Column(name = "max_value") // required only for RATING type
	private String maxValue;
	
	@OneToMany( mappedBy = "surveyElement",
	        	cascade = CascadeType.ALL,
	        	orphanRemoval = true
	    )
	@JsonBackReference
	private List<SectionElementMappingTable> sections = new ArrayList<>();
	
	public Long getElementId() {
		return elementId;
	}

	public void setElementId(Long elementId) {
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

	public List<SectionElementMappingTable> getSections() {
		return sections;
	}

	public void setSections(List<SectionElementMappingTable> sections) {
		this.sections = sections;
	}

	@Override
	public String toString() {
		return "SurveyElement [elementId=" + elementId + ", element=" + element + ", type=" + type + ", options="
				+ options + ", minValue=" + minValue + ", maxValue=" + maxValue + ", sections=" + sections + "]";
	}
}
