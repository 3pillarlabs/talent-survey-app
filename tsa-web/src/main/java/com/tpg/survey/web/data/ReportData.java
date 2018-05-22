package com.tpg.survey.web.data;

import java.util.List;
import java.util.Map;

public interface ReportData {

	public List<Map<String, String>> getDataFromResponseFile(String fileName);
	public Map<String, Map<String, String>> fetchOfficeLocationWiseData(String fileName);
}
