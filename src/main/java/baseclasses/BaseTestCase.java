package baseclasses;

import driver.A2Driver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import pages.HomePage;
import utils.JsonReader;

/**
 * Created by taras on 7/18/18.
 */
public class BaseTestCase {

    protected WebDriver driver;
    protected String homePageEn = JsonReader.getPropertyFileValue("home_page_en");

    @BeforeTest
    public void getDriver() throws Exception {
        driver = new A2Driver(JsonReader.getPropertyFileValue("browser"));
        driver.manage().window().maximize();
    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }

    public HomePage openHomePage() {
        driver.get(homePageEn);

        return new HomePage(this.driver);
    }
}
