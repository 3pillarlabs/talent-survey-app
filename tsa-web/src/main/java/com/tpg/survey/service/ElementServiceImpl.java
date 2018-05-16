package com.tpg.survey.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpg.survey.domain.SurveyElement;
import com.tpg.survey.domain.SurveySection;
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
	public List<SurveyElement> getElementByType(List<ElementType> elementTypes) {

		List<SurveyElement> allElements = elementRepository.findAll();
		List<SurveyElement> resultList = new ArrayList<>();
		for(SurveyElement q : allElements){
			if(elementTypes.contains(q.getType())){
				resultList .add(q);
			}
		}
		return resultList;
	}
	
	@Override
	public List<SurveySection> getAllSections(){
		List<SurveySection> list = sectionRepository.findAll();
		return list;
	}

}
