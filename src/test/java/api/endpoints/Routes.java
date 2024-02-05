package api.endpoints;

public class Routes {
	//Create Routes class to mention all the URL's

	public static String base_URL = "https://petstore.swagger.io/v2";

	public static String createUserURL = base_URL + "/user";
	public static String updateUserURL = base_URL + "/user/{username}";
	public static String getUserURL = base_URL + "/user/{username}";
	public static String deleteUserURL = base_URL + "/user/{username}";

}
