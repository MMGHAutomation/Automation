package excel;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils{

	private XSSFSheet ExcelWSheet;

	private XSSFWorkbook ExcelWBook;

	private XSSFCell Cell;

	private XSSFRow Row;

	private static XSSFWorkbookType XLSX = null;
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";

	public void setExcelFile(String Path, String SheetName) throws Exception{

		try {
			// Open the Excel file
			FileInputStream ExcelFile = new FileInputStream(new File(Path));

			// Access the required test excel data sheet

			ExcelWBook = new XSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);

		} catch (Exception e){
			throw (e);
		}
	}

	public XSSFSheet getExcelSheet(){
		return ExcelWSheet;

	}

	public XSSFRow getExcelRow(){
		return Row;
	}

	// Get last row from excel file.
	public XSSFRow getLastRowIndex() {
		int sheetTotalRowsCount = getExcelSheet().getPhysicalNumberOfRows();
		XSSFRow row = ExcelWSheet.getRow(sheetTotalRowsCount);
		return row;

	}
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
		XSSFRow row = getExcelSheet().getRow(rowIndex);
		DataFormatter formatter = new DataFormatter();

		if (row == null) {
			// The whole row is blank
		} else {
			for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
				XSSFCell cell = row.getCell(i);
				//System.out.println(formatter .formatCellValue(cell));
				rowList.add(formatter.formatCellValue(cell));
			}
			//rowList.add(formatter.formatCellValue(row.getCell(0)));
		}
		ExcelWBook.close();
		return rowList;
	}
}