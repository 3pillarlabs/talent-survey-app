/**
 * 
 */
package com.tpg.survey.utils;

import java.util.UUID;

/**
 * @author amit.bharti
 *
 */
public class TalentSurveyUtilities {
	public static final String URL_PREFIX = "http://";
	public static final String URL_SEPARATOR = "/";
	public static final String COLON = ":";
	
	public static String nextUniqueId() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
