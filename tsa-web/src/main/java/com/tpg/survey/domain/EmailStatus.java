/**
 * 
 */
package com.tpg.survey.domain;

/**
 * @author amit.bharti
 *
 */
public class EmailStatus extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";

	private final String recipientEmail;
	private final String subject;
	private final String mailBody;
	private final String senderEmail;
	private String status;
	private String errorMessage;

	public EmailStatus(String recipientEmail, String senderEmail, String subject, String mailBody) {
		this.recipientEmail = recipientEmail;
		this.senderEmail = senderEmail;
		this.subject = subject;
		this.mailBody = mailBody;
	}

	public EmailStatus success() {
		this.status = SUCCESS;
		return this;
	}

	public EmailStatus error(String errorMessage) {
		this.status = ERROR;
		this.errorMessage = errorMessage;
		return this;
	}

	public boolean isSuccess() {
		return SUCCESS.equals(this.status);
	}

	public boolean isError() {
		return ERROR.equals(this.status);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getRecipientEmail() {
		return recipientEmail;
	}

	public String getSubject() {
		return subject;
	}

	public String getMailBody() {
		return mailBody;
	}

	public String getSenderEmail() {
		return senderEmail;
	}
}