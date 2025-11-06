package Cognizant.Major_Project.pages;

import java.time.Duration;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JustDialHomePage {
	 
	WebDriver driver;
	WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
	
	@FindBy(linkText = "Maybe Later")
	WebElement closeLoginElement;
	
	@FindBy(xpath = "//span[@aria-label=\"Close Banner\"]")
	WebElement closePopUpElement;
	
	@FindBy(id="city-auto-sug")
	WebElement locationElement;
	
	@FindBy(id="react-autowhatever-city-auto-suggest--item-1")
	WebElement autoSuggestionElement;
	
	@FindBy(id="main-auto")
	WebElement searchElement;
	
	@FindBy(xpath = "//*[@class='location_text font14 fw400 color007']")
	WebElement autoDetectElement;
	
	public JustDialHomePage(WebDriver driver) {
		this.driver = driver;
	    PageFactory.initElements(driver, this); 
	}

	public void closeLogin() {
		wait.until(ExpectedConditions.visibilityOf(closeLoginElement));
		closeLoginElement.click();
	}
	
	public void closeOuterPopUp() {
		closePopUpElement.click();
	}
	
	public void setLocation(String location) throws InterruptedException {	    
		locationElement.click();
		locationElement.sendKeys(location);
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOf(autoSuggestionElement));
		autoSuggestionElement.click();
	}
	
	public CarWashServicesPage search() {
		searchElement.click();
		searchElement.sendKeys("Car Wash Services");
		searchElement.sendKeys(Keys.ENTER);
		return new CarWashServicesPage(driver);		
	}
	
	

}
