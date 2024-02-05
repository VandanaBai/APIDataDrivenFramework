package api.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.*;

public class DataProviders {

	// get all the user data to create new post requests
	@DataProvider(name = "testData")
	public String[][] getAllData() throws FileNotFoundException, IOException {
		String path = System.getProperty("user.dir") + "//TestData//usersData.xlsx";
		ExcelUtility xl = new ExcelUtility(path);
		String sheetName = "createUsers";
		int rows = xl.getRowCount(sheetName);
		int cols = xl.getCellCount(sheetName, rows);
		String postData[][] = new String[rows][cols];

		for (int i = 1; i <= rows; i++) {
			for (int j = 0; j < cols; j++) {
				postData[i - 1][j] = xl.getCellData(sheetName, i, j);
			}
		}
		return postData; // it returns 2 dimensional array object

	}

	// get only user names to pass as path parameters for get, delete and put
	// it is a single dimensional-arary
	@DataProvider(name = "testColData")
	public String[] getUserNamesData() throws FileNotFoundException, IOException {
		String path = System.getProperty("user.dir" )+ "//TestData//usersData.xlsx";
		ExcelUtility xl = new ExcelUtility(path);
		String sheetName = "createUsers";
		int rows = xl.getRowCount(sheetName);

		String postData[] = new String[rows];

		for (int i = 1; i <= rows; i++) {
			postData[i - 1] = xl.getCellData(sheetName, i, 1);
		}
		return postData;

	}

}
