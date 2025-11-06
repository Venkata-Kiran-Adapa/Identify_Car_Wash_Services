 package Cognizant.Major_Project.Reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
 
public class ReportsClass {
	public static ExtentReports reports;
	public static ExtentTest test;
	
	public static void createReport() {
		reports = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("test-output/report.html");
		reports.attachReporter(spark);
	}
	
	public static void createTest(String name) {
		test = reports.createTest(name);
	}
	
	public static void endReport() {
		reports.flush();
	}
	
	
}
