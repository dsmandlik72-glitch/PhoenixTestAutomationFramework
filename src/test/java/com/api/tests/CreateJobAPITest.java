package com.api.tests;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;

import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

public class CreateJobAPITest {
	


	
	@Test
	public void createJobAPITest() {
		//Creating CreateJobPayloadObject
		
		Customer customer=new Customer("Jatin", "Shharma", "7045663552", "", "jatinvsharma@gmail.com", "");
		System.out.println(customer.first_name());
		CustomerAddress customerAddress=new CustomerAddress("D 404", "Vasant Galaxy", "Bangur Nagar", "Inorbit", "Mumbai", "411039", "India", "Maharashtra");
		System.out.println(customerAddress.apartment_name());
		CustomerProduct customerProduct=new CustomerProduct("2025-04-06T18:30:00.000Z", "61332187442753", "61332187442753", "61332187442753","2025-04-06T18:30:00.000Z", 1, 1);
		Problems problems=new Problems(1, "Battery Issue");
		List<Problems> problemList=new ArrayList<Problems>();
		problemList.add(problems);
		CreateJobPayload createJobPayload=new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemList);
		
		
		given()
		.spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
		
		
		.when()
		.post("/job/create")
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
		.body("message",equalTo("Job created successfully. "))
		.body("data.mst_service_location_id",equalTo(1))
		.body("data.job_number",startsWith("JOB_"));


		
		
	}
}
