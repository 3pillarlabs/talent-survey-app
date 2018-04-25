package com.tpg.survey.web.reports;

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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GenerateReport {

	private static Map<Integer, String> headerMap = new HashMap<>();
	
	public List<Map<Integer, String>> readResponseFile(String fileName) {
		List<Map<Integer, String>> responseList = new ArrayList<>();
		XSSFWorkbook workbook = null;
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
								headerMap.put(index, currentCell.getStringCellValue());
								break;
							case NUMERIC:
								headerMap.put(index, String.valueOf((int) (currentCell.getNumericCellValue())));
								break;
							default:
								break;
							}
							index++;
						}
					}
					isFirstRow = false;
				} else {
					Map<Integer, String> map = new HashMap<>();
					while (cellIterator.hasNext()) {
						Cell currentCell = cellIterator.next();
						if (currentCell != null) {
							if (currentCell.getCellTypeEnum() == CellType.STRING) {
								map.put(currentCell.getColumnIndex(), currentCell.getStringCellValue() );
							} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
								map.put(currentCell.getColumnIndex(), String.valueOf((int)(currentCell.getNumericCellValue())) );
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
	
	public  static void main(String args[]){
		GenerateReport generateReport = new GenerateReport();
		List<Map<Integer, String>> responseList = generateReport.readResponseFile("C://Users//deepti.dubey//Desktop//ResponseExcels//Response Sample 1.xlsx");
		System.out.println(responseList);
		
//		Map<Object, Long> grouped = responseList.stream().collect(Collectors.groupingBy(m ->m.get(1), Collectors.counting()	));
//		System.out.println(grouped);
		
		responseList.sort(Comparator.comparing(m -> m.get(1), Comparator.nullsLast(Comparator.naturalOrder())));
		System.out.println(responseList);
		Map<String, Map<Integer, String>> resultMap = new HashMap<>();
		int count = 0;
		for (int i=0; i< responseList.size(); i++){
			Map<Integer, String> m = responseList.get(i);
			if(resultMap.containsKey(m.get(1))){ // office location index is 1
				Map<Integer, String> containsMap = resultMap.get(m.get(1));
				for(Map.Entry<Integer, String> entry : m.entrySet()){
					String value2 = "";
					try {
						value2 = String.valueOf(Integer.valueOf(containsMap.get(entry.getKey())) + Integer.valueOf(entry.getValue()));
					} catch (NumberFormatException e) {
						System.out.println("wrong type containsMap :: " + containsMap.get(entry.getKey()));
						System.out.println("wrong type main map :: " + entry.getValue());
					} 
					if(value2.trim().length() > 0){
						containsMap.put(entry.getKey(), value2);
						count = 0;
					}
				}
			}else{
				resultMap.put(m.get(1), m);
			}
		}
		System.out.println(resultMap.toString());
		
	}

}
