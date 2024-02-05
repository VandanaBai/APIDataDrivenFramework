package api.endpoints;

import api.payload.UserPayload;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

public class UserEndPoints2 {
	// create user End points class to implement all CURD methods
 static Properties  prop=new Properties();
 
 public static String deleteURL()throws IOException  {
	 prop.load(new FileInputStream(System.getProperty("user.dir")+".//src//test//resources//routes.properties"));
	String url= prop.getProperty("deleteUserURL");
	 return url;
 }

 public static ResourceBundle getURL()throws IOException  {
	ResourceBundle routesbundle=  ResourceBundle.getBundle("routes"); //it will take the routes.properties from test/src/resources
	//ResourceBundle is a class from java util where we can read the properties file
	 return routesbundle;
 }

	//create user
	public static Response createUser(UserPayload user) throws IOException  {
		String postURL= getURL().getString("createUserURL");
		Response response = given() // add headers from swagger
				.contentType("application/json")
				.accept("application/json")// body
				.body(user)
				.when().post(postURL);
		return response;
	}
	
	//Update user
		public static Response updateUser(UserPayload user, String userName) throws IOException {
			String putURL= getURL().getString("updateUserURL");
			Response response = given() // add headers from swagger
					.contentType("application/json")
					.accept("application/json")// body
					.pathParam("username", userName)
					.body(user)
					.when().put(putURL);
			return response;
		}

	// get user
	public static Response getUser(String userName) throws IOException {
		String getUserURL= getURL().getString("getUserURL");
		Response response = given()
				// add path parameter
				.pathParam("username", userName).
				when().get(getUserURL);
		return response;
	}
	
	// delete user
		public static Response deleteUser(String userName) throws IOException {
			Response response = given()
					// add path parameter
					.pathParam("username", userName).
					when().delete(deleteURL());
			return response;
		}

}
