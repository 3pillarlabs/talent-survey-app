/**
 * 
 */
package com.tpg.survey.exception;

/**
 * @author amit.bharti
 *
 */
public class SurveyAlreadyRunningException extends Exception {
	private static final long serialVersionUID = 1L;

	public SurveyAlreadyRunningException(String message) {
		super(message);
	}
}
