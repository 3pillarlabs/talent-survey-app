/**
 * 
 */
package com.tpg.survey.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.tpg.survey.domain.EmailAddress;
import com.tpg.survey.domain.Link;
import com.tpg.survey.domain.Survey;
import com.tpg.survey.exception.LinkGenerationException;
import com.tpg.survey.repository.LinkRepository;
import com.tpg.survey.utils.TalentSurveyUtilities;

/**
 * @author amit.bharti
 *
 */
@Service
public class LinkServiceImpl implements LinkService {

	@Inject
	private LinkRepository linkRepository;

	// To generate unique links for each email id
	private List<Link> generateLinks(Survey survey, List<EmailAddress> emails) throws LinkGenerationException {
		List<Link> links = null;

		if (emails.size() > 0) {
			links = new ArrayList<Link>();
			for (EmailAddress email : emails) {
				Link link = new Link();
				link.setLinkId(TalentSurveyUtilities.nextUniqueId());
				link.setEmail(email.getEmail());
				link.setSurvey_id(survey.getSurveyId());
				links.add(link);
			}
		}

		return links;
	}

	@Transactional
	@Override
	public List<Link> createAndSaveLinks(Survey survey, List<EmailAddress> emails) throws LinkGenerationException {
		List<Link> links = generateLinks(survey, emails);
		return linkRepository.saveAll(links);
	}
}