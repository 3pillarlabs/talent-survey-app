package com.tpg.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tpg.survey.domain.QuestionnaireSection;

@Repository
public interface SectionRepository  extends JpaRepository<QuestionnaireSection, Integer> {

}
