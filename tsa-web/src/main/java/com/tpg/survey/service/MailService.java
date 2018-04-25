/**
 * 
 */
package com.tpg.survey.service;

import java.util.List;

import com.tpg.survey.domain.EmailAddress;
import com.tpg.survey.domain.EmailStatus;
import com.tpg.survey.domain.Link;

/**
 * @author amit.bharti
 *
 */
public interface MailService {
	List<EmailAddress> getEmployeeEmails();

	void sendEmails(List<Link> links);

	EmailStatus sendEmail(String recipientEmail, String senderEmail, String mailSubject, String mailBody);

	void sendNotification();
}
