package com.tpg.survey.web.pojos;

import java.util.List;

import com.tpg.survey.web.enums.ElementType;

public class Element {
	
	private String elementId;
	private String title;
	private String element;
	private ElementType type;
	private List<String> options; // in case of radio button type
	private boolean isMandatory;
	public String getElementId() {
		return elementId;
	}
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public List<String> getOptions() {
		return options;
	}
	public void setOptions(List<String> options) {
		this.options = options;
	}
	public boolean isMandatory() {
		return isMandatory;
	}
	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	@Override
	public String toString() {
		return "Element [elementId=" + elementId + ", title=" + title + ", element=" + element + ", type=" + type
				+ ", options=" + options + ", isMandatory=" + isMandatory + "]";
	}
	
	

}
