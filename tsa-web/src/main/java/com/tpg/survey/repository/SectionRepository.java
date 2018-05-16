package com.tpg.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tpg.survey.domain.SurveySection;

@Repository
public interface SectionRepository  extends JpaRepository<SurveySection, Integer> {

}
