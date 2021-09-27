package com.TestingAPITest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.publisherResources.TestingResource;

import commonUtils.ConfigRead;
import commonUtils.ExcelReader;
import commonUtils.GenericUtility;
import commonUtils.RequestSpec;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TestingCreateAPI {
	ExcelReader excelReader = new ExcelReader();
	Logger logger = Logger.getLogger(TestingApi.class);

	/**
	 * @author Checking Status code
	 * @throws IOException
	 *
	 */
	@Test(priority = 3, description = "Post API for create user data")
	@SuppressWarnings("unchecked")
	public void getStatus() throws IOException {
		logger.info("==========get all page details=======");
		ArrayList<String> bodyValue = excelReader.getData("Restaddbody", "RestapiPost");
		/**
		 * @author Created Map object to take body value in key & value pair
		 */
		HashMap<String, Object> map = new HashMap<>();
		map.put("name", bodyValue.get(1));
		map.put("job", bodyValue.get(2));

		/**
		 * @author Extracting Response
		 */

		Response response = given().spec(RequestSpec.requestSpecification()).body(map).log().all().when()
				.post(TestingResource.postApiDetails()).then().log().all().extract().response();
		/**
		 * @author Checking id
		 */
		String id = GenericUtility.rawToJson(response).get("id");
		System.out.println("Id is " + id);
		/**
		 * @author Checking created at
		 */
		String createdAt = GenericUtility.rawToJson(response).get("createdAt");
		System.out.println("Created response time" + createdAt);

		/**
		 * @author Checking Status code,Statusline, time
		 */
		Assert.assertEquals(201 , response.getStatusCode());
		Assert.assertEquals("HTTP/1.1 201 Created", response.getStatusLine());
		long responseTime = response.time();
		logger.info("==========response time of create api======="+responseTime);


	}

}
