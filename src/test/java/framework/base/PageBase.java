package framework.base;

import com.aventstack.extentreports.Status;
import framework.utilities.DriverFactory;
import framework.utilities.ReportHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PageBase {

    protected WebDriver driver;
    protected RemoteWebDriver remoteWebDriver;
    WebDriverWait wait;
    public PageBase() {
        if(DriverFactory.threadLocalDriverType.get().equalsIgnoreCase("local")){
            this.driver = DriverFactory.getWebDriver();
            wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        }else{
            this.remoteWebDriver = DriverFactory.getRemoteWebDriver();
            wait = new WebDriverWait(remoteWebDriver,Duration.ofSeconds(10));
        }
    }

    public void clickElement(WebElement element, String elementName) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        ReportHelper.logTestStep(Status.INFO, "clicked on element: "+ elementName);
        System.out.println("clicked on element: "+ elementName);
    }

    public void enterText(WebElement element, String text, String elementName) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.sendKeys(text);
        ReportHelper.logTestStep(Status.INFO, "Entered text into: "+ elementName);
        System.out.println("Entered text into: "+ elementName);
    }
    public boolean isPresent(WebElement element, String elementName) {
        Boolean result = false;
        try{
            wait.until(ExpectedConditions.visibilityOf(element));
            if(element.isDisplayed()){
                result=true;
            }
        }catch (Exception e){
            System.out.println(elementName+" not displayed");
        }
        return result;
    }

}
