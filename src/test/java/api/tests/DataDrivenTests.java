package api.tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.*;

import io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Random;

import api.endpoints.UserEndPoints;
import api.payload.UserPayload;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataDrivenTests {
	
	int uniqueID=0;	
	
	@Test(priority=1, dataProvider = "testData", dataProviderClass= DataProviders.class)
	public void postUsers(String id, String un, String fn, String ln, String email, String pwd, String phone, String userStatus) {
		//data providers will read data from excel sheet and store in these parameter variables
		UserPayload pojoData=new UserPayload();
		 Random rand = new Random();
		 //generate random integer b.w 0 t0 1000 to create unique id
		int userID= Integer.parseInt(id)+rand.nextInt(1000);
		System.out.println ("user id is -->"+userID);
		
		pojoData.setId(userID);
		pojoData.setUsername(un);
		pojoData.setFirstName(fn);
		pojoData.setLastName(ln);
		pojoData.setEmail(email);
		pojoData.setPassword(pwd);
		pojoData.setUserStatus(Integer.parseInt(userStatus));
		
		Response resp =UserEndPoints.createUser(pojoData);

		resp.then().body("code", equalTo(200));
		Assert.assertEquals(resp.getStatusCode(), 200);
		uniqueID=userID;
		
		Response getresp=UserEndPoints.getUser(un);
		getresp.then().statusCode(200);
		getresp.then().log().all();
		getresp.then().body("id", equalTo(uniqueID));
	}
	
	//get users
	//@Test(priority=2,dataProvider ="testColData",dataProviderClass= DataProviders.class)
	public void getUsers(String username) {
		Response resp=UserEndPoints.getUser(username);
		resp.then().statusCode(200);
		resp.then().log().all();
		resp.then().body("id", equalTo(uniqueID));
	}
	
	//get users
		@Test(priority=3,dataProvider ="testColData",dataProviderClass= DataProviders.class)
		public void deleteUsers(String username) {
			Response resp=UserEndPoints.deleteUser(username);
			resp.then().statusCode(200);
			
		}
 
}
