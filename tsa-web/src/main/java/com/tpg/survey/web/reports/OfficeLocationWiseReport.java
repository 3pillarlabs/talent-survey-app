package com.tpg.survey.web.reports;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.tpg.survey.domain.QuestionnaireElement;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class OfficeLocationWiseReport {
	
	private Map<String, Map<String, String>> officeLocationData;
	
	private List<OfficeWiseLocationAvgScores> datasource = new ArrayList<>();
	public OfficeLocationWiseReport(Map<String, Map<String, String>> officeWiseData, List<QuestionnaireElement> list) {
		// {Cluj={4=Cluj, 6=5.5, 7=5.5}, Fairfax={4=Fairfax, 6=6.0, 7=8.5}, India={4=India, 6=8.0, 7=8.666666666666666}, Timisoara={4=Timisoara, 6=7.0, 7=6.375}}
		
		// [QuestionnaireElement [elementId=4, element=Office Location., type=RADIOGROUP, options=India, Cluj, Timisoara, US, Iasi, 
		// isMandatory=true, minValue=null, maxValue=null, section=com.tpg.survey.domain.QuestionnaireSection@285dd6be], QuestionnaireElement 
		// [elementId=6, element=How happy are you at work?, type=RATING, options=1,2,3,4,5,6,7,8,9,10, isMandatory=true, minValue=1, maxValue=10, 
		// section=com.tpg.survey.domain.QuestionnaireSection@71d66edc], QuestionnaireElement [elementId=7, element=How would you rate 3Pillar's culture?, type=RATING, options=1,2,3,4,5,6,7,8,9,10, isMandatory=true, minValue=1, maxValue=10, section=com.tpg.survey.domain.QuestionnaireSection@71d66edc]]

		this.officeLocationData = officeWiseData;
		for(Entry<String, Map<String, String>> e : officeWiseData.entrySet()){
			OfficeWiseLocationAvgScores oLocationAvgScores = new OfficeWiseLocationAvgScores();
			oLocationAvgScores.setOfficeLocation(e.getKey());
			Map<String, String> mapToAdd = new HashMap<>();
			for(Entry<String, String> e2 : e.getValue().entrySet()){
				String question = e2.getKey();
				for(QuestionnaireElement qe : list){
					if(qe.getElementId().trim().equalsIgnoreCase(question.trim())){
						String key = qe.getElement();
						mapToAdd.put(key, e2.getValue());
						break;
					}
				}
			}
			List<Map<String, String>> questionWiseAverageScores = new ArrayList<>();
			questionWiseAverageScores.add(mapToAdd);
			oLocationAvgScores.setQuestionWiseAverageScores(questionWiseAverageScores);
			datasource.add(oLocationAvgScores);
		}
		
		
	}

	public JasperPrint getReport() throws ColumnBuilderException, JRException, ClassNotFoundException {
		Style headerStyle = createHeaderStyle();
		Style detailTextStyle = createDetailTextStyle();
		Style detailNumberStyle = createDetailNumberStyle();
		DynamicReport dynaReport = getReport(headerStyle, detailTextStyle, detailNumberStyle);
		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(), new JRBeanCollectionDataSource(datasource));
		return jp;
	}

	private Style createHeaderStyle() {
		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(Font.VERDANA_MEDIUM_BOLD);
		sb.setBorder(Border.THIN());
		sb.setBorderBottom(Border.PEN_2_POINT());
		sb.setBorderColor(Color.BLACK);
		sb.setBackgroundColor(Color.ORANGE);
		sb.setTextColor(Color.BLACK);
		sb.setHorizontalAlign(HorizontalAlign.CENTER);
		sb.setVerticalAlign(VerticalAlign.MIDDLE);
		sb.setTransparency(Transparency.OPAQUE);
		return sb.build();
	}

	private Style createDetailTextStyle() {
		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(Font.VERDANA_MEDIUM);
		sb.setBorder(Border.DOTTED());
		sb.setBorderColor(Color.BLACK);
		sb.setTextColor(Color.BLACK);
		sb.setHorizontalAlign(HorizontalAlign.LEFT);
		sb.setVerticalAlign(VerticalAlign.MIDDLE);
		sb.setPaddingLeft(5);
		return sb.build();
	}

	private Style createDetailNumberStyle() {
		StyleBuilder sb = new StyleBuilder(true);
		sb.setFont(Font.VERDANA_MEDIUM);
		sb.setBorder(Border.DOTTED());
		sb.setBorderColor(Color.BLACK);
		sb.setTextColor(Color.BLACK);
		sb.setHorizontalAlign(HorizontalAlign.RIGHT);
		sb.setVerticalAlign(VerticalAlign.MIDDLE);
		sb.setPaddingRight(5);
		sb.setPattern("#,##0.00");
		return sb.build();
	}

	private AbstractColumn createColumn(String property, Class<?> type, String title, int width, Style headerStyle, Style detailStyle)
			throws ColumnBuilderException {
		AbstractColumn columnState = ColumnBuilder.getNew()
				.setColumnProperty(property, type.getName())
				.setTitle(title)
				.setWidth(Integer.valueOf(width))
				.setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		return columnState;
	}

	private DynamicReport getReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle)
			throws ColumnBuilderException, ClassNotFoundException {

		DynamicReportBuilder report = new DynamicReportBuilder();
		
		AbstractColumn columnQuestion = createColumn("officeLocation", String.class, "Question", 30, headerStyle, detailTextStyle);
		AbstractColumn column = createColumn("questionWiseAverageScores", List.class, "", 30, headerStyle, detailTextStyle);
		report.addColumn(columnQuestion).addColumn(column);
		/*for(Entry<String, Map<String, String>> e : officeLocationData.entrySet()){
			AbstractColumn column = createColumn("questionWiseAverageScores", List.class, e.getKey(), 30, headerStyle, detailTextStyle);
			report.addColumn(column);
		}*/
//		AbstractColumn columnSalary = createColumn("salary", Integer.class, "Salary", 30, headerStyle, detailNumStyle);

		StyleBuilder titleStyle = new StyleBuilder(true);
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle.setFont(new Font(20, Font._FONT_GEORGIA, true));

		StyleBuilder subTitleStyle = new StyleBuilder(true);
		subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		subTitleStyle.setFont(new Font(Font.MEDIUM, Font._FONT_GEORGIA, true));

		report.setTitle("OfficeLocation Wise Report");
		report.setTitleStyle(titleStyle.build());
		report.setSubtitle("tpg");
		report.setSubtitleStyle(subTitleStyle.build());
		report.setUseFullPageWidth(true);
		return report.build();
	}

}
