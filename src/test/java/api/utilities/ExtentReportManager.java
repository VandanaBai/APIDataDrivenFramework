package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {
	ExtentSparkReporter sparkReporter; // report UI html part
	ExtentReports report; // common report context we can add here
	ExtentTest test; // test related data
	String reportName;

	public void onStart(ITestContext testcontext) {
		// create time stamp for each report
		String timeStamp = new SimpleDateFormat("dd.mm.yyyy.mm.ss").format(new Date());
		reportName = "TestReport-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"\\Reports\\" + reportName);

		// title of report
		sparkReporter.config().setDocumentTitle("Rest Assured Data Driven TestNG Framework");
		sparkReporter.config().setReportName("PET Strore API report");
		sparkReporter.config().setTheme(Theme.DARK);

		// extent reports -- add common parts for test
		report = new ExtentReports();
		report.attachReporter(sparkReporter);
		report.setSystemInfo("Application:", "pet store");
		report.setSystemInfo("ENV", "QA");
		report.setSystemInfo("User name", System.getProperty("user.name"));
		report.setSystemInfo("system OS", System.getProperty("os.name"));
		report.setSystemInfo("user", "vandana");
	}

	public void onTestSuccess(ITestResult result) {
		// create test report for success case
		test = report.createTest(result.getName());
		test.createNode(result.getName());//to create test names in the report
		test.assignCategory(result.getMethod().getGroups()); //to create category in pie chart in reports

		test.log(Status.PASS, "test passed");
	}

	public void onTestFailure(ITestResult result) {
		// create test report for failure case
		test = report.createTest(result.getName());
		test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL, "test failed");
		test.log(Status.FAIL, result.getThrowable().getMessage());
		
		//in selenium how to capture screenshot only for failed tests
		/*test.log(Status.FAIL, test.addScreenCapture(capture(driver))+ "Test Failed");
		public static String capture(WebDriver driver) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File Dest = new File("src/../ErrImages/" + System.currentTimeMillis()
		+ ".png");
		String errflpath = Dest.getAbsolutePath();
		FileUtils.copyFile(scrFile, Dest);
		return errflpath;*/
	}

	public void onTestSkipped(ITestResult result) {
		// create test report for failure case
		test = report.createTest(result.getName());
		test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.SKIP, "test Skipped");
		test.log(Status.SKIP, result.getThrowable().getMessage());
	}

	// this flush method is mandatory to call to get the report generated
	public void onFinish(ITestContext testcontext) {
		report.flush();
	}
}
