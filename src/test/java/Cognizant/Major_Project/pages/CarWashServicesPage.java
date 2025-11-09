package Cognizant.Major_Project.pages;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Cognizant.Major_Project.Utils.ExcelUtil;

public class CarWashServicesPage {
	
	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;
	
	public CarWashServicesPage(WebDriver driver) {
		this.driver=driver;
		this.wait=new WebDriverWait(driver, Duration.ofSeconds(20));
		this.js=(JavascriptExecutor) driver;
		PageFactory.initElements(driver,this);
	}

	@FindBy(xpath="//span[text() = 'Ratings']")
	WebElement ratingsElement;
	
	@FindBy(xpath="//span[text()='Top Rated']")
	WebElement topRatedElement;
	
	@FindBy(xpath = "//section[@id=\"best_deal_div\"]/section/span")
	WebElement innerPopUpClose;
	
	@FindBy(xpath = "//label[@for='4.0+']")
	WebElement setRatingElement;
	
	@FindBy(xpath="//div[@role='button']/child::span[text()='Show Number']")
	List<WebElement> showNumberBtn;
	
	@FindBy(xpath = "//h3")
	List<WebElement> serviceNames;
	
	@FindBy(id="listing_call_button")
    WebElement mobilElementClick;
	
	@FindBy(xpath="//div[text()='Contact Information']/following-sibling::div")
	WebElement contactInfoElement;
	
	@FindBy(xpath = "//span[@class='jsx-5dc0aa11bf0ffdf3 callcontent callNowAnchor']")
	List<WebElement>  getNumberElement;
	
	@FindBy(xpath="//div[@class=\"jsx-dcde576cdf171c2a jd_modal_close jdicon\"]")
	WebElement closePopUpElement;
	
	
	List<String> names=new ArrayList<String>(5);
	List<String> mobileNum=new ArrayList<String>(5);
	
	public void closeInnerPopUp() {
		try {
			
		wait.until(ExpectedConditions.visibilityOf(innerPopUpClose));
		innerPopUpClose.click();
		
		}catch(NoSuchElementException e) {
			System.out.println("Element Not Found "+ e.getLocalizedMessage());
		}catch(ElementClickInterceptedException e) {
			System.out.println(e.getLocalizedMessage());
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public void setRating() throws InterruptedException {
		try {
			
		wait.until(ExpectedConditions.visibilityOf(ratingsElement));
		ratingsElement.click();
		js.executeScript("arguments[0].click();",setRatingElement);
		}catch(NoSuchElementException e) {
			System.out.println("Element Not Found "+ e.getLocalizedMessage());
		}catch(ElementClickInterceptedException e) {
			System.out.println(e.getLocalizedMessage());
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	public void setTopRated() throws InterruptedException {
		try {
			
		wait.until(ExpectedConditions.visibilityOfAllElements(getNumberElement));
		getMobileNumber();
		wait.until(ExpectedConditions.elementToBeClickable(topRatedElement)).click();
		
		}catch(NoSuchElementException e) {
			System.out.println("Element Not Found "+ e.getLocalizedMessage());
		}catch(ElementClickInterceptedException e) {
			System.out.println(e.getLocalizedMessage());
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	public void getServiceDetails() throws InterruptedException{
		try {	
		wait.until(ExpectedConditions.visibilityOfAllElements(serviceNames));
		}
		catch(NoSuchElementException e) {
			System.out.println("Element Not Found "+ e.getLocalizedMessage());
		}catch(ElementClickInterceptedException e) {
			System.out.println(e.getLocalizedMessage());
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		for(int i=0;i<5;i++) {
			WebElement element=serviceNames.get(i);
			names.add(element.getText());
			WebElement numberElement=showNumberBtn.get(i);
			js.executeScript("arguments[0].scrollIntoView(true)", numberElement);
			Thread.sleep(1000);
			js.executeScript("arguments[0].click()", numberElement);
			Thread.sleep(1000);
//			wait.until(driver -> !contactInfoElement.getText().equals("Loading..."));
//			String contactNumString=contactInfoElement.getText();
//			mobileNum.add(contactNumString);
			wait.until(ExpectedConditions.elementToBeClickable(closePopUpElement)).click();
		}
		
		}
	
	private void getMobileNumber(){
		int i=0;
		for(WebElement element:getNumberElement) {
			mobileNum.add(element.getText().substring(1));
			i++;
			if(i==5) break;
		}
	}

	public FreeListing printDetails() throws IOException {
		for(int i=0;i<5;i++) {
			System.out.println(names.get(i)+" - "+mobileNum.get(i));
		}
		try {			
			ExcelUtil.writeData(names, mobileNum, "carWashServices");
		}
		catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return new FreeListing(driver);
	}
}
