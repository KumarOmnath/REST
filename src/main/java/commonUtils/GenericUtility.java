package commonUtils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class GenericUtility  {

	/**
	 * @author kumar This method will store json response
	 */

	
	public static JsonPath rawToJson(Response r){ 
		String respon=r.asString();
		JsonPath Path=new JsonPath(respon);
		return Path;
	}

	
	
	
	
	}