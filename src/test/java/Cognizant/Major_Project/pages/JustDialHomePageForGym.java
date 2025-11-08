package Cognizant.Major_Project.pages;
 
import java.io.IOException;

import java.time.Duration;

import java.util.ArrayList;

import java.util.List;
 
import org.openqa.selenium.By;

import org.openqa.selenium.JavascriptExecutor;

//import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;
 
import Cognizant.Major_Project.Utils.ExcelUtil;
 
public class JustDialHomePageForGym {
 
    WebDriver driver;

    WebDriverWait wait;
 
    public JustDialHomePageForGym(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        PageFactory.initElements(driver, this);

    }
 
    @FindBy(xpath = "//div[text() = 'Gym']")

    WebElement gymLink;
 
    @FindBy(id = "city-auto-sug") 
    WebElement locationInput;
    @FindBy(xpath = "(//div[@class = 'location_text font14 color111'])[1]")
    WebElement firstLocationDropdown;
    @FindBy(xpath="//main[@id='mainContent']/child::div")
    List<WebElement> gyms;
    @FindBy(xpath="//ul[@class='jsx-a487a86b47d169d9 breadcrumb_items']/child::li[4]")
    WebElement noOfGyms;
    List<String> writeNamesToXL=new ArrayList<>();
 
 
    public void clickGymLink() throws InterruptedException {
    	Actions a =new Actions(driver);
    	a.scrollToElement(gymLink).perform();
    	Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(gymLink)).click();
    }
 
    public void selectLocation(String location) {
    		wait.until(ExpectedConditions.elementToBeClickable(locationInput)).click();
    		locationInput.sendKeys(location);
    		wait.until(ExpectedConditions.elementToBeClickable(firstLocationDropdown)).click(); 
    }

    public void scrolling() throws InterruptedException {
    	JavascriptExecutor js = (JavascriptExecutor) driver;
		int length = 3000;
		for ( int i = 0 ; i <= 5 ; i++ ) {
			js.executeScript("window.scrollBy(0,"+length+")");
			Thread.sleep(4000);
			length = 1000;
		}
    }


    public void gymNames() throws InterruptedException, IOException {
    	System.out.println("inside gym names");
    	for(int i=0;i<gyms.size();i++) {
    		try {
    		WebElement title = gyms.get(i).findElement(By.tagName("h3"));
    		writeNamesToXL.add(title.getText());
    		System.out.println(title.getText());
    		}catch(Exception e) {
    		}
    	}
    	ExcelUtil.writeData(writeNamesToXL, null,"GymNames");

    }


}
 