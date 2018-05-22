package com.tpg.survey.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class ReportServiceImpl implements ReportService{

	@Override
	public void writePdfReport(JasperPrint jp, HttpServletResponse response, final String reportName)  throws IOException, JRException{

		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=" + (reportName == null ? jp.getName() : reportName).replace('"', '_') + ".pdf");

		OutputStream outStream = response.getOutputStream();

		final byte[] pdfBytes = JasperExportManager.exportReportToPdf(jp);
		response.setContentLength(pdfBytes.length);

		final ByteArrayInputStream bais = new ByteArrayInputStream(pdfBytes);
		IOUtils.copy(bais, outStream);

		outStream.flush();

		IOUtils.closeQuietly(bais);
		IOUtils.closeQuietly(outStream);
	
		
	}

}
