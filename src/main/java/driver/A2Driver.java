package driver;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import utils.JsonReader;

import java.io.File;
import java.util.List;
import java.util.Set;

import static utils.OsName.isMac;
import static utils.OsName.isUnix;
import static utils.OsName.isWindows;

/**
 * Created by taras on 7/18/18.
 */
public class A2Driver implements WebDriver{

    private WebDriver driver;
    private final String browserName;

    private String chromeDriverPath;
    private String firefoxDriverPath;

    private final String chromeDriverPathLinux = JsonReader.getString("chrome_driver_linux");
    private final String firefoxDriverPathLinux =JsonReader.getString("firefox_driver_linux");
    private final String chromeDriverPathMacos = JsonReader.getString("chrome_driver_macos");
    private final String firefoxDriverPathMacos = JsonReader.getString("firefox_driver_macos");
    private final String chromeDriverPathWindows = JsonReader.getString("chrome_driver_windows");
    private final String firefoxDriverPathWindows = JsonReader.getString("firefox_driver_windows");

    private boolean headlessMode = JsonReader.getBoolean("headless_mode");

    public A2Driver(String browserName) {
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

    private String getDriverPath(String browserName) {
        if (browserName.equalsIgnoreCase("firefox") ||
                browserName.equalsIgnoreCase("ff")) {
            if (isUnix()) return firefoxDriverPathLinux;
            if (isMac()) return firefoxDriverPathMacos;
            if (isWindows()) return firefoxDriverPathWindows;
        }

        if (browserName.equalsIgnoreCase("chrome") ||
                browserName.equalsIgnoreCase("ch")) {
            if (isUnix()) return chromeDriverPathLinux;
            if (isMac()) return chromeDriverPathMacos;
            if (isWindows()) return chromeDriverPathWindows;
        }

        return null;
    }

    private WebDriver chromeDriver() {
        chromeDriverPath = getDriverPath(this.browserName);

        if (chromeDriverPath == null){
            throw new RuntimeException
                    ("chromeDriverPath is not correctly set, please check the property file");
        }

        if (!new File(chromeDriverPath).exists()) {
            throw new RuntimeException
                    ("chromedriver executable file does not exist!");
        }

        try {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximised");
            options.addArguments("--disable-local-storage");

            if (headlessMode) {
                options.setHeadless(true);
                options.addArguments("window-size=1920x1080");
            }

            return new ChromeDriver(options);
        } catch (Exception ex) {
            throw new RuntimeException
                    ("couldn't create chrome driver");
        }
    }

    private WebDriver firefoxDriver() {
        firefoxDriverPath = getDriverPath(this.browserName);

        if (firefoxDriverPath == null){
            throw new RuntimeException
                    ("firefoxDriverPath is not correctly set, please check the property file");
        }

        if (!new File(firefoxDriverPath).exists())
            throw new RuntimeException
                    ("firefox executable file does not exist!");

        try {
            System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
            FirefoxProfile firefoxProfile = new FirefoxProfile();
            firefoxProfile.setPreference("network.cookie.cookieBehavior", 0);
            // disable push notifications
            firefoxProfile.setPreference("dom.webnotifications.enabled", false);
            firefoxProfile.setPreference("geo.prompt.testing", false);
            firefoxProfile.setPreference("geo.prompt.testing.allow", false);
            firefoxProfile.setPreference("geo.enabled", false);
            FirefoxOptions options = new FirefoxOptions();
            options.setProfile(firefoxProfile);

            if (headlessMode) {
                FirefoxBinary firefoxBinary = new FirefoxBinary();
                firefoxBinary.addCommandLineOptions("--headless");
                options.setBinary(firefoxBinary);
            }

            return new FirefoxDriver(options);

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
