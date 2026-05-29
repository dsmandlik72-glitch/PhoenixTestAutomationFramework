package com.api.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderUtil {
	public static void main(String[] args) throws IOException {
//APACHE POI OOXML LIB
		InputStream is = Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream("testData/PhoenixTestData.xlsx");
		XSSFWorkbook myWorkBook = new XSSFWorkbook(is);
		// Focus on the Sheet

		XSSFSheet mySheet = myWorkBook.getSheet("LoginTestData");
		XSSFRow myRow;
		XSSFCell myCell;
//		XSSFRow myRow = mySheet.getRow(1);
//		XSSFCell myCell = myRow.getCell(0);
//		System.out.println(myCell);
		
		int lastRowIndex=mySheet.getLastRowNum();
		System.out.println(lastRowIndex);
		
		XSSFRow rowHeader=mySheet.getRow(0);
		int lastIndexOfCol=rowHeader.getLastCellNum()-1;//Return the total number of cols
		System.out.println(lastIndexOfCol);
		
		for(int rowIndex=0;rowIndex<=lastRowIndex;rowIndex++) {
			for(int colIndex=0;colIndex<=lastIndexOfCol;colIndex++) {
				myRow=mySheet.getRow(rowIndex);
				myCell=myRow.getCell(colIndex);
				System.out.print(myCell+"  ");
				
			}
			System.out.println(" ");
		}
	}
}
