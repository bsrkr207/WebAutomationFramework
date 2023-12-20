package framework.base;

import com.aventstack.extentreports.Status;
import framework.utilities.DriverFactory;
import framework.utilities.ReportHelper;
import framework.utilities.TestData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.lang.reflect.Method;

public class TestBase {
    protected WebDriver driver;
    protected RemoteWebDriver remoteWebDriver;
    String locality;

    @BeforeClass
    public void setUpClass(ITestContext context) throws IOException {
        // Initialize the driver and other setup (e.g., Extent Reports setup)
        String browser = context.getCurrentXmlTest().getParameter("browser");
        locality = context.getCurrentXmlTest().getParameter("locality");
        String testType = context.getCurrentXmlTest().getParameter("testType");
        String testClass =context.getCurrentXmlTest().getClasses().get(0).getName();
        TestData data = TestData.TestDataLoader.loadTestData();
        String cloudUrl = data.getCloudURL();

        System.out.println("Before Suite Started");

        if (testType.equalsIgnoreCase("sanity")) {
            ReportHelper.initializeExtentReport(data.getProjectName()+"_Web_Sanity_"+browser);
        }else if (testType.equalsIgnoreCase("regression")) {
            ReportHelper.initializeExtentReport(data.getProjectName()+"_Web_Regression_"+browser);
        }
        if(locality.equalsIgnoreCase("local")){
            DriverFactory.setWebDriver(driver,browser);
        }else{
            DriverFactory.setRemoteWebDriver(remoteWebDriver,browser);
        }

    }
    @BeforeMethod
    public void setUpTest(ITestResult result, Method method) {
        System.out.println("Before Method Started");
        ReportHelper.createTest(result.getTestClass().getRealClass().getSimpleName()+ " >>>>>>>> "+method.getName());
        System.out.println("Class: "+result.getTestClass().getRealClass().getSimpleName()+" >> Test Method "+method.getName()+ "  is Starting ");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        System.out.println("@ After Method Started");
        if (result.getStatus() == ITestResult.FAILURE){
            ReportHelper.logTestStepWithScreenshot_Base64(Status.FAIL, result.getThrowable().getMessage());
        } else if (result.getStatus() == ITestResult.SKIP) {
            ReportHelper.logTestStepWithScreenshot_Base64(Status.SKIP, "Test skipped " + result.getThrowable());
        }
    }

    @AfterClass
    public void afterClass() {
        ReportHelper.flushReport();
        if(locality.equalsIgnoreCase("local")){
            DriverFactory.quitWebDriver();
        }else{
            DriverFactory.quitRemoteWebDriver();
        }
    }

}
