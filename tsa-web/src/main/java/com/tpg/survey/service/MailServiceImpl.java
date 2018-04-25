/**
 * 
 */
package com.tpg.survey.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.tpg.survey.domain.EmailAddress;
import com.tpg.survey.domain.EmailStatus;
import com.tpg.survey.domain.Link;
import com.tpg.survey.utils.TalentSurveyUtilities;

/**
 * @author amit.bharti
 *
 */
@Service
public class MailServiceImpl implements MailService {

	@Inject
	private JavaMailSender javaMailSender;

	@Inject
	private TemplateEngine templateEngine;

	@Value("${sender.email}")
	private String senderEmail;

	@Value("${survey.email.subject}")
	private String mailSubject;

	@Value("${survey.admin.email}")
	private String adminEmail;

	@Value("${gui.hostname}")
	private String HOST_NAME;

	@Value("${gui.port}")
	private String PORT;

	@Transactional
	@Override
	public EmailStatus sendEmail(String recipientEmail, String senderEmail, String mailSubject, String mailBody) {
		try {
			MimeMessage mail = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			helper.setFrom(senderEmail);
			helper.setTo(recipientEmail);
			helper.setSubject(mailSubject);
			helper.setText(mailBody, true);
			javaMailSender.send(mail);
			return new EmailStatus(recipientEmail, senderEmail, mailSubject, mailBody).success();
		} catch (Exception e) {
			e.printStackTrace();
			return new EmailStatus(recipientEmail, senderEmail, mailSubject, mailBody).error(e.getMessage());
		}
	}

	// Call Work Day API to collect the email of all employees
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
	@Transactional
	@Override
	public void sendEmails(List<Link> links) {
		links.forEach(i -> {
			Context context = new Context();
			String link = prepareLink(i.getLinkId());
			context.setVariable("link", link);
			String mailBody = templateEngine.process("email/emailSurvey", context);
			sendEmail(i.getEmail(), this.senderEmail, this.mailSubject, mailBody);
		});
	}

	private String prepareLink(String linkId) {
		StringBuilder builder = new StringBuilder();
		builder.append(TalentSurveyUtilities.URL_PREFIX).append(HOST_NAME).append(TalentSurveyUtilities.COLON)
				.append(PORT).append(TalentSurveyUtilities.URL_SEPARATOR).append("survey")
				.append(TalentSurveyUtilities.URL_SEPARATOR).append(linkId);

		return builder.toString();
	}

	@Override
	public void sendNotification() {
		Context context = new Context();
		String mailBody = templateEngine.process("email/adminNotification", context);
		sendEmail(this.adminEmail, this.senderEmail, this.mailSubject, mailBody);
	}
}