package com.tpg.survey.service;

import java.util.List;

import com.tpg.survey.domain.QuestionnaireElement;
import com.tpg.survey.web.enums.ElementType;

public interface QuestionElementService {

	List<QuestionnaireElement> getElementByType(List<ElementType> elementType);
}
