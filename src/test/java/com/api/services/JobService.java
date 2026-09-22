package com.api.services;

import static io.restassured.RestAssured.given;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.utils.SpecUtil;

import io.restassured.response.Response;

public class JobService {

	private static final String CREATE_JOB_ENDPOINT="/job/create";
	
	public Response createJob(Role role, CreateJobPayload createJobPayload) {
		return given()
				.spec(SpecUtil.requestSpecWithAuth(role, createJobPayload))
				.when()
				.post(CREATE_JOB_ENDPOINT);

	}
}
