package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.services.UserService;

public class UserDetailsAPITest {
	private UserService userService;

	@BeforeMethod(description = "Setting up the UserService instance")
	public void setup() {
		userService = new UserService();

	}

	@Test(description = "Verify if the UserDetails API Response is shown correctly", groups = { "api", "smoke",
			"regression" })
	public void userDetailsAPITest() throws IOException {

		userService.userDetails(FD).then().spec(responseSpec_OK()).and()
				.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsResponseSchema.json"));
	}
}
