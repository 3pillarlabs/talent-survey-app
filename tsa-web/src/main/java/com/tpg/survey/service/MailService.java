/**
 * 
 */
package com.tpg.survey.service;

import java.util.List;

import com.tpg.survey.domain.EmailAddress;
import com.tpg.survey.domain.Link;

/**
 * @author amit.bharti
 *
 */
public interface MailService {
	void sendEmail();

	List<EmailAddress> getEmployeeEmails();

	void sendEmails(List<Link> links);
}
