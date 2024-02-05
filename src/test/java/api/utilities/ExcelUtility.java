package api.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;

public class ExcelUtility {

	 FileInputStream fis;
	 FileOutputStream fos;

	 String path;
	 XSSFWorkbook workbook;
	   XSSFSheet sheet;
	 XSSFRow row;
	 XSSFCell cell;
	 XSSFCellStyle style;

	public ExcelUtility(String path) {
		this.path = path;
	}

	// get total Row Count
	public  int getRowCount(String sheetName) throws FileNotFoundException, IOException {
		fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum();
		fis.close();
		workbook.close();
		return rowCount;

	}

	// get total cells Count in given row
	public  int getCellCount(String sheetName, int rowNum) throws FileNotFoundException, IOException {
		fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		int cellCount = row.getLastCellNum();

		workbook.close();
		fis.close();
		return cellCount;
	}

	// get cell data 
	public  String getCellData(String sheetName, int rowNum, int cellNum) throws FileNotFoundException, IOException {
		fis = new FileInputStream(path);
		
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		cell = row.getCell(cellNum);
		// returns the formated value of cell as a string regardless of cell type 
		//use Data formatter below or use setcelltype as string
		DataFormatter dataformat = new DataFormatter();
		String data = dataformat.formatCellValue(cell); //
		 //while reading numbers text from excel it throws error Cannot get a STRING value from a NUMERIC cell
		
		cell.setCellType(CellType.STRING);
		 String cellData=cell.getStringCellValue();
		 
		workbook.close();
		fis.close();
		return cellData;
	}

	// set cell data - like pass or fail
	public  void setCellData(String sheetName, int rowNum, int cellNum, String data)
			throws FileNotFoundException, IOException {

		File file = new File(path);
	

		// create new workbook file if file does not exist
		if (!file.exists()) {
			workbook = new XSSFWorkbook();
			fos = new FileOutputStream(path);
			workbook.write(fos);
		}
		fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		
		// create sheet if it does not exist
		if (workbook.getSheetIndex(sheetName) == -1) {
			workbook.createSheet(sheetName);
		}
		sheet = workbook.getSheet(sheetName);
		// create row if it does not exist
		if (sheet.getRow(rowNum) == null) {
			sheet.createRow(rowNum);
		}
		// create cell if it does not exist
		row = sheet.getRow(rowNum);
		if (row.getCell(cellNum) == null) {
			row.createCell(cellNum);
		}
		cell = row.getCell(cellNum);

		cell.setCellValue(data);
		fos = new FileOutputStream(path);
		workbook.write(fos);

		workbook.close();
		fis.close();
		fos.close();
	}

	// fill red color for failed tests
	public  void fillRedColor(String sheetName, int rowNum, int cellNum) throws FileNotFoundException, IOException {
		fis = new FileInputStream(path);
		
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		cell = row.getCell(cellNum);
		
		style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cell.setCellStyle(style);
		fos = new FileOutputStream(path);
		workbook.write(fos);
		workbook.close();
		fis.close();
		fos.close();
	}

	// fill green color for passed tests
	public  void fillGreenColor(String sheetName, int rowNum, int cellNum) throws FileNotFoundException, IOException {
		
		fis = new FileInputStream(path);
	
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		cell = row.getCell(cellNum);
		
		style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(style);
		
		fos = new FileOutputStream(path);
		workbook.write(fos);
		workbook.close();
		fis.close();
		fos.close();

	}

}
