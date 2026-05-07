package com.api.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import com.dataproviders.api.bean.UserBean;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CSVReaderUtil {
	/*
	 * Constructor is private
	 * 
	 * static - static methods! Job: Help me Read the CSV file and Map it a Bean
	 */
	private CSVReaderUtil() {
		// Due to Private Constructor No one can create Object of CSVReaderUtil class
		// outside the Class.
		// Singleton class also has Private Constructors
	}

	public static <T> Iterator<T> loadCSV(String pathOfCSVFile, Class<T> bean) {

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pathOfCSVFile);
		InputStreamReader isr = new InputStreamReader(is);
		CSVReader csvReader = new CSVReader(isr);// CSVReader Constructor

		CsvToBean<T> csvToBean = new CsvToBeanBuilder(csvReader)
				.withType(bean)
				.withIgnoreEmptyLine(true)
				.build();

		List<T> list = csvToBean.parse();
		return list.iterator();
	}
}
