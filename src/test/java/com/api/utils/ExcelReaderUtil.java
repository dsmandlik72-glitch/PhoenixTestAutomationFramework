package com.api.utils;

import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.api.request.model.UserCredentials;
import com.dataproviders.api.bean.UserBean;
import com.poiji.bind.Poiji;

public class ExcelReaderUtil {

	private ExcelReaderUtil() {

	}

	public static <T> Iterator<T> loadTestData(String xlsxFile,String sheetName, Class<T> clazz)  {
//APACHE POI OOXML LIB
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(xlsxFile);
		XSSFWorkbook myWorkBook=null;
		try {
			myWorkBook = new XSSFWorkbook(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Focus on the Sheet

		XSSFSheet mySheet = myWorkBook.getSheet(sheetName);//"LoginTestData"
	List<T> dataList	=Poiji.fromExcel(mySheet, clazz);
	return dataList.iterator();
		
	}
}
