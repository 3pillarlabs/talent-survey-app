package com.tpg.survey.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tpg.survey.service.QuestionElementService;
import com.tpg.survey.service.ReportService;
import com.tpg.survey.web.data.ReportData;
import com.tpg.survey.web.enums.ElementType;
import com.tpg.survey.web.reports.OfficeLocationWiseReport;

import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private ReportData reportData;
	
	@Autowired
	private QuestionElementService quesElementService;
	
	@Value("${talent.survey.response.sample.file.name}") // needs to be changed
	private String responseFile;
	
	@RequestMapping (value = "/officeLocationWiseSurveyReport.pdf", method = RequestMethod.GET, produces = "application/pdf")
	public void getOfficeLocationWiseReport(final HttpServletResponse response) throws ColumnBuilderException, ClassNotFoundException, JRException, IOException{
		List<ElementType> elementTypes = new ArrayList<>();
		elementTypes.add(ElementType.RATING);
		elementTypes.add(ElementType.RADIOGROUP);
		OfficeLocationWiseReport report = new OfficeLocationWiseReport(reportData.fetchOfficeLocationWiseData(this.responseFile), quesElementService.getElementByType(elementTypes));
		JasperPrint jp = report.getReport();
		reportService.writePdfReport(jp, response, "officeLocationWiseSurveyReport");
		return;
	}

}
