package com.api.tests.datadriven;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.services.AuthService;
import com.dataproviders.api.bean.UserBean;

import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class LoginAPIExcelDataDrivenTest {

	private AuthService authService;

	@BeforeMethod(description = "Setting up the Auth Service reference")
	public void setup() {
		authService = new AuthService();
	}

	@Test(description = "Verify if login api is working for FD user", groups = { "api", "regression",
			"datadriven" }, dataProviderClass = com.dataproviders.DataProviderUtils.class, dataProvider = "LoginAPIExcelDataProvider")

	public void loginAPITest(UserBean userBean) throws IOException

	{
		// Rest Assured Code

		authService.login(userBean).then().spec(responseSpec_OK()).and().body("message", equalTo("Success")).and()
				.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));

	}
}
