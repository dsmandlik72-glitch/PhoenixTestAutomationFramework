package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.AuthTokenProvider.getToken;
import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class MasterAPITest {
	@Test
	public void masterAPITest() {
given()
.baseUri(getProperty("BASE_URI"))
.and()
.header("Authorization",getToken(FD))
.and()
.contentType("")
.log().all()
.when()
.post("master")//Default content-type application/url-formencoded
.then()
.log().all()
.statusCode(200)
.time(lessThan(1000L))
.body("message", equalTo("Success"))
.body("data", notNullValue())
.body("data", hasKey("mst_oem"))
.body("data", hasKey("mst_model"))
.body("$", hasKey("message"))
.body("$", hasKey("data"))
.body("data.mst_model.size()", greaterThan(0))
.body("data.mst_oem.size()", equalTo(2))
.body("data.mst_model.size()", greaterThan(0))
.body("data.mst_model.id", everyItem(notNullValue()))
.body("data.mst_model.name", everyItem(notNullValue()))
.body(matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));


	
}
@Test
public void invalidTokenMasterAPITest() {
	given()
	.baseUri(getProperty("BASE_URI"))
	.and()
	.header("Authorization","")
	.and()
	.contentType("")
	.log().all()
	.when()
	.post("master")//Default content-type application/url-formencoded
	.then()
	.log().all()
	.statusCode(401);
}}