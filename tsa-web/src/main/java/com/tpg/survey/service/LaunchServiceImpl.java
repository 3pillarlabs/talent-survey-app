/**
 * 
 */
package com.tpg.survey.service;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.scheduling.annotation.Async;
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
	
	// Launch survey in a separate thread
	@Async
	@Transactional
	@Override
	public void initiateSurvey(Survey survey) {
		try {
			// save survey dates
			Survey persistedSurvey = surveyRepository.save(survey);
			
			// collect email addresses
			List<EmailAddress> emails = mailService.getEmployeeEmails();
			
			// generate and save links
			List<Link> links = linkService.createAndSaveLinks(persistedSurvey, emails);
			
			// Email survey links to employees
			mailService.sendEmails(links);
			
		} catch (LinkGenerationException lge) {
			lge.printStackTrace();
		} finally {
			// Email administrators about survey launch
			mailService.sendEmail();
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