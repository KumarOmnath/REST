package com.TestingAPITest;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.publisherResources.TestingResource;

import commonUtils.ConfigRead;
import commonUtils.ExcelReader;
import commonUtils.GenericUtility;
import commonUtils.RequestSpec;
import io.restassured.response.Response;

public class TestingApi {
	ExcelReader excelReader = new ExcelReader();

	Logger logger = Logger.getLogger(TestingApi.class);

	/**
	 * @author Checking Status code
	 *
	 */
	@Test(priority = 1, description = "Get page details Omnath")

	public void pageDetails() throws IOException {
		ArrayList<String> paramValue = excelReader.getData("RestAddbook", "RestAssured");

		ConfigRead.allConsoleLog();
		logger.info("==========get all page details=======");
		Response response = given().spec(RequestSpec.requestSpecification()).queryParam("pages", paramValue.get(1))
				.log().all().when().relaxedHTTPSValidation().get(TestingResource.pageallDetails()).then().log().all()
				.and().extract().response();

		logger.info("==========Assert for statuscode 200=======");
		Assert.assertEquals(200, response.statusCode());

	}

	/**
	 * @author extracting all resonse and validating
	 * @throws IOException
	 *
	 */
	@Test(priority = 2, description = "Get page all response")
	public void allResponse() throws IOException {
		// ConfigRead.allConsoleLog();

		ArrayList<String> paramValue = excelReader.getData("RestAddbook", "RestAssured");

		logger.info("==========get all page details when extracting response=======");
		Response response = given().spec(RequestSpec.requestSpecification()).queryParam("pages", paramValue.get(1))
				.log().all().when().relaxedHTTPSValidation().get(TestingResource.pageallDetails()).then().log().all()
				.and().extract().response();

		logger.info("==========getting all response=======" + response);

		int allData = GenericUtility.rawToJson(response).get("data.size()");

		System.out.println("alldata" + allData);
		int statusCode = response.getStatusCode();
		logger.info("==========getting response Code=======" + statusCode);

		String responseLine = response.getStatusLine();
		logger.info("==========getting response Line=======" + responseLine);

		Long ResponseTime = response.time();
		System.out.println("Response Time in Mili Second for Request is\t" + ResponseTime);

		/**
		 * @author extracting and validating first and last name
		 *
		 */

		for (int i = 0; i < allData; i++) {

			String firstName = GenericUtility.rawToJson(response).get("data[" + i + "].first_name").toString();
			logger.info("==========all first namee=======" + firstName);
			String lastName = GenericUtility.rawToJson(response).get("data[" + i + "].last_name").toString();
			logger.info("==========all last namee=======" + lastName);

			if (firstName.equalsIgnoreCase("George")) {
				String gorgeLastName = GenericUtility.rawToJson(response).get("data[" + i + "].last_name").toString();
				System.out.println("==========gorgeLastName=======" + gorgeLastName);
				Assert.assertEquals(gorgeLastName, "Bluth", "Last name is matched");

				/**
				 * @author extracting and validating avatar image is present or not
				 */
				String avtar = GenericUtility.rawToJson(response).get("data[" + i + "].avatar").toString();
				System.out.println(avtar);
				if (firstName.equalsIgnoreCase("Byron")) {
					String byronAvtarName = GenericUtility.rawToJson(response).get("data[" + i + "].avtar").toString();
					System.out.println(byronAvtarName);
				}

			}

		}
	}

}
