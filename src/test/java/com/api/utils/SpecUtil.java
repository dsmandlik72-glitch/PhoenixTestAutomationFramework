package com.api.utils;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import com.api.constant.Role;
import com.api.pojo.UserCredentials;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {
//Static methods

	
	public static RequestSpecification requestSpec() {
		// To take care of common request sections(Methods)

		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(ConfigManager.getProperty("BASE_URI")).setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON).log(LogDetail.URI).log(LogDetail.METHOD)
				.log(LogDetail.HEADERS).log(LogDetail.BODY).build();

		return requestSpecification;
	}
	
	public static RequestSpecification requestSpec(Object payload) {
		// To take care of common request sections(Methods)

		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(ConfigManager.getProperty("BASE_URI")).setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON).setBody(payload).log(LogDetail.URI).log(LogDetail.METHOD)
				.log(LogDetail.HEADERS).log(LogDetail.BODY).build();

		return requestSpecification;
	}

	public static RequestSpecification requestSpecWithAuth(Role role) {
		// To take care of common request sections(Methods)

		RequestSpecification requestSpecification = new RequestSpecBuilder()
				.setBaseUri(ConfigManager.getProperty("BASE_URI"))
				.setContentType(ContentType.JSON)
				.setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role))
				.log(LogDetail.URI)
				.log(LogDetail.METHOD)
				.log(LogDetail.HEADERS)
				.log(LogDetail.BODY)
				.build();

		return requestSpecification;
	}

	// Post, Put, Patch

	public static ResponseSpecification responseSpec_OK() {
		// To take care of common response sections(Methods)

		ResponseSpecification responseSpecifiction = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(200).expectResponseTime(Matchers.lessThan(2000L)).log(LogDetail.ALL).build();

		return responseSpecifiction;
	}
	
	public static ResponseSpecification responseSpec_JSON(int statusCode) {
		// To take care of common response sections(Methods)

		ResponseSpecification responseSpecifiction = new ResponseSpecBuilder().expectContentType(ContentType.JSON)
				.expectStatusCode(statusCode).expectResponseTime(Matchers.lessThan(2000L)).log(LogDetail.ALL).build();

		return responseSpecifiction;
	}
	
	public static ResponseSpecification responseSpec_TEXT(int statusCode) {
		// To take care of common response sections(Methods)

		ResponseSpecification responseSpecifiction = new ResponseSpecBuilder()
				.expectStatusCode(statusCode).expectResponseTime(Matchers.lessThan(2000L)).log(LogDetail.ALL).build();

		return responseSpecifiction;
	}
}
