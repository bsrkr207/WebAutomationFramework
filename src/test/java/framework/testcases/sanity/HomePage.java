package framework.testcases.sanity;

import framework.base.TestBase;
import framework.pageobjects.HomePageObjects;
import org.testng.annotations.Test;

import java.io.IOException;

public class HomePage extends TestBase {

    @Test (priority = 1)
    public void verifyHomePageLogoDisplayed() throws IOException {
        HomePageObjects homePageObjects = new HomePageObjects();
        homePageObjects.verifyLogoDisplayedInHomePage();
    }

    @Test (priority = 2)
    public void verifySearchButtonFunctionality() throws Exception {
        HomePageObjects homePageObjects = new HomePageObjects();
        homePageObjects.verifySearchFunction();
    }

    @Test (priority = 3)
    public void verifyLeftMenuFunctionality() throws Exception {
        HomePageObjects homePageObjects = new HomePageObjects();
        homePageObjects.verifyLeftMenuFunctionality();
    }
}
