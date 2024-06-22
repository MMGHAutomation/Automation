package excel;

import Utils.Helper;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtilsOLE2 {

	private HSSFSheet ExcelWSheet;

	private HSSFWorkbook ExcelWBook;

	private HSSFCell Cell;

	private HSSFRow Row;

	private static XSSFWorkbookType XLSX = null;
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";

	// This method is to set the File path and to open the Excel file, Pass Excel
	// Path and Sheet name as Arguments to this method

	public void setExcelFile(String Path, String SheetName) throws Exception{

		try {
			// Open the Excel file
			FileInputStream ExcelFile = new FileInputStream(new File(Path));

			// Access the required test excel data sheet

			ExcelWBook = new HSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);

		} catch (Exception e){
			throw (e);
		}
	}

	public HSSFSheet  getExcelSheet(){
		return ExcelWSheet;

	}

	public HSSFRow  getExcelRow(){
		return Row;
	}

	// This method is to read the test exceldata from the Excel cell, in this we are
	// passing parameters as Row num and Col num

	public String getCellData(int RowNum, int ColNum) throws Exception{
		DataFormatter formatter = new DataFormatter();
		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			String CellData = formatter.formatCellValue(Cell);
			//System.out.println("Cell value : "+ CellData);
			ExcelWBook.close();
			return CellData;
		} catch (Exception e) {
			return "";
		}
	}
	public void  setCellData(String cellValue, int RowNum, int ColNum, String path, String fileName) throws Exception{

		try {

			Row = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(ColNum, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if (Cell == null) {
				Cell = Row.createCell(ColNum);
				Cell.setCellValue(cellValue);
			} else {
				Cell.setCellValue(cellValue);
			}
			// Constant variables Test Data path and Test Data file name
			FileOutputStream fileOut = new FileOutputStream(path + fileName+".xlsx");
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();

		} catch (Exception e) {
            e.printStackTrace();
			System.out.println("Excel File is not avialable ");
		}
	}


	public List<String> getExcelRowBYID(int rowIndex) throws Exception {
		List<String> rowList = new ArrayList<String>();
		HSSFRow row = getExcelSheet().getRow(rowIndex);
		DataFormatter formatter = new DataFormatter();

		System.out.println("coulmn count : "+row.getPhysicalNumberOfCells());
		if (row == null) {
			// The whole row is blank
		} else {
			for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
				HSSFCell cell = row.getCell(i);
				//System.out.println(formatter .formatCellValue(cell));

				String value = formatter.formatCellValue(cell);
				String valueOneLine = Helper.getTextasOneLine(value);
				String valueWithoutExtraSpaces = Helper.getTextWithoutExtraSpacesBetweenWords(valueOneLine);
				rowList.add(valueWithoutExtraSpaces);
			}
			//rowList.add(formatter.formatCellValue(row.getCell(0)));
		}
		ExcelWBook.close();
		return rowList;
	}
	public List<String> getExcelColumnBYID(int firstRowID, int columnIndex) throws Exception {
		List<String> colList = new ArrayList<String>();
		int lastRow = ExcelWSheet.getLastRowNum();
		for (int i = firstRowID; i <= lastRow; i++) {
			colList.add(getCellData(i, columnIndex));
		}
		return colList;
	}
	public int getRowCount(int firstRow) {
		int lastRow = ExcelWSheet.getLastRowNum();
		System.out.println("----> last row index : " + lastRow);
		firstRow = firstRow -1;
		return  lastRow - firstRow;
	}
}