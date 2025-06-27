# Selenium TestNG Page Object Model (POM) Maven Project

## Project Overview
This is a **Selenium TestNG** automation framework using the **Page Object Model (POM)** design pattern. The project is built using **Maven** for dependency management and provides a structured approach to automate web applications. The tests are executed with TestNG. Extent Reports are used for reporting, and test data is managed using a JSON files and config.properties`file.

## Tech Stack
- **Java** (JDK 20 or higher)
- **Selenium WebDriver**
- **TestNG**
- **Maven**
- **IntelliJ IDEA** (Integrated Development Environment for Java (Community version))
- **Extent Reports** (for test reporting)

## Project Structure
```
selenium-testng-pom/
├── ReportGenerator/
│   ├── Html reports/
├── src/
│   ├── framework/
│   │   ├── base/
│   │   │   ├── PageBase/          	# Reusable methods
│   │   │   ├── TestBase/          	# Utility functions (e.g., WebDriver setup)
│   │   ├── pageObjects/          
|   |   |   |── Pages/			   	# Page Object Model classes
│   │   ├── testcases/
│   │   │   ├── sanity/       	   	# TestNG test classes
│   │   ├──	├── ├── java class/
│   │   │   ├── regression/        	# TestNG test classes
│   │   ├──	├── ├── java class/
│   │   ├── utilities/
│   │   │   ├── CredentialData/    	# Handling of credential data
│   │   │   ├── CustomListener/    	# Managing Listeners
│   │   │   ├── DriverFactory/     	# Utility functions (e.g., WebDriver setup)
│   │   │   ├── ReportHelper/      	# Managing report related functions
│   │   │   ├── TestData/          	# Handling of test data
├── config.properties              	# contains configuration data
├── credential_prod.json           	# username and passwords related to prod env
├── credential_uat.json            	# username and passwords related to uat env
├── pom.xml                        	# Maven dependencies and plugins
├── testdata_prod.json             	# Test data related to prod env
├── testdata_uat.json              	# Test data related to uat env
├── testng_sanity.xml              	# xml to trigger sanity test cases
├── testng_regression.xml          	# xml to trigger regression test cases
├── testng_regression_parallel.xml  # xml to trigger regression test cases in parallel browser
├── README.md                   	# Project documentation
```

## How to Set Up and Run the Project

### **1. Clone the Repository**
```sh
git clone <repository-url>
```

### **2. Install Dependencies**
```sh
mvn clean install
```

### **4. Run Tests**
- Run specific profile:
  ```sh
   mvn -B clean test -Psanity
  ```
  ```sh
  mvn -B clean test -Pregression
  ```

- Run specific TestNG suite from IDE:
  ```sh
  right click on desired xml file (eg:testng_web_regression.xml) and choose Run from the contextual menu
  
### **4. Generate Reports**
- **Extent Reports** are generated under `ReportGenerator/`.
- Open `TM_Web_Sanity_ANY_YYYY-MM-DD-HH-mm-ss.html` inside `ReportGenerator/` to view results.

## Writing Test Cases
1. Create a new **Page Object Model (POM)** class in `pageOjects/`.
2. Define locators and methods for page interactions.
3. Create a **TestNG test class** in `testcases/` and call POM methods.
4. Use `@Test` annotation to define test methods.
5. Follow the camelCase style for naming the methods and classes 

Example Test Case:
```java
public class Epaper extends TestBase {

    @Test(priority = 1)
    public void TC6917_ableToLaunchEpaperAndLogout() throws Exception {
        TmPageObjects homePageObjects = new TmPageObjects();
        homePageObjects.ableToLaunchEpaper();
        homePageObjects.ePaperVerifyIfUserAbleToLogout();
    }
}
```
