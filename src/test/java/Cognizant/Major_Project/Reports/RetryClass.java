package Cognizant.Major_Project.Reports;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
 
public class RetryClass implements IRetryAnalyzer {
	
	int retry = 2;
	int count = 0;
	public boolean retry(ITestResult result) {
		if (count < retry) {
			count++;
			if (ReportsClass.test != null) {
				ReportsClass.test.log(Status.WARNING, "Retrying test: " + result.getName() + " | Attempt: " + count);
			}
			return true;
		}
		return false;
	}
}