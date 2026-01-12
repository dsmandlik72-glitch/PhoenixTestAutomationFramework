package com.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigManagerOLD {

//Now we will write the program to Read the Properties File from src/test/resources/Config/config.properties

	static Properties prop = new Properties();// Create the Object of Properties Class

	private ConfigManagerOLD() {

	}

	static {

		// Operation of Loading the Properties File in the Memory!!
		// Static Block it will be executed Once during Class Loading Time.
		// Load the Properties File Using Load Method
		File configFile = new File(
				System.getProperty("user.dir") + File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"config"+File.separator+"config.properties");

		FileReader fileReader = null;
		try {
			fileReader = new FileReader(configFile);
			prop.load(fileReader);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getProperty(String key) {

		return prop.getProperty("BASE_URI");

	}

}
