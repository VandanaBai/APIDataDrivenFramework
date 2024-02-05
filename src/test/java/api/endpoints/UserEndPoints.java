package api.endpoints;

import api.payload.UserPayload;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class UserEndPoints {
	// create user End points class to implement all CURD methods

	//create user
	public static Response createUser(UserPayload user) {
		Response response = given() // add headers from swagger
				.contentType("application/json")
				.accept("application/json")// body
				.body(user)
				.when().post(Routes.createUserURL);
		return response;
	}
	
	//Update user
		public static Response updateUser(UserPayload user, String userName) {
			Response response = given() // add headers from swagger
					.contentType("application/json")
					.accept("application/json")// body
					.pathParam("username", userName)
					.body(user)
					.when().put(Routes.updateUserURL);
			return response;
		}

	// get user
	public static Response getUser(String userName) {
		Response response = given()
				// add path parameter
				.pathParam("username", userName).
				when().get(Routes.getUserURL);
		return response;
	}
	
	// delete user
		public static Response deleteUser(String userName) {
			Response response = given()
					// add path parameter
					.pathParam("username", userName).
					when().delete(Routes.deleteUserURL);
			return response;
		}

}
