package com.tpg.survey.web.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tpg.survey.domain.SurveyElement;
import com.tpg.survey.service.ElementService;
import com.tpg.survey.web.enums.ElementType;


@RestController
public class GenerateReport {

	@Autowired
	private ElementService elementService;
	
	private static Map<Integer, String> columnVsElementId = new HashMap<>();
	private final String officeLocationElementIdKey = "4";
	private static Map<String, Integer> occurences = new HashMap<>();
	private static List<SurveyElement> elementsFromDb = new ArrayList<>();
	
	public List<Map<String, String>> readResponseFile(String fileName) {
		List<Map<String, String>> responseList = new ArrayList<>();
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
	// not used will be deleted later on 
	public List<Map<Integer, Object>> readResponseFileOlds(String fileName) {
		List<Map<Integer, Object>> responseList = new ArrayList<>();
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
					System.out.println("Column vs elementid " + columnVsElementId.toString());
				} else {
					Map<Integer, Object> map = new HashMap<>();
					while (cellIterator.hasNext()) {
						Cell currentCell = cellIterator.next();
						if (currentCell != null) {
							if (currentCell.getCellTypeEnum() == CellType.STRING) {
								map.put(currentCell.getColumnIndex(), currentCell.getStringCellValue() );
							} else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
								map.put(currentCell.getColumnIndex(), (int)(currentCell.getNumericCellValue()));
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
	
	@RequestMapping (value = "/generateReport", method = RequestMethod.GET)
	public void generateReport(){
		List<Map<String, String>> responseList = readResponseFile("C://Users//deepti.dubey//Desktop//ResponseExcels//Response2.xlsx");
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
				occurences.put(m.get(officeLocationElementIdKey), 1);// 
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
		try {
			generatePdfReport(resultMap);
		} catch (FileNotFoundException | DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			generatePdfReport(resultMap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public void generatePdfReport(Map<String, Map<String, String>> resultMap) throws FileNotFoundException, DocumentException {

		/*Document document = new Document();	
		PdfPTable table = new PdfPTable(5);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell("Question");
		for(Entry<String, Integer> head : occurences.entrySet()){
			table.addCell(head.getKey());
		}
		table.setHeaderRows(1);
		
		PdfPCell[] cells = table.getRow(0).getCells();
		for (int j = 0; j < cells.length; j++) {
			cells[j].setBackgroundColor(BaseColor.GRAY);
		}
		for (Entry<String, Map<String, String>> avgResult : resultMap.entrySet()) {
			table.addCell(avgResult.getKey() );
		}
		try {
			PdfWriter.getInstance(document, new FileOutputStream("sampleTable2.pdf"));
			document.open();
			document.add(table);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(document!=null)
		document.close();}*/
		Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("sampleTable.pdf"));
        document.open();
        PdfPTable table = new PdfPTable(5);
        table.setWidths(new int[]{ 2, 2, 2, 2, 2});
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Question"));
        table.addCell(cell);
        for(Entry<String, Integer> head : occurences.entrySet()){
			table.addCell(head.getKey());
		}
        for(SurveyElement question : elementsFromDb){
        	
        }
        for(Entry<String, Map<String, String>> row : resultMap.entrySet()){
        	
        }
//        cell.setRowspan(2);
//        table.addCell(cell);
//        cell = new PdfPCell(new Phrase("Name"));
//        cell.setColspan(3);
//        table.addCell(cell);
//        cell = new PdfPCell(new Phrase("Age"));
//        cell.setRowspan(2);
//        table.addCell(cell);
//        table.addCell("SURNAME");
//        table.addCell("FIRST NAME");
//        table.addCell("MIDDLE NAME");
//        table.addCell("1");
//        table.addCell("James");
//        table.addCell("Fish");
//        table.addCell("Stone");
//        table.addCell("17");
        document.add(table);
        document.close();
		System.out.println("Done");
		
		 /*Document document = new Document();
	        PdfWriter.getInstance(document, new FileOutputStream("sample2.pdf"));
	        document.open();
	        PdfPTable table = new PdfPTable(4);
	        PdfPCell cell = new PdfPCell(new Phrase(" 1,1 "));
	        table.addCell(cell);
	        cell = new PdfPCell(new Phrase(" 1,2 "));
	        table.addCell(cell);
	        PdfPCell cell23 = new PdfPCell(new Phrase("multi 1,3 and 1,4"));
	        cell23.setColspan(2);
	        cell23.setRowspan(2);
	        table.addCell(cell23);
	        cell = new PdfPCell(new Phrase(" 2,1 "));
	        table.addCell(cell);
	        cell = new PdfPCell(new Phrase(" 2,2 "));
	        table.addCell(cell);
	        document.add(table);
	        document.close();*/
	}

	
}
