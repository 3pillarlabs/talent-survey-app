package com.tpg.survey.web.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tpg.survey.domain.SurveyElement;
import com.tpg.survey.service.ElementService;
import com.tpg.survey.web.enums.ElementType;

@Repository
public class ReportDataImpl implements ReportData {

	@Autowired
	private ElementService elementService;
	
	private final String officeLocationElementIdKey = "4"; // will fetch from DB further
	private static List<SurveyElement> elementsFromDb = new ArrayList<>();
	@Override
	public List<Map<String, String>> getDataFromResponseFile(String fileName) {
		
		List<Map<String, String>> responseList = new ArrayList<>();
		XSSFWorkbook workbook = null;
		Map<Integer, String> columnVsElementId = new HashMap<>();
		try {
			FileInputStream excelFile = new FileInputStream(new File(fileName));
			workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			boolean isFirstRow = true;
			while (iterator.hasNext()) {
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				if (isFirstRow) { 
					int index = 0;
					while (cellIterator.hasNext()) {
						Cell currentCell = cellIterator.next();
						if (currentCell != null) {
							switch (currentCell.getCellTypeEnum()) {
							case STRING:
								columnVsElementId.put(index, currentCell.getStringCellValue());
								break;
							case NUMERIC:
								columnVsElementId.put(index, String.valueOf((int) (currentCell.getNumericCellValue())));
								break;
							default:
								break;
							}
							index++;
						}
					}
					isFirstRow = false;
					System.out.println("Column vs elementid : " + columnVsElementId.toString());
				} else {
					Map<String, String> map = new HashMap<>();
					while (cellIterator.hasNext()) {
						Cell currentCell = cellIterator.next();
						if (currentCell != null) {
							if (currentCell.getCellTypeEnum() == CellType.STRING) {
								map.put(columnVsElementId.get(currentCell.getColumnIndex()), currentCell.getStringCellValue() );
							} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
								map.put(columnVsElementId.get(currentCell.getColumnIndex()), String.valueOf((int)currentCell.getNumericCellValue()));
							}
						}
					}
					responseList.add(map);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return responseList;
	}

	private void filterSortedResponseByElementType(List<Map<String, String>> responseList,
			List<ElementType> elementTypes) {
		elementsFromDb = elementService.getElementByType(elementTypes);
		System.out.println("elementsFromDb : " + elementsFromDb);
		List<String> keys = new ArrayList<>();
		for(SurveyElement q : elementsFromDb){
			keys.add(String.valueOf(q.getElementId()));
		}
		System.out.println("keys : " + keys);
		for (Map<String, String> m : responseList){
			m.keySet().retainAll(keys);
		}
	}

	@Override
	public Map<String, Map<String, String>> fetchOfficeLocationWiseData(String fileName) {
		List<Map<String, String>> responseList = getDataFromResponseFile(fileName);
		Map<String, Integer> occurences = new HashMap<>();
		System.out.println("List fully read from File : " + responseList);
		
		responseList.sort(Comparator.comparing(m -> m.get(officeLocationElementIdKey), Comparator.nullsLast(Comparator.naturalOrder())));
		System.out.println("Sorted based on office Location : " + responseList);
		
		List<ElementType> elementTypes = new ArrayList<>();
		elementTypes.add(ElementType.RATING);
		elementTypes.add(ElementType.RADIOGROUP);
		filterSortedResponseByElementType(responseList, elementTypes);
		System.out.println("Required data for office location wise analysis : " + responseList);
		
		
		
		Map<String, Map<String, String>> resultMap = new HashMap<>();
		for (int i=0; i< responseList.size(); i++){
			Map<String, String> m = responseList.get(i);
			if(resultMap.containsKey(m.get(officeLocationElementIdKey))){ // office location index
				Map<String, String> containsMap = resultMap.get(m.get(officeLocationElementIdKey)); // containsMap = {4=Cluj,6=3,7=3, 4=5} 
				for(Entry<String, String> entry : m.entrySet()){ // m = {4=Cluj,6=8,7=8, 9=10}
					if(!entry.getKey().equalsIgnoreCase(officeLocationElementIdKey)){ // 4
						if(containsMap.containsKey(entry.getKey())){
							int value = Integer.valueOf(entry.getValue()) + Integer.valueOf(containsMap.get(entry.getKey()));
							containsMap.put(entry.getKey(), String.valueOf(value));
						}else {
							containsMap.put(entry.getKey(), entry.getValue());
						}
					}
				}
				occurences.put(m.get(officeLocationElementIdKey), occurences.get(m.get(officeLocationElementIdKey))+1 );
			}else{
				occurences.put(m.get(officeLocationElementIdKey), 1);
				resultMap.put(m.get(officeLocationElementIdKey), m);
			}
		}
		System.out.println("Occurences of data : " + occurences.toString());
		System.out.println("Sum of data office wise : " + resultMap.toString());
		
		for(Entry<String, Integer> entry : occurences.entrySet()){
			Map<String, String> map = resultMap.get(entry.getKey());
			for(Entry<String, String> e : map.entrySet()){
				if(!e.getKey().equalsIgnoreCase(officeLocationElementIdKey)){
					double avg = Double.valueOf(e.getValue()) / (double)entry.getValue();
					map.put(e.getKey(), String.valueOf(avg));
				}
			}
		}
		System.out.println("Final Average Data" + resultMap.toString());
		return resultMap;
	}
}
