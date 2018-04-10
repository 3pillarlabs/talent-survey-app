package com.tpg.survey.web.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;

import com.tpg.survey.web.enums.ElementType;
import com.tpg.survey.web.pojos.Element;

@Repository
public class RetrieveQuestionsData {
	
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
			                    	currentSection = String.valueOf(currentCell.getNumericCellValue());
			                    }
							} else if (currentCell.getColumnIndex() == 1) { // title
								element.setTitle(currentCell.getStringCellValue());

							} else if (currentCell.getColumnIndex() == 2) { // elementId
								if (currentCell.getCellTypeEnum() == CellType.STRING) {
									element.setElementId(currentCell.getStringCellValue());
			                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
			                    	element.setElementId(String.valueOf(currentCell.getNumericCellValue()));
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

    return questionnaire;
		
	}
	
	
	

}
