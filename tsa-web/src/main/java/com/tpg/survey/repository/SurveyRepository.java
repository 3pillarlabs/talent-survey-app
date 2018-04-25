/**
 * 
 */
package com.tpg.survey.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tpg.survey.domain.Survey;

/**
 * @author amit.bharti
 *
 */
@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {
	List<Survey> findByIsActiveAndIsLaunched(boolean isActive, boolean isLaunched, Sort sort);
}
