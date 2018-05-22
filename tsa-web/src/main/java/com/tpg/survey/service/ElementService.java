package com.tpg.survey.service;

import java.util.List;

import com.tpg.survey.domain.SurveyElement;
import com.tpg.survey.domain.SurveySection;
import com.tpg.survey.web.enums.ElementType;

public interface ElementService {

	List<SurveyElement> getElementByType(List<ElementType> elementType);

	List<SurveySection> getAllSections();
}
