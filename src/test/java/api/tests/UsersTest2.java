package api.tests;

import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints2;
import api.payload.UserPayload;
import api.utilities.ExcelUtility;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;


public class UsersTest2 {
	
	Faker fake;
	UserPayload userPayload;
	Logger log;	
	
	@BeforeClass //runs before all tests in this class
	public void setUp() {
		//to get log4j2 xml file and dependncies check this url 
		//https://howtodoinjava.com/log4j2/log4j2-xml-configuration-example/
		
		log=LogManager.getLogger(this.getClass());

		fake = new Faker();
		userPayload = new UserPayload();
	
	userPayload.setId(fake.idNumber().hashCode());
	userPayload.setUsername(fake.name().username());
	userPayload.setFirstName(fake.name().firstName());
	userPayload.setLastName(fake.name().lastName());
	userPayload.setEmail(fake.internet().safeEmailAddress());
	userPayload.setPassword(fake.internet().password(5,10));
	userPayload.setPhone(fake.phoneNumber().cellPhone());
			}
	

	
	@Test(priority=1)
	public void testCreateUser() throws IOException {
		log.info("create new user");	
	Response resp=UserEndPoints2.createUser(userPayload);
	resp.then().log().body().statusCode(200);//chai assertion
	Assert.assertEquals(resp.getStatusCode(), 200); //testng assertion
	Assert.assertEquals(resp.contentType(), "application/json");
	resp.then().log().all();
	System.out.println("______"+this.userPayload.getUsername());
	log.info("post req completed");
	}

	@Test(priority=2)
	public void testGetUserByName() throws IOException {
		log.info("get a  user details by passing user name");
		Response resp=UserEndPoints2.getUser(this.userPayload.getUsername());
		resp.then().log().all();
		Assert.assertEquals(resp.getStatusCode(), 200);
		resp.then().body("username", equalTo(this.userPayload.getUsername()));
	}
	
	
	@Test(priority=3,dependsOnMethods = {"testCreateUser"} )
	public void testUpdateUserByName() throws IOException {
		userPayload.setFirstName("test");
		userPayload.setPhone(fake.phoneNumber().cellPhone());
		Response resp=UserEndPoints2.updateUser(userPayload, this.userPayload.getUsername());
		resp.then().log().all();
		Assert.assertEquals(resp.getStatusCode(), 200);
		Response afterUpdate=UserEndPoints2.getUser(this.userPayload.getUsername());
		afterUpdate.then().body("firstName",equalTo("test"));
		Assert.assertEquals(afterUpdate.getStatusCode(), 200);
		afterUpdate.then().body("phone",equalTo(this.userPayload.getPhone()));
		
	}
	
	@Test(priority=4,dependsOnMethods = {"testCreateUser"} )
	public void testDeleteUserByName() throws IOException {
		log.info("delete user by passing his username");
		Response resp=UserEndPoints2.deleteUser(userPayload.getUsername());
		resp.then().log().body();
		Assert.assertEquals(resp.getStatusCode(), 200);
		
	}
}
