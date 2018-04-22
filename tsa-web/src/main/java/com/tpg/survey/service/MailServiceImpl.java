/**
 * 
 */
package com.tpg.survey.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.tpg.survey.domain.EmailAddress;
import com.tpg.survey.domain.Link;

/**
 * @author amit.bharti
 *
 */
@Service
public class MailServiceImpl implements MailService {

	@Transactional
	@Override
	public void sendEmail() {
	}

	// Hit Work Day to collect the emails of all employees
	@Transactional
	@Override
	public List<EmailAddress> getEmployeeEmails() {
		List<EmailAddress> emails = new ArrayList<>();
		EmailAddress e1 = new EmailAddress("amit.bharti@3pillarglobal.com");
		EmailAddress e2 = new EmailAddress("amitb.mca@gmail.com");
		emails.add(e1);
		emails.add(e2);
		
		return emails;
	}
	
	// send email with survey link to all employees
	@Override
	public void sendEmails(List<Link> links) {
	}
}