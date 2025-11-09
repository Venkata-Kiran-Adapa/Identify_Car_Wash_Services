package Cognizant.Major_Project.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Cognizant.Major_Project.Reports.ListenerClass;
import Cognizant.Major_Project.Reports.ReportsClass;
import Cognizant.Major_Project.Utils.ExcelUtil;
import Cognizant.Major_Project.pages.CarWashServicesPage;
import Cognizant.Major_Project.pages.FreeListing;
import Cognizant.Major_Project.pages.JustDialHomePage;
import Cognizant.Major_Project.pages.JustDialHomePageForGym;

@Listeners(ListenerClass.class)
public class JustDialHomePageTest  {
	public static WebDriver driver;
	
	JustDialHomePage page;
	CarWashServicesPage search;
	FreeListing listingPage;
	JustDialHomePageForGym homePage;
	int count=0;
	
	Properties properties = new Properties();
	String url;
	{
	    try {
	        FileInputStream file = new FileInputStream("src/test/resources/resource/config.properties");
	        properties.load(file);
	        url = properties.getProperty("url");
	    } catch (IOException e) {
	        e.printStackTrace();
	        url = "https://www.justdial.com/";
	    }
	}
	
	@BeforeMethod
	public WebDriver setUpDriver(ITestContext context) {
		EdgeOptions options=new EdgeOptions();
		options.addArguments("--disable-notifications");
		driver=new EdgeDriver(options);
		context.setAttribute("driver", driver);
		driver.manage().window().maximize();
		driver.get(url);
		return driver;
	}
	
	@DataProvider(name = "Details")
	public Object[][] readLocation() throws IOException{
		return ExcelUtil.readXlData();
	}
	
	@Test(dataProvider = "Details", retryAnalyzer = Cognizant.Major_Project.Reports.RetryClass.class)
	public void testJustDialPage(String location,String mobileNum) throws InterruptedException, IOException{
		logInfo("Starting the Test 1");
		test1(location);
		logInfo("Test 1 passed");
		deleteCookies();
		driver.navigate().to(url);
		Thread.sleep(1000);
		deleteCookies();
		logInfo("Starting the Test 2");
		test2(mobileNum);
		logInfo("Test 2 passed");
		deleteCookies();
		driver.navigate().to(url);
		logInfo("Starting the Test 3");
		testGymSearchAndScroll();
		logInfo("Test 2 passed");
		tearDown();
	}
	
	public void test1(String location) throws InterruptedException, IOException {
	    page=new JustDialHomePage(driver);
		deleteCookies();
		page.closeLogin();
		page.closeOuterPopUp();
		logInfo("Setting Location and search bar with Car Wash Services");
		page.setLocation(location);
		search = page.search();
		deleteCookies();
		search.setRating();
		Thread.sleep(1000);
		search.setTopRated();
		search.getServiceDetails();
		Thread.sleep(2000);
		logInfo("Printing the Services Names and Phone Numbers");
		listingPage= search.printDetails();
		System.out.println("Test 1 Passed");
	}
	
	public void test2(String mobileNum){
		try{
    		deleteCookies();
            listingPage.fillFormWithInvalidPhone(mobileNum);
            String error = listingPage.getErrorMessage();
            logInfo("Logging Error Info");
            System.out.println("Captured Error Message: " + error);
            homePage=listingPage.captureErrorScreenshot(mobileNum,++count);
            logInfo("ScreenShot captured for second test");
            System.out.println("Test 2 Passed");
    	} catch (Exception e) {
            System.out.println("Error capturing screenshot: " + e.getMessage());
            e.printStackTrace();
        }
	}
	    public void testGymSearchAndScroll() throws InterruptedException, IOException {
	    	deleteCookies();
	    	logInfo("Redirecting to Gyms Page");
	        homePage.clickGymLink();
	        homePage.selectLocation("gachibowli, Hyderabad");
	        homePage.scrolling();
	        homePage.gymNames();
	        logInfo("Printing the Gyms Names and Storing it in Excel File");
	        System.out.println("Test 3 Passed");
	    }
	 
	    public void logInfo(String info) {
	    	ReportsClass.test.log(Status.INFO, info);
	    }
	    
	    public void deleteCookies() {
	    	if(driver!=null) driver.manage().deleteAllCookies();
	    }
	    
	    public void tearDown() {
	    	driver.quit();
	    }


}
