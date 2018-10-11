package baseclasses;

import driver.A2Driver;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import pages.*;
import utils.EmailReader;
import utils.JsonReader;
import utils.Utils;

import java.lang.reflect.Method;
import java.util.Date;

import static utils.Utils.createFolder;
import static utils.Utils.takeScreenshot;


/**
 * Created by taras on 7/18/18.
 */
public class BaseTestCase {

    protected WebDriver driver;
    public final static String ENVIRONMENT = Utils.getUiTestEnvironment();
    public final static String LANGUAGE = Utils.getLanguage();
    private final String SCREENSHOTS_FOLDER = JsonReader.getString("failed_tests_screenshot_folder");
    private final String VIDEO_FOLDER = JsonReader.getString("failed_tests_video_folder");
    private static final Logger LOG = LoggerFactory.getLogger(BaseTestCase.class);

    private final String a2dEmail = JsonReader.getUserEmail("app2_driver");
    private final String a2dPassword = JsonReader.getUserPassword("app2_driver");
    private final String homePageProductionEn = JsonReader.getUrl("home_page_en_production");
    private final String homePageProductionDe = JsonReader.getUrl("home_page_de_production");
    private final String bookingPageInteraEn = JsonReader.getUrl("booking_page_intera_en");
    private final String bookingPageInteraDe = JsonReader.getUrl("booking_page_intera_de");
    private final String www3PageEn = JsonReader.getUrl("www3_en");
    private final String www3PageDe = JsonReader.getUrl("www3_de");

    protected HomePage homePage;
    protected HomePageIntera homePageIntera;
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
    protected AccountPage accountPage;
    protected ConfirmBookingPage confirmBookingPage;

    public void initPages(WebDriver driver){
        homePage = new HomePage(driver);
        homePageIntera = new HomePageIntera(driver);
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
        accountPage = new AccountPage(driver);
        confirmBookingPage = new ConfirmBookingPage(driver);
    }

    @BeforeSuite
    public void setup(){
        try {
            EmailReader.deleteAllMails();
        } catch (Exception e){
            e.printStackTrace();
        }

        createFolder(SCREENSHOTS_FOLDER);
        createFolder(VIDEO_FOLDER);
    }

    @BeforeMethod
    public void nameBefore(Method method) {
        LOG.info("+++++ STARTED : <<<" + method.getName() + ">>> +++++");
    }

    @BeforeMethod
    public void getDriver(ITestContext context) throws Exception {
        String browser;
        browser = JsonReader.getString("browser");
        driver = new BaseTestCase().createDriver(browser);
        initPages(driver);
    }

    private String getResultDescription(int statusCode){
        switch (statusCode){
            case -1: return "CREATED";
            case 1: return "SUCCESS";
            case 2: return "FAILURE";
            case 3: return "SKIP";
            case 4: return "SUCCESS_PERCENTAGE_FAILURE";
            case 16: return "STARTED";
        }
        return "N/A";
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser(ITestResult result) {
        long time = result.getEndMillis() - result.getStartMillis();
        LOG.info("+++ FINISHED : <" + result.getName() + " > +++");
        LOG.info("+++ WITH RESULT: <"+ getResultDescription(result.getStatus()) + "> +++");
        LOG.info("+++ TIME SPENT: <" + time / 1000.0 + "> seconds +++");

        takeScreenshot(result, SCREENSHOTS_FOLDER, driver);
        driver.quit();
    }

    private WebDriver createDriver(String browserName) {
        if (browserName.equalsIgnoreCase("firefox") ||
                browserName.equalsIgnoreCase("ff"))
            return new A2Driver().firefoxDriver(browserName);

        if (browserName.equalsIgnoreCase("chrome") ||
                browserName.equalsIgnoreCase("ch"))
            return new A2Driver().chromeDriver(browserName);

        throw new RuntimeException ("invalid browser name, please check out property json file");
    }

    protected void login() {
        switch(ENVIRONMENT){
            case "prod_en": {
                loginUserProd(a2dEmail, a2dPassword, homePageProductionEn);
                return;
            }

            case "prod_de": {
                loginUserProd(a2dEmail, a2dPassword, homePageProductionDe);
                return;
            }

            case "intera_en": {
                loginUserIntera(a2dEmail, a2dPassword, bookingPageInteraEn);
                return;
            }

            case "intera_de": {
                loginUserIntera(a2dEmail, a2dPassword, bookingPageInteraDe);
                return;
            }

            case "www3_en": {
                loginUserProd(a2dEmail, a2dPassword, www3PageEn);
                return;
            }

            case "www3_de": {
                loginUserProd(a2dEmail, a2dPassword, www3PageDe);
                return;
            }
        }
    }

    private void loginUserProd(String email, String password, String url) {
        homePage.start(url)
                .clickLogin();

        loginPage.verifyLoginPageDisplayed()
                .waitLoginFieldDisplayed()
                .login(email, password)
                .verifyUserLogged();
    }

    private void loginUserIntera(String email, String password, String url) {
        homePageIntera.start(url)
                .login(email, password);

        loginPage.verifyUserLogged();
    }

    protected void bookVehicle(String from, String to) {
        login();

        bookingPage.verifyBookingPageDisplayed()
                .fillCheckOut(from)
                .fillCheckIn(to);

        bookingPage.chooseLocation(null)
                .clickFindCar();

        chooseCarPage.waitChooseCarDisplayed()
                .chooseFirstCarDisplayed();

        Date withBookingDate = new Date();

        confirmBookingPage.bookCar()
                .verifyCarBooked();

        try {
            EmailReader.checkConfirmationEmailReceived(withBookingDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
