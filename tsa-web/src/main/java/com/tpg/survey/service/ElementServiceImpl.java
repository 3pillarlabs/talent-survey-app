package com.tpg.survey.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpg.survey.domain.QuestionnaireElement;
import com.tpg.survey.domain.QuestionnaireSection;
import com.tpg.survey.repository.QuestionnaireElementRepository;
import com.tpg.survey.repository.SectionRepository;
import com.tpg.survey.web.enums.ElementType;

@Service
public class ElementServiceImpl implements ElementService {

	@Autowired
	private QuestionnaireElementRepository elementRepository;
	
	@Autowired
	private SectionRepository sectionRepository;
	
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
	
	@Override
	public List<QuestionnaireSection> getAllSections(){
		List<QuestionnaireSection> list = sectionRepository.findAll();
		return list;
	}

}
