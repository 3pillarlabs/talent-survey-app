package com.tpg.survey.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public interface ReportService {

	void writePdfReport(JasperPrint jp, HttpServletResponse response, final String reportName)  throws IOException, JRException;

}
