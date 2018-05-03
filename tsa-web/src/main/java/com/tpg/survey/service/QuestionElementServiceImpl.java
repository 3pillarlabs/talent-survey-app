package com.tpg.survey.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpg.survey.domain.QuestionnaireElement;
import com.tpg.survey.repository.QuestionnaireElementRepository;
import com.tpg.survey.web.enums.ElementType;

@Service
public class QuestionElementServiceImpl implements QuestionElementService {

	@Autowired
	private QuestionnaireElementRepository elementRepository;
	
	@Override
	public List<QuestionnaireElement> getElementByType(List<ElementType> elementTypes) {

		List<QuestionnaireElement> allElements = elementRepository.findAll();
		List<QuestionnaireElement> resultList = new ArrayList<>();
		for(QuestionnaireElement q : allElements){
			if(elementTypes.contains(q.getType())){
				resultList .add(q);
			}
		}
		return resultList;
	}

}
