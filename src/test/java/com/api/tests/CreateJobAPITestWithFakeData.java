package com.api.tests;

import static com.api.utils.DateTimeUtil.getTimeWithDaysAgo;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.Role;
import com.api.constant.ServiceLocation;
import com.api.constant.Warranty_Status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.DateTimeUtil;
import com.api.utils.FakerDataGenerator;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.github.javafaker.Faker;

import static com.api.utils.SpecUtil.*;

public class CreateJobAPITestWithFakeData {

	CreateJobPayload createJobPayload;
	private final static String COUNTRY = "India";

	@BeforeMethod(description = "Creating createjob api request payload")
	public void setup() {

		createJobPayload = FakerDataGenerator.generateFakeCreateJobData();

	}

	@Test(description = "Verify if the Create Job API  is able to create Inwarranty Job", groups = { "api", "smoke",
			"regression" })
	public void createJobAPITest() {
		// Creating CreateJobPayloadObject

		int customerId=given().spec(requestSpecWithAuth(Role.FD, createJobPayload)).when().post("/job/create").then()
				.spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.mst_service_location_id", equalTo(1))
				.body("data.job_number", startsWith("JOB_"))
				.extract().body().jsonPath().getInt("data.tr_customer_id");
		
		Customer expectedCustomerData = createJobPayload.customer();
		CustomerDBModel actualCustomerDataInDB =CustomerDao.getCustomerInfo(customerId);
		
		Assert.assertEquals(actualCustomerDataInDB.getFirst_name(), expectedCustomerData.first_name());
		Assert.assertEquals(actualCustomerDataInDB.getLast_name(), expectedCustomerData.last_name());
		Assert.assertEquals(actualCustomerDataInDB.getMobile_number(), expectedCustomerData.mobile_number());
		Assert.assertEquals(actualCustomerDataInDB.getEmail_id(), expectedCustomerData.email_id());
		Assert.assertEquals(actualCustomerDataInDB.getEmail_id_alt(), expectedCustomerData.email_id_alt());
		Assert.assertEquals(actualCustomerDataInDB.getMobile_number_alt(), expectedCustomerData.mobile_number_alt());
		
		CustomerAddressDBModel customerAddressFromDB = CustomerAddressDao
				.getCustomerAddressData(actualCustomerDataInDB.getTr_customer_address_id());

		Assert.assertEquals(customerAddressFromDB.getFlat_number(), createJobPayload.customer_address().flat_number());
		Assert.assertEquals(customerAddressFromDB.getApartment_name(), createJobPayload.customer_address().apartment_name());
		Assert.assertEquals(customerAddressFromDB.getArea(), createJobPayload.customer_address().area());
		Assert.assertEquals(customerAddressFromDB.getLandmark(), createJobPayload.customer_address().landmark());
		Assert.assertEquals(customerAddressFromDB.getState(), createJobPayload.customer_address().state());
		Assert.assertEquals(customerAddressFromDB.getStreet_name(), createJobPayload.customer_address().street_name());
		Assert.assertEquals(customerAddressFromDB.getCountry(), createJobPayload.customer_address().country());
		Assert.assertEquals(customerAddressFromDB.getPincode(), createJobPayload.customer_address().pincode());
	}
}
