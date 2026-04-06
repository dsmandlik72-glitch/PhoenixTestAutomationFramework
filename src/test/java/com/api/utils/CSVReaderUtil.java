package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.dataproviders.api.bean.UserBean;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CSVReaderUtil {
/*
 * Constructor is private
 * 
 * static - static methods!
 * Job: Help me Read the CSV file and Map it a Bean
 */
private CSVReaderUtil() {
	//Due to Private Constructor No one can create Object of CSVReaderUtil class outside the Class.
	//Singleton class alos has Private Constructors.
}
	
public static void loadCSV(String pathOfCSVFile)  {
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile);
		InputStreamReader isr = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(isr);// CSVReader Constructor
		
		
		CsvToBean<UserBean> csvToBean=new CsvToBeanBuilder(csvReader)
				.withType(UserBean.class)
				.withIgnoreEmptyLine(true)
				.build();
		
		List<UserBean> userList=csvToBean.parse();
		System.out.println(userList);
	

	}
}

