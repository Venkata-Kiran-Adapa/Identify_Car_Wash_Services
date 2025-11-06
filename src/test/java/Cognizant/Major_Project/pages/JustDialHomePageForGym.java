package Cognizant.Major_Project.pages;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
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
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(50));
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


    public void clickGymLink() {
    	Actions a =new Actions(driver);
    	a.scrollToElement(gymLink).perform();
        wait.until(ExpectedConditions.elementToBeClickable(gymLink)).click();
    }

    public void selectLocation(String location) {
    		wait.until(ExpectedConditions.elementToBeClickable(locationInput)).click();
    		locationInput.sendKeys(location);
    		wait.until(ExpectedConditions.elementToBeClickable(firstLocationDropdown)).click(); 
    }

    public void scrollAndExtractGymNames() throws InterruptedException {
    	Actions a=new Actions(driver);
    	WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
    	wait.until(ExpectedConditions.visibilityOfAllElements(noOfGyms));
    	System.out.println(noOfGyms.getText());
    	String string = noOfGyms.getText();
    	
    	int size = gyms.size();
    	int totalsize=Integer.parseInt(string.substring(0, 3));
    	while(size<totalsize) {
    		WebElement webElement = gyms.get(size-1);
    		a.scrollToElement(webElement).perform();
    		Thread.sleep(3000);
    		size=gyms.size();
    		System.out.println(size);
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
    	System.out.println("excel start");
    	ExcelUtil.writeData(writeNamesToXL, null,"GymNames");
    	System.out.println("excel end");
    }
    
    
    
    
}