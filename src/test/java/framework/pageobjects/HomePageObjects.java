package framework.pageobjects;

import com.aventstack.extentreports.Status;
import framework.base.PageBase;
import framework.utilities.DriverFactory;
import framework.utilities.ReportHelper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.IOException;

public class HomePageObjects extends PageBase {
    public HomePageObjects() {
        super();
        if(DriverFactory.threadLocalDriverType.get().equalsIgnoreCase("local")){
            PageFactory.initElements(driver, this);
        }else{
            PageFactory.initElements(remoteWebDriver, this);
        }
    }

    //region Web Elements
    @FindBy(xpath="//*[@title='Home']/img")
    private WebElement homePageLogo;
    @FindBy(xpath="(//*[@id='sph_login'])[3]")
    private WebElement login;
    @FindBy(xpath="//*[@id='block-querylysearchblock']")
    private WebElement search;
    @FindBy(xpath="//*[@id='queryly_query']")
    private WebElement search_SearchBT;
    @FindBy(xpath="//*[@id='advanced_closebutton']")
    private WebElement search_Close;
    @FindBy(xpath="(//*[@id='block-leftmenu']//li)[1]")
    private WebElement leftMenu;
    @FindBy(xpath="(//*[@id='block-sidemenu']//div//a)[2]")
    private WebElement leftMenu_Close;

    //endregion Web Elements

    //region Page methods
    public void verifyLogoDisplayedInHomePage() throws IOException {
        try {
            Assert.assertTrue(isPresent(homePageLogo, "Home Page Logo"), "Home page logo is not displayed");
            ReportHelper.logTestStepWithScreenshot_Base64(Status.PASS, "Home page logo is displayed");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            ReportHelper.logTestStepWithScreenshot_Base64(Status.FAIL, e.getMessage());
        }
    }

    public void  verifySearchFunction() throws Exception {
        try{
            clickElement(search,"Search");
            Assert.assertTrue(isPresent(search_SearchBT, "Search BT text box"), "Search BT text box not displayed");
            ReportHelper.logTestStepWithScreenshot_Base64(Status.PASS, "Search BT text box is displayed");
            clickElement(search_Close,"Search Close");

        }catch(AssertionError e) {
            System.out.println(e.getMessage());
            ReportHelper.logTestStepWithScreenshot_Base64(Status.FAIL, e.getMessage());
            if(isPresent(search_Close, "Search Close")){
                clickElement(search_Close,"Search Close");
            }

        }
    }

    public void  verifyLeftMenuFunctionality() throws Exception {
        try{
            clickElement(leftMenu,"Left Menu");
            Assert.assertTrue(isPresent(leftMenu_Close, "leftMenu_Close"), "LeftMenu Close button not displayed");
            ReportHelper.logTestStepWithScreenshot_Base64(Status.PASS, "LeftMenu Close button is displayed");
            clickElement(leftMenu_Close,"Left Menu Close");

        }catch(AssertionError e) {
            System.out.println(e.getMessage());
            ReportHelper.logTestStepWithScreenshot_Base64(Status.FAIL, e.getMessage());
            if(isPresent(leftMenu_Close, "LeftMenu_ Close")){
                clickElement(leftMenu_Close,"leftMenu_Close");
            }
        }
    }

    //endregion Page methods
}
