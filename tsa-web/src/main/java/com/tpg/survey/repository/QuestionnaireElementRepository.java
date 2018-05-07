package com.tpg.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tpg.survey.domain.QuestionnaireElement;

@Repository
public interface QuestionnaireElementRepository extends JpaRepository<QuestionnaireElement, Integer> {

}