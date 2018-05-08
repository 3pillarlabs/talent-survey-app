package com.tpg.survey.service;

import java.util.List;

import com.tpg.survey.domain.QuestionnaireElement;
import com.tpg.survey.domain.QuestionnaireSection;
import com.tpg.survey.web.enums.ElementType;

public interface ElementService {

	List<QuestionnaireElement> getElementByType(List<ElementType> elementType);

	List<QuestionnaireSection> getAllSections();
}
