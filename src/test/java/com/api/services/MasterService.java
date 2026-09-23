package com.api.services;

import com.api.constant.Role;
import com.api.utils.SpecUtil;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class MasterService {

	public static final String MASTER_ENDPOINT = "/master";

	public Response master(Role role) {

		return given().spec(SpecUtil.requestSpecWithAuth(role)).when().post(MASTER_ENDPOINT);
	}
}
