package com.tpg.survey.service;

import com.tpg.survey.domain.Survey;
import com.tpg.survey.exception.SurveyAlreadyRunningException;

public interface SurveyService {
	Survey save(Survey survey) throws SurveyAlreadyRunningException;
}
