package baseclasses;

import driver.A2Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import utils.JsonReader;

/**
 * Created by taras on 7/18/18.
 */
public class BaseTestCase {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String homePageEn = JsonReader.getPropertyFileValue("home_page_en");

    @BeforeTest
    public void openBrowser() throws Exception {
        driver = new A2Driver(JsonReader.getPropertyFileValue("browser"));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }
}
