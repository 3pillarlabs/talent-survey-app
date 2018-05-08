package com.tpg.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tpg.survey.domain.SurveyResponse;


@Repository
public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Integer>{

}
