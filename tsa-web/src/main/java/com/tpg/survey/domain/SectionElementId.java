package com.tpg.survey.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SectionElementId extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column (name="section_id")
	private Long sectionId;
	
	@Column (name="element_id")
	private Long elementId;
	
	public SectionElementId(Long sectionId, Long elementId) {
		super();
		this.sectionId = sectionId;
		this.elementId = elementId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elementId == null) ? 0 : elementId.hashCode());
		result = prime * result + ((sectionId == null) ? 0 : sectionId.hashCode());
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
		SectionElementId other = (SectionElementId) obj;
		if (elementId == null) {
			if (other.elementId != null)
				return false;
		} else if (!elementId.equals(other.elementId))
			return false;
		if (sectionId == null) {
			if (other.sectionId != null)
				return false;
		} else if (!sectionId.equals(other.sectionId))
			return false;
		return true;
	}
	
	

}
