package Cognizant.Major_Project.tests;




import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Cognizant.Major_Project.Utils.ExcelUtil;
import Cognizant.Major_Project.pages.CarWashServicesPage;
import Cognizant.Major_Project.pages.FreeListing;
import Cognizant.Major_Project.pages.JustDialHomePage;
import Cognizant.Major_Project.pages.JustDialHomePageForGym;

public class JustDialHomePageTest {
	WebDriver driver;
	JustDialHomePage page;
	CarWashServicesPage search;
	FreeListing listingPage;
	JustDialHomePageForGym homePage;
	
	String url="https://www.justdial.com/";
	
	@BeforeMethod
	public WebDriver setUpDriver() {
		EdgeOptions options=new EdgeOptions();
		options.addArguments("--disable-notifications");
		driver=new EdgeDriver(options);
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
            homePage=listingPage.captureErrorScreenshot(mobileNum);
    	} catch (Exception e) {
            System.out.println("Error capturing screenshot: " + e.getMessage());
            e.printStackTrace();
        }
	}
	    public void testGymSearchAndScroll() throws InterruptedException, IOException {
	    	if(driver!=null) driver.manage().deleteAllCookies();
	        homePage.clickGymLink();
//	        homePage.selectLocation("gachibowli, Hyderabad");
	        homePage.scrollAndExtractGymNames();
	        homePage.gymNames();
	    }
	


}
