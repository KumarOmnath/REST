package resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * Extent Report Class
 */
public class ExtentReport implements IReporter {

	private ExtentReports extent;

	public static final DateFormat date = new SimpleDateFormat("YYYY_MM_dd HH mm ss ");
	public Date dateform = new Date();

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtentReports/ExtentReport-"+ date.format(dateform).trim().replace(' ', '_') + "html", true);

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> result = suite.getResults();

			for (ISuiteResult r : result.values()) {
				ITestContext context = r.getTestContext();

				buildTestNodes(context.getPassedTests(), LogStatus.PASS);
				buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
				buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
			
		}
	}

	
	
		extent.flush();
		extent.close();
	}

	@AfterMethod
	private void buildTestNodes(IResultMap tests, LogStatus status) {
		ExtentTest test;

		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				test = extent.startTest(result.getMethod().getMethodName());

				/*
				 * test.getTest(). = getTime(result.getStartMillis()); test.getTest().endedTime
				 * = getTime(result.getEndMillis());
				 */

				for (String group : result.getMethod().getGroups())
					test.assignCategory(group);

				String message = "API Automation Report " + status.toString().toLowerCase() + "ed";

				if (result.getThrowable() != null)
					message = result.getThrowable().getMessage();

				test.log(status, message);

				extent.endTest(test);
			}
		}
	}

}