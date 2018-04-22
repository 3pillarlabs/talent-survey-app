/**
 * 
 */
package com.tpg.survey.domain;

/**
 * @author amit.bharti
 *
 */
public class EmailAddress extends BaseDomain {

	private static final long serialVersionUID = 1L;

	private String email;

	public EmailAddress(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}