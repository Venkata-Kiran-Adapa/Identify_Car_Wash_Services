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

import Cognizant.Major_Project.Reports.ListenerClass;
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
//	String url="https://www.justdial.com/";
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
	
	@Test(dataProvider = "Details")
	public void main(String location,String mobileNum) throws InterruptedException, IOException{
		test1(location);
		driver.navigate().to(url);
		driver.manage().deleteAllCookies();
		test2(mobileNum);
		driver.manage().deleteAllCookies();
		driver.navigate().to(url);
		
		testGymSearchAndScroll();
		System.out.println("driver quit");
		driver.quit();
	}
	
	public void test1(String location) throws InterruptedException, IOException {
	    page=new JustDialHomePage(driver);
		if(driver!=null) driver.manage().deleteAllCookies();
		page.closeLogin();
		page.closeOuterPopUp();
		page.setLocation(location);
		search = page.search();
		if(driver!=null) driver.manage().deleteAllCookies();
		search.setRating();
		Thread.sleep(1000);
		search.setTopRated();
		search.getServiceDetails();
		Thread.sleep(2000);
		listingPage= search.printDetails();
	}
	
	public void test2(String mobileNum){
		
		try{
    		if(driver!=null) driver.manage().deleteAllCookies();
            listingPage.fillFormWithInvalidPhone(mobileNum);
            String error = listingPage.getErrorMessage();
            System.out.println("Captured Error Message: " + error);
            homePage=listingPage.captureErrorScreenshot(mobileNum,++count);
    	} catch (Exception e) {
            System.out.println("Error capturing screenshot: " + e.getMessage());
            e.printStackTrace();
        }
	}
	    public void testGymSearchAndScroll() throws InterruptedException, IOException {
	    	if(driver!=null) driver.manage().deleteAllCookies();
	        homePage.clickGymLink();
	        homePage.selectLocation("gachibowli, Hyderabad");
	        homePage.scrolling();
//	        homePage.scrollAndExtractGymNames();
	        homePage.gymNames();
	    }
	


}
