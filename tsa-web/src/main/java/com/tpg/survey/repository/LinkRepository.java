/**
 * 
 */
package com.tpg.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tpg.survey.domain.Link;

/**
 * @author amit.bharti
 *
 */
@Repository
public interface LinkRepository extends JpaRepository<Link, Integer> {
}
