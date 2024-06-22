package excel;



/**
 * Handling patients,
 * Read all patients from excel
 * 
 * */



import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class ReadFromExcel {
	private ExcelUtils excelUtils;
	private XSSFSheet ExcelWSheet;
	private XSSFCell Cell;
	

	// Setup connection with excel by passing ExcelUtils, excel file path and sheet
	// name to class constructor
	public ReadFromExcel(ExcelUtils excelUtils, String path, String sheetName) throws Exception {
		this.excelUtils = excelUtils;
		this.excelUtils.setExcelFile(path, sheetName);

	}

}
