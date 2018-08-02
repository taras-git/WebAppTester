package baseclasses;

import driver.A2Driver;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.*;
import utils.JsonReader;

import static utils.Utils.createFolder;
import static utils.Utils.takeScreenshot;


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
    protected BlogPage blogPage;
    protected CompanyPage companyPage;
    protected RegisterNowPage registerNowPage;
    protected ApplyAsPartnerPage applyAsPartnerPage;
    protected CorporateMobilityPage corporateMobilityPage;
    protected ChooseCarPage chooseCarPage;
    protected LoginPage loginPage;
    protected StationsPage stationsPage;
    protected AirfieldsPage airfieldsPage;

    public void initPages(WebDriver driver){
        homePage = new HomePage(driver);
        bookingPage = new BookingPage(driver);
        ratesPage = new RatesPage(driver);
        fleetPage = new FleetPage(driver);
        howItWorksPage = new HowItWorksPage(driver);
        contactPage = new ContactPage(driver);
        blogPage = new BlogPage(driver);
        companyPage = new CompanyPage(driver);
        registerNowPage = new RegisterNowPage(driver);
        applyAsPartnerPage = new ApplyAsPartnerPage(driver);
        corporateMobilityPage = new CorporateMobilityPage(driver);
        chooseCarPage = new ChooseCarPage(driver);
        loginPage = new LoginPage(driver);
        stationsPage = new StationsPage(driver);
        airfieldsPage = new AirfieldsPage(driver);
    }

    @BeforeSuite
    public void setup(){
        createFolder(SCREENSHOTS_FOLDER);
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
        takeScreenshot(result, SCREENSHOTS_FOLDER);
        driver.quit();
    }

}
