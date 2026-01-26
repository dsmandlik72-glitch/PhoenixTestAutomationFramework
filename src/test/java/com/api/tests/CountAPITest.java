package com.api.tests;

import static com.api.utils.AuthTokenProvider.getToken;
import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import com.api.constant.Role;

public class CountAPITest {
	@Test
	public void verifyCountAPIResponse() {

		given().baseUri(getProperty("BASE_URI")).header("Authorization", getToken(Role.FD)).log().uri().log().method()
				.log().headers().log().body().when().get("dashboard/count").then().log().all().statusCode(200)
				.body("message", equalTo("Success")).time(lessThan(1000L)).body("data", notNullValue())
				.body("data.size()", equalTo(3)).body("data.count", everyItem(greaterThanOrEqualTo(0)))
				.body("data.label", everyItem(not(blankOrNullString())))
				.body("data.key",containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"))
				.body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema-FD.json"));

	}

	@Test
	public void countAPITest_MissingAuthToken() {
		given().baseUri(getProperty("BASE_URI")).log().uri().log().method()
				.log().headers().log().body().when().get("dashboard/count").then().log().all().statusCode(401);
	}
}
