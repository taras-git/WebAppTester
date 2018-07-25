package baseclasses;

import driver.A2Driver;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.*;
import utils.JsonReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Created by taras on 7/18/18.
 */
public class BaseTestCase {

    protected WebDriver driver;
    String browser;
    private final String SCREENSHOTS_FOLDER = JsonReader.getPropertyFileValue("failed_tests_screenshot_folder");

    protected HomePage homePage;
    protected BookingPage bookingPage;
    protected RatesPage ratesPage;
    protected FleetPage fleetPage;
    protected HowItWorksPage howItWorksPage;
    protected ContactPage contactPage;

    public void initPages(WebDriver driver){
        homePage = new HomePage(driver);
        bookingPage = new BookingPage(driver);
        ratesPage = new RatesPage(driver);
        fleetPage = new FleetPage(driver);
        howItWorksPage = new HowItWorksPage(driver);
        contactPage = new ContactPage(driver);
    }

    @BeforeSuite
    public void setup(){
        createScreenshotFolder(SCREENSHOTS_FOLDER);
    }

    @BeforeMethod
    public void getDriver(ITestContext context) throws Exception {
        // get the browser from XML testng file
        //browser = context.getCurrentXmlTest().getParameter("browser");

        // or
        // get the browser from JSON property file
        browser = JsonReader.getPropertyFileValue("browser");

        driver = new A2Driver(browser);
        driver.manage().window().maximize();
        initPages(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser(ITestResult result) {
        takeScreenshot(result);
        driver.quit();
    }

    private void takeScreenshot(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE ||
                result.getStatus() == ITestResult.SKIP) {
            try {
                takeScreenshotWithRobot(result, SCREENSHOTS_FOLDER);
                System.out.println("Screenshot taken");
            } catch (Exception e) {
                System.out.println("Exception while taking screenshot!");
                e.printStackTrace();
            }
        }
    }

    private void takeScreenshotWithRobot(ITestResult result, String screenshotFolder) throws AWTException, IOException {
        BufferedImage image = new Robot()
                .createScreenCapture(new Rectangle(Toolkit
                        .getDefaultToolkit()
                        .getScreenSize()));
        ImageIO.write(image, "png", new File(screenshotFolder
                + result.getName()
                + "_"
                + getTime()
                + "_scr.png"));
    }

    private String getTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        return now.format(formatter);
    }

    private void createScreenshotFolder(String screenshotFolder) {
        Path dirsPath = Paths.get(screenshotFolder);
        try {
            Files.createDirectories(dirsPath);
            System.out.println("Screenshot folder created");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
