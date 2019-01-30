package baseclasses;

import com.testautomationguru.ocular.Ocular;
import driver.A2Driver;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.*;
import utils.EmailReader;
import utils.Env;
import utils.JsonReader;

import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static utils.Timeouts.SHORTEST_TIMEOUT;
import static utils.Utils.*;


/**
 * Created by taras on 7/18/18.
 */
public class BaseTestCase {

    private static final Logger LOG = LoggerFactory.getLogger(BaseTestCase.class);

    protected WebDriver driver;

    protected final static String LANGUAGE = getLanguage();

    private final static String ENVIRONMENT = getUiTestEnvironment();
    private final static String SCREENSHOT_FOLDER = JsonReader.getString("failed_tests_screenshot_folder");
    private final static String VIDEO_FOLDER = JsonReader.getString("failed_tests_video_folder");
    private final static String OCULAR_SNAPSHOTS = "test-output/ocular/snapshots/";
    private final static String OCULAR_RESULTS = "test-output/ocular/results/";

    private final static String APP2_DRIVER = "app2_driver";
    private final static String APP2_D_EMAIL = "app2d@ukr.net";
    private final static String APP2_D_PASSWORD = "112233Yahoo!";
    private final static String APP2_D_NAME = "App";

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
    protected AccountPage accountPage;
    protected ConfirmPaymentPage confirmPaymentPage;
    protected TelecashPage telecashPage;

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
        accountPage = new AccountPage(driver);
        confirmPaymentPage = new ConfirmPaymentPage(driver);
        telecashPage = new TelecashPage(driver);
    }

    @BeforeSuite
    public void setup(){
        try {
            EmailReader.deleteAllMails();
        } catch (Exception e){
            e.printStackTrace();
        }

        createFolder(SCREENSHOT_FOLDER);
        createFolder(VIDEO_FOLDER);
        createFolder(OCULAR_RESULTS, false);
        createFolder(OCULAR_SNAPSHOTS, false);
        createFolder(OCULAR_SNAPSHOTS + "/web", false);
        createFolder(OCULAR_SNAPSHOTS + "/iphone", false);
    }

    @BeforeMethod
    public void nameBefore(Method method) {
        LOG.info("+++++ STARTED : <<<" + method.getName() + ">>> +++++");
    }

    @BeforeMethod
    public void getDriver(ITestContext context) {
        driver = new BaseTestCase().createDriver(getBrowser(), getMobileDevice());
        initPages(driver);
    }

    public void setOcular(String device){
        Ocular.config()
                .resultPath(Paths.get(OCULAR_RESULTS))
                .snapshotPath(Paths.get(OCULAR_SNAPSHOTS, device))
                .globalSimilarity(95)
                .saveSnapshot(true);
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

        takeScreenshot(result, SCREENSHOT_FOLDER, driver);
        driver.quit();
    }

    private WebDriver createDriver(String browserName, String deviceName) {
        if(!deviceName.isEmpty()){
            if (browserName.equalsIgnoreCase("chrome"))
                return new A2Driver().chromeMobileDriver(browserName, deviceName);
        }

        if (browserName.equalsIgnoreCase("firefox") ||
                browserName.equalsIgnoreCase("ff"))
            return new A2Driver().firefoxDriver(browserName);

        if (browserName.equalsIgnoreCase("chrome") ||
                browserName.equalsIgnoreCase("ch"))
            return new A2Driver().chromeDriver(browserName);

        if (browserName.equalsIgnoreCase("edge"))
            return new A2Driver().edgeDriver();

        if (browserName.equalsIgnoreCase("ie"))
            return new A2Driver().ieDriver();

        if (browserName.equalsIgnoreCase("safari"))
            return new A2Driver().safariDriver();

        throw new RuntimeException ("invalid browser name, please check out property json file");
    }


    protected void login() {
        login(APP2_D_EMAIL, APP2_D_PASSWORD, getUrlFromProperty());
    }


    public static String getUrlFromProperty() {
        String url;
        try {
            url = Env.valueOf(ENVIRONMENT.toUpperCase()).getUrl();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Please check if environment and url are correctly set in Env.java");
        }
        return url;
   }

    public static String getUrlFromEnvEnum(String key) {
        String url;
        try {
            url = Env.getUrl(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Please check if KEY and Env.java are have same values");
        }
        return url;
    }

    private void login(String email, String password, String url) {

        homePage.start(url);
        homePage.clickLogin();

        loginPage.verifyLoginPageDisplayed()
                .waitLoginFieldDisplayed()
                .login(email, password)
                .verifyUserLogged();
    }

    protected void openHomePage() {
        homePage.start(getUrlFromProperty());
        Assert.assertTrue(homePage.compare().isEqualsImages());
    }


    protected void bookVehicle() {
        bookVehicle(TimeUnit.HOURS, 0, 0);
    }

    protected void bookVehicle(TimeUnit timeUnit, int fromOffset, int toOffset) {
        login();

        bookingPage.chooseLocation()
                .clickFindCar();

        chooseCarPage.waitChooseCarPageDisplayed();

        Date date = new Date();

        // modify booking time in URL in case: fromOffset and toOffset != 0
        if(!(0 == fromOffset && 0 == toOffset)){
            String currentUrl = driver.getCurrentUrl();
            String modifiedUrl = getModifiedUrl(timeUnit, fromOffset, toOffset, currentUrl);

            driver.navigate().to(modifiedUrl);
            LOG.info("URL is modified with time unit: {}, from: {}  to: {}", timeUnit.toString(), fromOffset, toOffset);

            sleep(SHORTEST_TIMEOUT);
        }

        chooseCarPage.chooseFirstCarDisplayed();

        confirmPaymentPage.isWithoutCents();

        boolean paymentSumWithoutCents = confirmPaymentPage.confirmCarBooked();
        LOG.info("Payment sum is without cents: {}", paymentSumWithoutCents);

        telecashPage.verifyTelecashPageDisplayed()
                .makePayment();

        boolean isBookingSuccess = false;
        if (paymentSumWithoutCents) {
            bookingPage.verifySuccessPaymentPageDisplayed();
            isBookingSuccess = true;
        } else {
            bookingPage.verifyFailedPaymentPageDisplayed();
        }

        //booking should be successful and email sent
        if (isBookingSuccess){
            try {
                EmailReader.checkConfirmationEmailReceived(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getModifiedUrl(TimeUnit timeUnit, int from, int to, String url) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter;
        String startDate = null;
        String endDate = null;

        switch (timeUnit){
            case HOURS:
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                startDate = now.plusHours(from).format(formatter);
                endDate = now.plusHours(to).format(formatter);
                break;

            case DAYS:
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:00");
                startDate = now.plusDays(from).format(formatter);
                endDate = now.plusDays(to).format(formatter);
                break;
        }


        int index = url.indexOf("co=");
        url = url.substring(0, index + 3)
                + startDate
                + url.substring(index + 19);
        index = url.indexOf("ci=");
        url = url.substring(0, index + 3)
                + endDate
                + url.substring(index + 19);

        return url;
    }

}
