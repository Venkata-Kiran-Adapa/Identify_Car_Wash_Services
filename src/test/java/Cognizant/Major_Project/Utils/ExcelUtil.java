package Cognizant.Major_Project.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	
	public static  Object[][] readXlData() throws IOException {
		File file = new File("src\\test\\resources\\resource\\cityNames.xlsx");
		if (!file.exists() || file.length() == 0) {
		    throw new RuntimeException("Excel file is missing or empty.");
		}
		FileInputStream inputStream = new FileInputStream(file);
		Workbook book = new XSSFWorkbook(inputStream);
		Sheet sheet = book.getSheetAt(0);
		Object[] [] data = new Object[sheet.getPhysicalNumberOfRows() - 1][sheet.getRow(0).getLastCellNum()];	
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			Row row = sheet.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				data[i - 1][j] = row.getCell(j).toString();
			}
		}
		book.close();
		return data;
	}

	public static void writeData(List<String> list1, List<String> list2, String sheetName) throws IOException {
	    String filePath = "Output-ss/ExcelFIles/OutputXLFile.xlsx";
	    File file = new File(filePath);
	    XSSFWorkbook workbook;

	    if (!file.exists()) {
	        file.getParentFile().mkdirs(); 
	        workbook = new XSSFWorkbook();
	    } else {
	        try (FileInputStream fis = new FileInputStream(file)) {
	            workbook = new XSSFWorkbook(fis);
	        }
	    }

	    int sheetIndex = workbook.getSheetIndex(sheetName);
	    if (sheetIndex != -1) {
	        workbook.removeSheetAt(sheetIndex);
	    }
	    XSSFSheet sheet = workbook.createSheet(sheetName);
	    XSSFRow headerRow = sheet.createRow(0);

	    if (list2 == null) {
	        headerRow.createCell(0).setCellValue("Gym Names");
	    } else {
	        headerRow.createCell(0).setCellValue("CarWashServiceName");
	        headerRow.createCell(1).setCellValue("Contact Number");
	    }

	    for (int i = 0; i < list1.size(); i++) {
	        XSSFRow row = sheet.createRow(i + 1);
	        row.createCell(0).setCellValue(list1.get(i));
	        if (list2 != null && i < list2.size()) {
	            row.createCell(1).setCellValue(list2.get(i));
	        }
	    }
	    try (FileOutputStream fos = new FileOutputStream(filePath)) {
	        workbook.write(fos);
	    }

	    System.out.println("Sheet '" + sheetName + "' written successfully.");
	}
}
