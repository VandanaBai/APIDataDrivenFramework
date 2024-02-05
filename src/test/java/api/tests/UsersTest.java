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

import api.endpoints.UserEndPoints;
import api.payload.UserPayload;
import api.utilities.ExcelUtility;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;


public class UsersTest {
	
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
	

	@Test(priority=0)
	public void createExcel() throws FileNotFoundException, IOException {
		log.info("Create excel and add new sheet if it does not exist");
		log.debug("Debug Message Logged !!!");
		//log.info("Info Message Logged !!!");
		//log.error("Error Message Logged !!!", new NullPointerException("NullError"));
		
		ExcelUtility xl=new ExcelUtility("D:\\Vandana\\notes\\userstestdata.xlsx");
		xl.setCellData("userstestdata1", 0, 0, "test2");
		xl.setCellData("userstestdata1", 0, 1, "test3");
		xl.fillGreenColor("userstestdata1", 0, 1);
		System.out.println("cell data______"+xl.getCellData("userstestdata1", 1, 0));
		xl.setCellData("userstestdata2", 0, 1, "test5");
		xl.fillRedColor("userstestdata2", 0, 1);
		log.info("Excel created and colors filled");
	}
	
	@Test(priority=1)
	public void testCreateUser() {
		log.info("create new user");	
	Response resp=UserEndPoints.createUser(userPayload);
	resp.then().log().body().statusCode(200);//chai assertion
	Assert.assertEquals(resp.getStatusCode(), 200); //testng assertion
	Assert.assertEquals(resp.contentType(), "application/json");
	resp.then().log().all();
	System.out.println("______"+this.userPayload.getUsername());
	log.info("post req completed");
	}

	@Test(priority=2)
	public void testGetUserByName() {
		log.info("get a  user details by passing user name");
		Response resp=UserEndPoints.getUser(this.userPayload.getUsername());
		resp.then().log().all();
		Assert.assertEquals(resp.getStatusCode(), 200);
		resp.then().body("username", equalTo(this.userPayload.getUsername()));
	}
	
	
	@Test(priority=3,dependsOnMethods = {"testCreateUser"} )
	public void testUpdateUserByName() {
		userPayload.setFirstName("test");
		userPayload.setPhone(fake.phoneNumber().cellPhone());
		Response resp=UserEndPoints.updateUser(userPayload, this.userPayload.getUsername());
		resp.then().log().all();
		Assert.assertEquals(resp.getStatusCode(), 200);
		Response afterUpdate=UserEndPoints.getUser(this.userPayload.getUsername());
		afterUpdate.then().body("firstName",equalTo("test"));
		Assert.assertEquals(afterUpdate.getStatusCode(), 200);
		afterUpdate.then().body("phone",equalTo(this.userPayload.getPhone()));
		
	}
	
	@Test(priority=4,dependsOnMethods = {"testCreateUser"} )
	public void testDeleteUserByName() {
		log.info("delete user by passing his username");
		Response resp=UserEndPoints.deleteUser(userPayload.getUsername());
		resp.then().log().body();
		Assert.assertEquals(resp.getStatusCode(), 200);
		
	}
}
