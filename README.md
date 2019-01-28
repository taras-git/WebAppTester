# UI Automation test framework (ATF)

ATF is designed to execute testcases for "Booking a car" functionality, the execution can be performed on local machine,
or on CI (Jenkins)


## Getting Started

Development process is done on Ubuntu 18.04, with Java 8


## Configuration

ATF project folder ```artifacts``` holds subfolders for running the project:
  - ```artifacts/binaries``` : holds browser binaries (in case particular browser version has to be tested);
  - ```artifacts/drivers``` : holds Webdriver binaries for Linux, Windows, and Mac OSs;
  - ```artifacts/properties``` : holds properties in JSON format;

All basic configuration needed for the project is stored in ```artifacts/properties/general_property.json``` file.

#### general_property.json
Some important keys:

###### Environment
  - "env" - the main property for the project, possible values are: prod_en, prod_de.
  Language is set with end part of the value ("..._en" - English, "..._de" - German)
  This value is also set in Jenkins jobs, and is used for remote test runs.

###### Browser 
  - "browser" - string value, choose among Chrome ("ch" or "chrome"), Firefox ("ff" or "firefox"), Edge ("edge"),
    Internet Explorer ("ie"), Safari ("safari");
  - "headless_mode" - boolean value (true/false), should browser open UI part or run without it;
  - "use_browser_binary" - boolean value (true/false), should the default browser version be picked (usually used by OS), or particular browser version (see next key);
  - "chrome_binary" and "firefox_binary" - path, where particular binaries of browser are stored;
  - "path to driver executables" - specifies the exact path where the Web-Driver executables are stored.

#### emails.json

Holds information about emails subjects (in English and German languages) that are received while booking.

#### time_pattern.json

Holds time pattern for creating file name during making screenshots, 
and time pattern for booking an auto.


#### users.json

Holds cashed login and password to access email (app2driver@yahoo.com) and login to app2drive.com . 

#### api_calls.json

Holds values for Api GET and POST tests.

## CI - Jenkins

Jenkins job is configured using command:
```aidl
mvn clean package -DsuiteXmlFile=${TestSuite} -DENV=${ENV_Ui_Test} -DBrowser=${Browser}
```

where:
 - "suiteXmlFile" - XML file for test suite
 - "ENV" - environment, where test suite runs (see general_properties.json)
 - "Browser" - specify browser name for the test suite


## WebDriver executables

In order to automatically run test and open browsers, the driver executables need to be present in the project.
Default path to it: "artifacts/drivers/"
There are FireFox and Chrome drivers for:
- Linux("artifacts/drivers/linux/geckodriver" and "artifacts/drivers/linux/chromedriver"); 
- MacOS("artifacts/drivers/macos/geckodriver" and "artifacts/drivers/macos/chromedriver");
- Windows("artifacts/drivers/windows/geckodriver.exe", "artifacts/drivers/windows/chromedriver.exe",
  "artifacts/drivers/windows/IEDriverServer.exe" and "artifacts/drivers/windows/MicrosoftWebDriver.exe").


## Chrome
* [Driver](https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver)
* [More info](http://chromedriver.chromium.org/home)


## Firefox
* [Driver](https://github.com/mozilla/geckodriver/releases)


## Internet Explorer
* [IE driver configuration](https://github.com/SeleniumHQ/selenium/wiki/InternetExplorerDriver#required-configuration)
Please read carefully "Required Configuration" section, and do all steps required.


## Edge
* [Driver](https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/)


## Safari
* [Info](https://developer.apple.com/documentation/webkit/testing_with_webdriver_in_safari)
* [Download Safari extension](https://www.seleniumhq.org/download/)
SafariDriver now requires manual installation of the extension prior to automation


## Page Object (PO) Pattern

This framework uses PO to separate page classes from tests classes.
There are 2 main base classes:
    BasePage.java
    BaseTestCase.java

Every page (HomePage, BookingPage...) is inherited from BasePage.
Every testcase is inherited from BaseTestCase.

All pages are instantiated in 
###### BaseTestCase.java:

```$xslt
    protected HomePage homePage;
    
    public void initPages(){
        homePage = new HomePage(driver);
        ...
    }
```

Doing this, writing the testcase becomes very straightforward
###### for example  - A2DNavigationTests.java:

```$xslt
    @Test
    public void navigateToBookingPage() {
        homePage.start()
                .clickBookVehicle();
        bookingPage.verifyBookingPageDisplayed();
    }
```

## Screenshots

The framework enables to take a screenshot of failed test.
The method is called in "@AfterMethod", and takes 3 arguments: result of the testcase (failed or not, path to screenshot folder, and driver instance).

```$xslt
@AfterMethod(alwaysRun = true)
    public void closeBrowser(ITestResult result) {
        long time = result.getEndMillis() - result.getStartMillis();
        LOG.info("=== FINISHED : <<<" + result.getName() + ">>>  RESULT: <<<"+ getResultDescription(result.getStatus()) + ">>> ===");
        LOG.info("=== TIME SPENT: " + time / 1000.0 + " seconds");

        takeScreenshot(result, SCREENSHOTS_FOLDER, driver);
        driver.quit();
    }
```

To change the screenshot rule (i.e. take screenshot of every test), please change the condition ( result.getStatus() ) of taking screenshots:

```$xslt
    public static void takeScreenshot(ITestResult result, String folderName, WebDriver d) {
        if(result.getStatus() == ITestResult.FAILURE ||
                result.getStatus() == ITestResult.SKIP) {
            try {
                takeScreenshotWithWebdriver(result, folderName, d);
                LOG.info("Screenshot taken");
            } catch (Exception e) {
                LOG.info("Exception while taking screenshot!");
                e.printStackTrace();
            }
        }
    }
```


## Video Recording

To enable video recording of failed test in the framework library "video-recorder-testng" is used (dependency in pom.xml file). If a test is annotated with "@Video", it will be recorded in case of fail.

```$xslt
    @Video
    @Test
    public void loggedUserCanChangeCountry(){
        ...
    }
```

For details please check : 
(https://github.com/SergeyPirogov/video-recorder-examples)


## REST API test

ATF framework allows executing REST call and verifying the responses(usually should be 200):

```$xslt
        get(url)
            .then()
            .contentType(ContentType.HTML)
            .and()
            .assertThat()
            .statusCode(200);
```

It is possible to test both GET and POST requests by adding relevant information to api_calls.json:

For GET test add an endpoint to "api_get_calls":

```$xslt
"api_get_calls": [
    "/storm/station/",
    "/storm/station/ids?id=",
    "... 
``` 

For POST test add JSON object with "endpoint" and POST body:

```$xslt
"api_post_calls":[
    {
      "endpoint": "/storm/price/",
      "ci": 1537275600000,
      "ciLocationCode": "TA1706048601DE",
      "co": 1537102800000,
      "coLocationCode": "TA1706048601DE"
    },
    {
    ...
    }
]
```


### Install these software and libraries

Install these software and libraries:
  - Java 8
  - Maven
  - IntelliJIDEA
  - Selenium libraries. Good tutorial to install Selenium:
    https://www.guru99.com/intellij-selenium-webdriver.html


## Built With

* [Java 8](https://java.com) - Main coding language
* [IntelliJIDEA](https://www.jetbrains.com/idea/) - Java IDE
* [Maven](https://maven.apache.org/) - Dependency Management
* [Selenium](https://www.seleniumhq.org/) - Web Application test tool
