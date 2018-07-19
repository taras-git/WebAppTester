package driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.JsonReader;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * Created by taras on 7/18/18.
 */
public class A2Driver implements WebDriver {

    private WebDriver driver;
    private final String browserName;

    private final String chromeDriverPath;
    private final String firefoxDriverPath;

    public A2Driver(String browserName) {
        chromeDriverPath = JsonReader.getPropertyFileValue("chrome_driver");
        firefoxDriverPath = JsonReader.getPropertyFileValue("firefox_driver");
        this.browserName = browserName;
        this.driver = createDriver(browserName);
    }

    private WebDriver createDriver(String browserName) {
        if (browserName.equalsIgnoreCase("firefox") ||
                browserName.equalsIgnoreCase("ff"))
        return firefoxDriver();

        if (browserName.equalsIgnoreCase("chrome") ||
                browserName.equalsIgnoreCase("ch"))
        return chromeDriver();

        throw new RuntimeException ("invalid browser name, please check out property json file");
    }

    private WebDriver chromeDriver() {
        if (!new File(chromeDriverPath).exists())
            throw new RuntimeException
                    ("chromedriver executable file does not exist!");

        try {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximised");
            return new ChromeDriver();
        } catch (Exception ex) {
            throw new RuntimeException
                    ("couldn't create chrome driver");
        }
    }

    private WebDriver firefoxDriver() {
        if (!new File(firefoxDriverPath).exists())
            throw new RuntimeException
                    ("firefox executable file does not exist!");

        try {
            System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
            return new FirefoxDriver();
        } catch (Exception ex) {
            throw new RuntimeException
                    ("could not create firefox driver");
        }
    }

    public WebDriver driver() {
        return this.driver;
    }

    @Override
    public String toString() {
        return this.browserName;
    }

    @Override
    public void close() {
        driver().close();
    }

    @Override
    public WebElement findElement(By locator) {
        return driver().findElement(locator);
    }

    @Override
    public List findElements(By arg0) {
        return driver().findElements(arg0);
    }

    @Override
    public void get(String arg0) {
        driver().get(arg0);
    }

    @Override
    public String getCurrentUrl() {
        return driver().getCurrentUrl();
    }

    @Override

    public String getPageSource() {
        return driver().getPageSource();
    }

    @Override
    public String getTitle() {
        return driver().getTitle();
    }

    @Override
    public String getWindowHandle() {
        return driver().getWindowHandle();
    }

    @Override
    public Set getWindowHandles() {
        return driver().getWindowHandles();
    }

    @Override
    public Options manage() {
        return driver().manage();
    }

    @Override
    public Navigation navigate() {
        return driver().navigate();
    }

    @Override
    public void quit() {
        driver().quit();
    }

    @Override
    public TargetLocator switchTo() {
        return driver().switchTo();
    }
}
