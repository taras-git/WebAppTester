package driver;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import utils.JsonReader;

import java.io.File;

import static utils.OsName.isMac;
import static utils.OsName.isUnix;
import static utils.OsName.isWindows;

/**
 * Created by taras on 7/18/18.
 */
public class A2Driver {

    private String chromeDriverPath;
    private String firefoxDriverPath;

    private final String chromeDriverPathLinux = JsonReader.getString("chrome_driver_linux");
    private final String chromeDriverPathMacos = JsonReader.getString("chrome_driver_macos");
    private final String chromeDriverPathWindows = JsonReader.getString("chrome_driver_windows");

    private final String firefoxDriverPathLinux = JsonReader.getString("firefox_driver_linux");
    private final String firefoxDriverPathMacos = JsonReader.getString("firefox_driver_macos");
    private final String firefoxDriverPathWindows = JsonReader.getString("firefox_driver_windows");

    private final String edgeDriverPathWindows = JsonReader.getString("edge_driver_windows");

    private boolean headlessMode = JsonReader.getBoolean("headless_mode");
    private boolean useBrowserBinary = JsonReader.getBoolean("use_browser_binary");

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

        if (browserName.equalsIgnoreCase("edge") ||
                browserName.equalsIgnoreCase("ie")) {
            return edgeDriverPathWindows;
        }

        return null;
    }

    public WebDriver chromeDriver(String browserName) {
        chromeDriverPath = getDriverPath(browserName);

        if (chromeDriverPath == null) {
            throw new RuntimeException
                    ("chromeDriverPath is not correctly set, please check the property file");
        }

        if (!new File(chromeDriverPath).exists()) {
            throw new RuntimeException
                    ("chromedriver executable file does not exist!");
        }

        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        System.setProperty("webdriver.chrome.logfile", "test-output/ChromeTestLog.txt");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-local-storage");
        options.addArguments("disable-infobars");
        options.addArguments("window-size=1920x1080");

        if (useBrowserBinary) {
            options.setBinary(JsonReader.getString("chrome_binary"));
        }
        if (headlessMode) {
            options.setHeadless(true);
            options.addArguments("window-size=1920x1080");
        }

        return new ChromeDriver(options);
    }

    public WebDriver firefoxDriver(String browserName) {
        firefoxDriverPath = getDriverPath(browserName);

        if (firefoxDriverPath == null) {
            throw new RuntimeException
                    ("firefoxDriverPath is not correctly set, please check the property file");
        }

        if (!new File(firefoxDriverPath).exists())
            throw new RuntimeException
                    ("firefox executable file does not exist!");

        System.setProperty("webdriver.gecko.driver", firefoxDriverPath);
        System.setProperty("webdriver.firefox.logfile", "test-output/FirefoxTestLog.txt");

        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("network.cookie.cookieBehavior", 0);
        // disable push notifications
        firefoxProfile.setPreference("dom.webnotifications.enabled", false);
        firefoxProfile.setPreference("geo.prompt.testing", false);
        firefoxProfile.setPreference("geo.prompt.testing.allow", false);
        firefoxProfile.setPreference("geo.enabled", false);
        FirefoxOptions options = new FirefoxOptions();

        options.setProfile(firefoxProfile);

        FirefoxBinary firefoxBinary = new FirefoxBinary();

        if (useBrowserBinary) {
            firefoxBinary = new FirefoxBinary(new File(JsonReader.getString("firefox_binary")));
        }

        if (headlessMode) {
            firefoxBinary.addCommandLineOptions("--headless");
        }

        options.setBinary(firefoxBinary);
        return new FirefoxDriver(options);
    }

    public WebDriver edgeDriver() {
        if (edgeDriverPathWindows == null) {
            throw new RuntimeException
                    ("edgeDriverPathWindows is not correctly set, please check the property file");
        }

        if (!new File(edgeDriverPathWindows).exists()) {
            throw new RuntimeException
                    ("edge executable file does not exist!");
        }

        System.setProperty("webdriver.edge.driver", edgeDriverPathWindows);
        return new EdgeDriver();
    }
}
