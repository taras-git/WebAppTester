package baseclasses;

import driver.A2Driver;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import pages.BookingPage;
import pages.HomePage;
import utils.JsonReader;

/**
 * Created by taras on 7/18/18.
 */
public class BaseTestCase {

    protected WebDriver driver;

    protected HomePage homePage;
    protected BookingPage bookingPage;
    String browser;

    @BeforeTest
    public void getDriver(ITestContext context) throws Exception {
        // get the browser from XML testng file
        browser = context.getCurrentXmlTest().getParameter("browser");

        // or
        // get the browser from JSON property file
        //browser = JsonReader.getPropertyFileValue("browser");

        driver = new A2Driver(browser);
        driver.manage().window().maximize();
        initPages();
    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }

    public void initPages(){
        homePage = new HomePage(driver);
        bookingPage = new BookingPage(driver);
    }
}
