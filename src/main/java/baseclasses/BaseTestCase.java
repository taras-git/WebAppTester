package baseclasses;

import driver.A2Driver;
import org.openqa.selenium.WebDriver;
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

    @BeforeTest
    public void getDriver() throws Exception {
        driver = new A2Driver(JsonReader.getPropertyFileValue("browser"));
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
