package excel;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class GenerateExcel {
	public static Logger log = LogManager.getLogger();
	private static String macPath = System.getProperty("user.dir")+ FileSystems.getDefault().getSeparator()+"TestReport"+FileSystems.getDefault().getSeparator();
	//private static String windowsPath = System.getProperty("user.dir")+ "\\TestReport\\";
	//private static String reportName;

	private String[] columns = { "Tag", "Test Case", "Time", "Status"};


	public File generateExcel(ArrayList<ArrayList<String>> excelList, String fileName) throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String directory = formatter.format(date);
		directory = directory + FileSystems.getDefault().getSeparator();
		File createDirectoryWithDate = new File(macPath+directory);
		if (!createDirectoryWithDate.exists()){
			createDirectoryWithDate.mkdirs();
		}
		//String filePath = windowsPath+fileName+".xlsx";
		String filePath = macPath+directory+fileName+".xlsx";
		log.info("excel report path  : "+filePath);
		//System.out.println("Mime Type : "+ XSSFWorkbookType.values());
		// Create a Workbook
		XSSFWorkbook workbook = new XSSFWorkbook(XSSFWorkbookType.XLSX); 
		workbook.setWorkbookType(XSSFWorkbookType.XLSX);
		log.info("Workbook Type : "+workbook.getWorkbookType());
		// Instantiate data formatter to use it to convert excel cells to String

		CreationHelper createHelper = workbook.getCreationHelper();
		// Create a Sheet
		Sheet sheet1 = workbook.createSheet(fileName);
		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		// Create a Row
		Row headerRow = sheet1.createRow(0);
		// Create cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}
		// Create Cell Style for formatting Date
		//CellStyle dateCellStyle = workbook.createCellStyle();
	//	dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
		// ==== Create Other rows and cells with Census data =========
			int rowNum = 1;
		// Generate census data
		if (excelList != null) {
			//log.info("cell Size : "+excelList.size());
			for (int i = 0; i < excelList.size(); i++) {
				//log.info("row iteration : "+i);
				Row row = sheet1.createRow(rowNum++);
				row.createCell(0).setCellValue(excelList.get(i).get(0).toString());
				//System.out.println("cell 0 "+excelList.get(i).get(0).toString());
				row.createCell(1).setCellValue(excelList.get(i).get(1).toString());
				//System.out.println("cell 1 "+excelList.get(i).get(1).toString());
				row.createCell(2).setCellValue(excelList.get(i).get(2).toString());
				//System.out.println("cell 2 "+excelList.get(i).get(2).toString());
				String status = excelList.get(i).get(3);
				if (status.equals("Fail")) {
					Cell cell = row.createCell(3);
					CellStyle style = workbook.createCellStyle();
					Font font = workbook.createFont();
					//font.setFontName("Courier New");
					font.setBold(true);
					//font.setUnderline(Font.U_SINGLE);
					font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
					style.setFont(font);
					style.setAlignment(HorizontalAlignment.CENTER);
					style.setVerticalAlignment(VerticalAlignment.CENTER);
					//style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.DARK_RED.getIndex());
					cell.setCellStyle(style);
					cell.setCellValue(status);
				}else {
					row.createCell(3).setCellValue(status);
					//System.out.println("cell 3 "+excelList.get(i).get(3).toString());
				}
			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet1.autoSizeColumn(i);
		}
		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream(filePath);
		workbook.write(fileOut);
		fileOut.close();
		// Closing the workbook
		workbook.close();
		File file = new File(filePath);
		return file;
	}
}
