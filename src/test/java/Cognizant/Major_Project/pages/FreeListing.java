package Cognizant.Major_Project.pages;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class FreeListing {

    WebDriver driver;
    WebDriverWait wait;
    public FreeListing(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5)); 
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[text() = 'Free Listing']")
    WebElement freeListingClick;

    @FindBy(xpath = "(//input[@class = 'entermobilenumber_input__eCrdc input fw500'])[1]")
    WebElement enterNumberField;

    @FindBy(xpath = "(//button[text() = 'Start Now '])[1]")
    WebElement submitButton;

    @FindBy(xpath = "//span[@class = 'undefined entermobilenumber_error__text__uPM09']")
    public WebElement errorMessage;

    public void fillFormWithInvalidPhone(String phone) {
        wait.until(ExpectedConditions.elementToBeClickable(freeListingClick)).click();
        wait.until(ExpectedConditions.visibilityOf(enterNumberField)).sendKeys(phone);
        submitButton.click();
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }
    
    public JustDialHomePageForGym captureErrorScreenshot(String number) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", errorMessage);
            Path screenshotDir = Paths.get("src/test/java/Cognizant/Major_Project/Screenshots/error"+number+".png");
            
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            
            Files.copy(srcFile.toPath(), screenshotDir, StandardCopyOption.REPLACE_EXISTING);
           } catch (Exception e) {
            System.out.println("Error capturing screenshot: " + e.getMessage());
            e.printStackTrace();
        }
        return new JustDialHomePageForGym(driver);
    }
}