/**
 * 
 */
package com.tpg.survey.service;

import java.util.List;

import com.tpg.survey.domain.EmailAddress;
import com.tpg.survey.domain.Link;
import com.tpg.survey.domain.Survey;
import com.tpg.survey.exception.LinkGenerationException;

/**
 * @author amit.bharti
 *
 */
public interface LinkService {
	List<Link> createAndSaveLinks(Survey survey, List<EmailAddress> emails) throws LinkGenerationException;
}
