package commonUtils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RequestSpec {
	/**
	 *   RequestSpecification
	 */
	
	public static RequestSpecification requestSpecification() {
		
		RequestSpecification req =new RequestSpecBuilder().setBaseUri(ConfigRead.getPropertyValue("BaseURIQA")).
		setContentType(ContentType.JSON).build();
		return req;
	}
	
	/**
	 *   ResponseSpecification
	 */
	
	public static ResponseSpecification responseSpecBuilder() {
	
	ResponseSpecification resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	return resspec;
	
	}

}
