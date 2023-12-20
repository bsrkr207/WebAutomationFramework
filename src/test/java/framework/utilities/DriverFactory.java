package framework.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Base64;
import java.util.Calendar;

public class DriverFactory {
    public static ThreadLocal<String> threadLocalDriverType = new ThreadLocal<>();
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<RemoteWebDriver> remoteWebDriverThreadLocal = new ThreadLocal<>();
    public static WebDriver getWebDriver() {
        return driverThreadLocal.get();
    }
    public static RemoteWebDriver getRemoteWebDriver() {
        return remoteWebDriverThreadLocal.get();
    }

    public static void setWebDriver(WebDriver driver, String browser) throws IOException {
        TestData data = TestData.TestDataLoader.loadTestData();

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }
        else if (browser.equalsIgnoreCase("safari")) {
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
        }
        driver.get(data.getURL());
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driverThreadLocal.set(driver);
        threadLocalDriverType.set("local");
    }

    public static void setRemoteWebDriver(RemoteWebDriver remoteWebDriver, String browser) throws IOException {
        TestData data = TestData.TestDataLoader.loadTestData();
        System.out.println("remote started");
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("experitest:accessKey", data.getAccessKey());
        caps.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

        if (browser.equalsIgnoreCase("chrome")) {
            caps.setCapability("experitest:osName", "Windows 10");
            caps.setCapability(CapabilityType.BROWSER_NAME, "chrome");
        }
        else if (browser.equalsIgnoreCase("firefox")) {
            caps.setCapability("experitest:osName", "Windows 10");
            caps.setCapability(CapabilityType.BROWSER_NAME, "firefox");
        }
        else if (browser.equalsIgnoreCase("edge")) {
            caps.setCapability("experitest:osName", "Windows 10");
            caps.setCapability(CapabilityType.BROWSER_NAME, "MicrosoftEdge");
        }
        else if (browser.equalsIgnoreCase("Safari")) {
            caps.setCapability(CapabilityType.BROWSER_NAME, "safari");
        }
        remoteWebDriver = new RemoteWebDriver(new URL(data.getCloudURL()), caps);
        remoteWebDriver.get(data.getURL());
        remoteWebDriver.manage().window().maximize();
        remoteWebDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        remoteWebDriverThreadLocal.set(remoteWebDriver);
        threadLocalDriverType.set("remote");
    }

    public static void quitWebDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }

    public static void quitRemoteWebDriver() {
        if (remoteWebDriverThreadLocal.get() != null) {
            remoteWebDriverThreadLocal.get().quit();
            remoteWebDriverThreadLocal.remove();
        }
    }
    public static String takeScreenShot() throws IOException {
        Calendar cal = Calendar.getInstance();
        long s = cal.getTimeInMillis();
        File screen = null;
        try {
            String driverType = threadLocalDriverType.get();
            if(driverType.equalsIgnoreCase("local")){
                screen = (File) ((TakesScreenshot) driverThreadLocal.get()).getScreenshotAs(OutputType.FILE);
            }else{
                screen = (File) ((TakesScreenshot) remoteWebDriverThreadLocal.get()).getScreenshotAs(OutputType.FILE);

            }
            FileUtils.copyFile(screen,
                    new File("ReportGenerator/" + ReportHelper.threadLocalReportFolder.get() + "/Screenshots/image" + s + ".png"));
        } catch (Exception e) {
            System.out.println(e);
        }

        return  "./Screenshots//image" + s + ".png";

    }

    // Method to capture screenshot as base64
    public static String captureScreenshotAsBase64() {
        String base64String = "";
        byte[] screenshot;
        try {
            String driverType = threadLocalDriverType.get();
            if(driverType.equalsIgnoreCase("local")){
                // Capture screenshot as byte array
                screenshot = ((TakesScreenshot) driverThreadLocal.get()).getScreenshotAs(OutputType.BYTES);
            }else{
                screenshot = ((TakesScreenshot) remoteWebDriverThreadLocal.get()).getScreenshotAs(OutputType.BYTES);
            }

            // Convert byte array to base64
            base64String = Base64.getEncoder().encodeToString(screenshot);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64String;
    }
}
