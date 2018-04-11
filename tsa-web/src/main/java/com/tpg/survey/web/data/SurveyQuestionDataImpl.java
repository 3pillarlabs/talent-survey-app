package com.tpg.survey.web.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;

import com.tpg.survey.web.enums.ElementType;
import com.tpg.survey.web.pojos.Element;

@Repository
public class SurveyQuestionDataImpl implements SurveyQuestionData{
	
	private static Map<String,String> idQuestionMap = new HashMap<>();
	
	public static Map<String, String> getIdQuestionMap() {
		return idQuestionMap;
	}

	@Override
	public Map<String, List<Element>> getQuestions (String fileName){
		
		Workbook workbook = null;
		Map<String, List<Element>> questionnaire = null;
        try {

            FileInputStream excelFile = new FileInputStream(new File(fileName));
            workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            boolean isFirstRow = true;
            questionnaire = new HashMap<String, List<Element>>();
            
            while (iterator.hasNext()) {
            	
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                if(isFirstRow){ // skipping header
                	isFirstRow = false;
                }
				else {
					Element element = new Element();
					String currentSection = "";
					while (cellIterator.hasNext()) {

						Cell currentCell = cellIterator.next();
						if (currentCell != null) {
							if (currentCell.getColumnIndex() == 0) { // section
								if (currentCell.getCellTypeEnum() == CellType.STRING) {
									currentSection = currentCell.getStringCellValue();
			                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
			                    	currentSection = String.valueOf((int)(currentCell.getNumericCellValue()));
			                    }
							} else if (currentCell.getColumnIndex() == 1) { // title
								element.setTitle(currentCell.getStringCellValue());

							} else if (currentCell.getColumnIndex() == 2) { // elementId
								if (currentCell.getCellTypeEnum() == CellType.STRING) {
									element.setElementId(currentCell.getStringCellValue());
			                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
			                    	element.setElementId(String.valueOf((int)(currentCell.getNumericCellValue())));
			                    }

							}
							else if (currentCell.getColumnIndex() == 3) { // element
								element.setElement(currentCell.getStringCellValue());

							} else if (currentCell.getColumnIndex() == 4) { // type
								element.setType(ElementType.getValueOf(currentCell.getStringCellValue()));

							} else if (currentCell.getColumnIndex() == 5) { // options
								if (currentCell.getStringCellValue() != null
										&& !currentCell.getStringCellValue().trim().isEmpty()) {
									String[] options = currentCell.getStringCellValue().split(",");
									if (options != null && options.length > 0) {
										List<String> opList = Arrays.asList(options);
										element.setOptions(opList);
									}
								}
							} else if (currentCell.getColumnIndex() == 6) { // isMandatory
								if(currentCell.getStringCellValue().equalsIgnoreCase("Yes")){
									element.setMandatory(true);
								}else if(currentCell.getStringCellValue().equalsIgnoreCase("No")){
									element.setMandatory(false);
								}
							}
						}
					}
					addToQuestionMap(element);
					if(questionnaire.containsKey(currentSection)){
						List<Element> alreadyPresentElements = questionnaire.get(currentSection);
						alreadyPresentElements.add(element);
					}else{
						 List<Element> elements = new ArrayList<Element>();
						 elements.add(element);
						questionnaire.put(currentSection, elements);
					}
				}
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }finally {
			if (workbook!=null){
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

//		Set<List<Element>> result = new HashSet<>();
//		Map<String, List<Element>> resultMap = new HashMap<>();
//		for (Entry<String, List<Element>> entry : questionnaire.entrySet()) {
//			result.add(entry.getValue());
//			entry.getValue();
//		}
    return questionnaire;
		
	}

	private void addToQuestionMap(Element element) {
		if(element!=null && element.getType()!=null && !element.getType().equals(ElementType.HTML)){
			if(!idQuestionMap.containsKey(element.getElementId()))
				idQuestionMap.put(element.getElementId(), element.getElement());
		}
		
	}

	@Override
	public void save(Map<String, String> responseMap, String fileName) {
		XSSFWorkbook workbook = null;
		File outputFile = new File(fileName);
		if (!outputFile.exists()) {
			workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("Sample sheet");
			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("TimeStamp");
			int headerIndex = 1;
			for (Entry<String, String> entry : idQuestionMap.entrySet()) {
				header.createCell(headerIndex).setCellValue(entry.getValue());
				headerIndex++;
			}

		}else {
			try {
				workbook = new XSSFWorkbook(outputFile);
			} catch (InvalidFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			XSSFSheet sheet = workbook.getSheet("Sample sheet");
			int currentRowIndex = sheet.getLastRowNum();
			Row row = sheet.createRow(currentRowIndex+1);
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			row.createCell(0).setCellValue(dateFormat.format(new Date()));
			int columnIndex = 0;
			for (Entry<String, String> entry : responseMap.entrySet()) {
				String question = null;
				if (idQuestionMap!=null && idQuestionMap.containsKey(entry.getKey())){
					question = idQuestionMap.get(entry.getKey());
				}
				if(question!=null){
					Row header = sheet.getRow(0);
					Iterator<Cell> headerIterator = header.iterator();
					while (headerIterator.hasNext()) {
						Cell headerCell = headerIterator.next();
						if (headerCell != null) {
							if (headerCell.getCellTypeEnum() == CellType.STRING) {
								if(headerCell.getStringCellValue().trim().equalsIgnoreCase(question)){
									columnIndex = headerCell.getColumnIndex();
								}
							} else if (headerCell.getCellTypeEnum() == CellType.NUMERIC) {
								if(String.valueOf(headerCell.getNumericCellValue()).trim().equalsIgnoreCase(question)){
									columnIndex = headerCell.getColumnIndex();
								}
							}
						}
					}
					row.createCell(columnIndex).setCellValue(entry.getValue());
				}
			}
		}
		try {
			FileOutputStream out =	new FileOutputStream(outputFile, true);
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	

}
