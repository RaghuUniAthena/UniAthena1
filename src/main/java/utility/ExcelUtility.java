package utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

public class ExcelUtility {

	String sheetName;

	public ExcelUtility(String sheetName) {
		this.sheetName = sheetName;
	}

	public String readData(String header) throws IOException  {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\TestData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		XSSFRow row = sheet.getRow(0);
		int col_num = -1;
 
		for (int i = 0; i < row.getLastCellNum(); i++) {
			if (row.getCell(i).getStringCellValue().trim().equals(header))
				col_num = i;
		}
		if (col_num == -1) {
			Assert.fail("'"+header+"' heading is NOT found in TestData file");
			System.out.println("'"+header+"' heading is NOT found in TestData file");
		}

		row = sheet.getRow(1);
		XSSFCell cell = row.getCell(col_num);
		DataFormatter formatter = new DataFormatter();
		String value = formatter.formatCellValue(cell);
		System.out.println("Sheet:" +sheetName+"   "+header+ " - " + value);

		return value.trim();

	}
}
