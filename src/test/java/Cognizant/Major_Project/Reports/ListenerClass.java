package Cognizant.Major_Project.Reports;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
 
import com.aventstack.extentreports.Status;
 
public class ListenerClass implements ITestListener {
	
	WebDriver driver;
	
	@Override
	public void onStart(ITestContext context) {
		ReportsClass.createReport();
	}
	
	
	@Override
	public void onTestStart(ITestResult result) {
		ReportsClass.createTest(result.getMethod().getMethodName());
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		ReportsClass.test.log(Status.PASS, "This test passed");
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		ReportsClass.test.log(Status.FAIL, "This test failed");
		driver = (WebDriver) result.getTestContext().getAttribute("drivername");
		String str = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
		ReportsClass.test.addScreenCaptureFromBase64String(str);
	}
	
	@Override
	public void onFinish(ITestContext context) {
		ReportsClass.endReport();
	}
}
 
