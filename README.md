# UI Automation test framework (ATF)

ATF is designed to execute testcases for "Booking a car" functionality, the execution can be performed on local machine,
or on CI (Jenkins)


## Getting Started

Development process is done on Ubuntu 18.04, with Java 8


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


