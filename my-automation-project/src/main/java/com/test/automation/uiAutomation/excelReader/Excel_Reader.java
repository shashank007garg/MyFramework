package com.test.automation.uiAutomation.excelReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel_Reader {

	public FileOutputStream fileOut = null;
	public String path;
	public FileInputStream fis;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;

	public Excel_Reader(String path) {
		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String[][] getDataFromSheet(String sheetName) {

		int rowcount;
		int columncount;
		try {

			XSSFSheet sheet = workbook.getSheet(sheetName);

			rowcount = sheet.getLastRowNum() - sheet.getFirstRowNum();

			columncount = sheet.getRow(0).getLastCellNum();

			String dataSets[][] = new String[rowcount][columncount];
			System.out.println("-------- excel Reader Started ----------");

			for (int i = 1; i <= rowcount; i++) {

				for (int j = 0; j < columncount; j++) {
					System.out.println(sheet.getRow(i).getCell(j)
							.getStringCellValue());
					dataSets[i - 1][j] = sheet.getRow(i).getCell(j)
							.getStringCellValue();

				}
			}
			
			return dataSets;
		}

		catch (Exception e) {
			System.out.println("Exception in excel file" + e.getMessage());
		}
		return null;

	}

}
