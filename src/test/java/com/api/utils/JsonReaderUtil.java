package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.api.request.model.UserCredentials;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReaderUtil {
public static <T> Iterator<T> loadJSON(String fileName, Class<T[]> clazz)  {
/*	//demo.json---->src/test/resources/testData/demo.json
	InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream("testData\\demo.json");
	
	//Now we will Convert the JSON Object into Java Object!!! ===> Deserialization
	// Class ObjectMapper Dependency Jackson DataBind ===>Here we are converting JSON Object into Java Object
	
	ObjectMapper objectmapper=new ObjectMapper();
	//UserCredentials userCredentials=objectmapper.readValue(is, UserCredentials.class);
	*Based on InputStream is which we are giving it is create JavaObject of UserCredential Class which has 
	 2 Instance Variables username,password and values iamfd is given to username and password is given to password 
	 so this JSON Object is converted into Java Object.We need to stored newly created Java Object so it will store inside
	 UserCredentials userCredentials;
	 This line creates UserCredentials Object objectmapper.readValue(is, UserCredentials.class);
	 Here we mapped Json Object(demo.json) to UserCredentials Object (UserCredentials.java)
	 
	 Now we will create Array of Json Object
	
	List list=objectmapper.readValue(is, List.class);
Now we have Array of Json Object inside demo.json to read this we will write 	List list=objectmapper.readValue(is, List.class);

	System.out.println(list);*/

	
	InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
	ObjectMapper objectmapper=new ObjectMapper();
	T[] classArray;
	List<T> list=null;
	try {
		classArray = objectmapper.readValue(is, clazz);
		list=Arrays.asList(classArray);

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return list.iterator();
	
	
}
}
