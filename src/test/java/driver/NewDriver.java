package driver;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import utils.JsonReader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static utils.OsName.isMac;
import static utils.OsName.isUnix;
import static utils.OsName.isWindows;

/**
 * Created by taras on 7/18/18.
 */
public class NewDriver {

    private String chromeDriverPath;
    private String firefoxDriverPath;

    private final String chromeDriverPathLinux = JsonReader.getString("chrome_driver_linux");
    private final String chromeDriverPathMacos = JsonReader.getString("chrome_driver_macos");
    private final String chromeDriverPathWindows = JsonReader.getString("chrome_driver_windows");

    private final String firefoxDriverPathLinux = JsonReader.getString("firefox_driver_linux");
    private final String firefoxDriverPathMacos = JsonReader.getString("firefox_driver_macos");
    private final String firefoxDriverPathWindows = JsonReader.getString("firefox_driver_windows");

    private final String edgeDriverPathWindows = JsonReader.getString("edge_driver_windows");
    private final String ieDriverPathWindows = JsonReader.getString("ie_driver_windows");

    private final String safariDriverPathMacos = JsonReader.getString("safari_driver_macos");

    private boolean headlessMode = JsonReader.getBoolean("headless_mode");
    private boolean useBrowserBinary = JsonReader.getBoolean("use_browser_binary");

    private String getDriverPath(String browserName) {
        if (browserName.equalsIgnoreCase("firefox") ||
                browserName.equalsIgnoreCase("ff")) {
            if (isUnix()) return firefoxDriverPathLinux;
            if (isMac()) return firefoxDriverPathMacos;
            if (isWindows()) return firefoxDriverPathWindows;
        }

        if (browserName.equalsIgnoreCase("ch") ||
                browserName.contains("chrome")) {
            if (isUnix()) return chromeDriverPathLinux;
            if (isMac()) return chromeDriverPathMacos;
            if (isWindows()) return chromeDriverPathWindows;
        }

        if (browserName.equalsIgnoreCase("edge")) {
            return edgeDriverPathWindows;
        }

        if (browserName.equalsIgnoreCase("ie")) {
            return ieDriverPathWindows;
        }

        if (browserName.equalsIgnoreCase("safari")) {
            return safariDriverPathMacos;
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
        options.addArguments("--disable-infobars");

        if (useBrowserBinary) {
            options.setBinary(JsonReader.getString("chrome_binary"));
        }
        if (headlessMode) {
            options.setHeadless(true);
        }

        return new ChromeDriver(options);
    }

    public WebDriver chromeMobileDriver(String browserName, String deviceName) {
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
        options.addArguments("disable-infobars");

        Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", deviceName);

        options.setExperimentalOption("mobileEmulation", mobileEmulation);

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
        options.setLogLevel(FirefoxDriverLogLevel.TRACE);
        options.setProfile(firefoxProfile);

        FirefoxBinary firefoxBinary = new FirefoxBinary();

        if (useBrowserBinary) {
            firefoxBinary = new FirefoxBinary(new File(JsonReader.getString("firefox_binary")));
            options.setBinary(firefoxBinary);
        }

        if (headlessMode) {
            firefoxBinary.addCommandLineOptions("--headless");
        }

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

    public WebDriver ieDriver() {
        if (ieDriverPathWindows == null) {
            throw new RuntimeException
                    ("ieDriverPathWindows is not correctly set, please check the property file");
        }

        if (!new File(ieDriverPathWindows).exists()) {
            throw new RuntimeException
                    ("IE executable file does not exist!");
        }

        String absoluteIeDriverPath = getIeDriverAbsolutePath(ieDriverPathWindows);
        updateWindowsEnvVariablePath(absoluteIeDriverPath);

        System.setProperty("webdriver.ie.driver", absoluteIeDriverPath);

        return new InternetExplorerDriver();
    }

    private String getIeDriverAbsolutePath(String relativePath) {
        File file = new File(relativePath);
        return file.getAbsolutePath();
    }

    private void updateWindowsEnvVariablePath(String absolutePath) {
        ProcessBuilder pb = new ProcessBuilder("CMD.exe", "/C", "SET");
        pb.redirectErrorStream(true);
        Map<String,String> env = pb.environment();
        String newEnvPath = env.get("Path") + ";" + absolutePath;
        env.put("Path", newEnvPath);
    }


    public WebDriver safariDriver() {
        if (safariDriverPathMacos == null) {
            throw new RuntimeException
                    ("safariDriverPathMacos is not correctly set, please check the property file");
        }

        if (!new File(safariDriverPathMacos).exists()) {
            throw new RuntimeException
                    ("Safari executable file does not exist!");
        }

        return new SafariDriver();
    }

}
