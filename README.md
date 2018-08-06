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

All configuration needed for the project is stored in ```artifacts/properties/general_property.json``` file.
Some important keys:
  - "browser" - string value, choose among Chrome ("ch" or "chrome") or Firefox ("ff" or "firefox");
  - "headless_mode" - boolean value (true/false), should browser open UI part or run without it;
  - "use_browser_binary" - boolean value (true/false), should the default browser version be picked (usually used by OS), or particular browser version (see next key);
  - "chrome_binary" and "firefox_binary" - path, where particular binaries of browser are stored;



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


## Page Object (PO) Pattern

This framework uses PO to separate page classes from tests classes.
There are 2 main base classes:
    BaserPage.java
    BaseTestCase.java

Every page (HomePage, BookingPage...) is inhereted from BasePage.
Every testcase is inhereted from BaseTestCase.

All pages are instantiated in BaseTestCase.java:

```
    public void initPages(){
        homePage = new HomePage(driver);
        bookingPage = new BookingPage(driver);
        ...
    }
```

Doing this, writing the testcase becomes very straightforward:

```
    @Test
    public void navigateToBookingPage() {
        homePage.start()
                .clickBookVehicle();
        bookingPage.verifyBookingPageDisplayed();
    }
```


## REST API test

ATF framework allows executing REST call and verifying the responses:

```
        get(url)
            .then()
            .contentType(ContentType.HTML)
            .and()
            .assertThat()
            .statusCode(200);
```