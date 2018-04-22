/**
 * 
 */
package com.tpg.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpg.survey.domain.Survey;

/**
 * @author amit.bharti
 *
 */
public interface SurveyRepository extends JpaRepository<Survey, Integer> {
}
