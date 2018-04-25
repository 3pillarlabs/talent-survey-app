/**
 * 
 */
package com.tpg.survey.service;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.tpg.survey.domain.EmailAddress;
import com.tpg.survey.domain.Link;
import com.tpg.survey.domain.Survey;
import com.tpg.survey.exception.LinkGenerationException;
import com.tpg.survey.repository.SurveyRepository;

/**
 * @author amit.bharti
 *
 */
@Service
public class LaunchServiceImpl implements LaunchService {
	
	@Inject
	private LinkService linkService;
	
	@Inject
	private MailService mailService;
	
	@Inject
	private SurveyRepository surveyRepository;
	
	@Transactional
	@Override
	public void initiateSurvey(Survey survey) {
		try {
			// collect email addresses
			List<EmailAddress> emails = mailService.getEmployeeEmails();
			
			// generate and save links
			List<Link> links = linkService.createAndSaveLinks(survey, emails);
			
			// Email survey links to employees
			mailService.sendEmails(links);
			
			// Mark the survey launched
			survey.setLaunched(Boolean.TRUE);
			surveyRepository.save(survey);

		} catch (LinkGenerationException lge) {
			lge.printStackTrace();
		} finally {
			// Send notification to administrators about survey launch
			mailService.sendNotification();
		}
	}
	
	public LinkService getLinkService() {
		return linkService;
	}

	public void setLinkService(LinkService linkService) {
		this.linkService = linkService;
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public SurveyRepository getSurveyRepository() {
		return surveyRepository;
	}

	public void setSurveyRepository(SurveyRepository surveyRepository) {
		this.surveyRepository = surveyRepository;
	}
}