package com.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class LoginAPITest {

	UserCredentials userCredentials;

	@BeforeMethod(description="Create the payload for login api")
	public void setUp() {
		userCredentials = new UserCredentials("iamfd", "password");

	}

	@Test(description = "Verify if login api is working for FD user", groups = { "api", "regression", "smoke" })
	public void loginAPITest() throws IOException

	{
		// Rest Assured Code

		given()
		.spec(requestSpec(userCredentials))
		.when()
		.post("login")
		.then()
		.spec(responseSpec_OK())
				.and()
				.body("message", equalTo("Success"))
				.and()
				.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));

	}
}
