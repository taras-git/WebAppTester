package baseclasses;

import com.testautomationguru.ocular.Ocular;
import driver.NewDriver;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.*;
import utils.EmailReader;
import utils.Env;
import utils.JsonReader;

import java.lang.reflect.Method;
import java.nio.file.Paths;

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

    protected HomePage homePage;

    public void initPages(WebDriver driver){
        homePage = new HomePage(driver);
    }

    @BeforeSuite
    public void setup(){
        createFolder(SCREENSHOT_FOLDER);
        createFolder(VIDEO_FOLDER);
        createFolder(OCULAR_RESULTS, false);
        createFolder(OCULAR_SNAPSHOTS, false);
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
                return new NewDriver().chromeMobileDriver(browserName, deviceName);
        }

        if (browserName.equalsIgnoreCase("firefox") ||
                browserName.equalsIgnoreCase("ff"))
            return new NewDriver().firefoxDriver(browserName);

        if (browserName.equalsIgnoreCase("chrome") ||
                browserName.equalsIgnoreCase("ch"))
            return new NewDriver().chromeDriver(browserName);

        if (browserName.equalsIgnoreCase("edge"))
            return new NewDriver().edgeDriver();

        if (browserName.equalsIgnoreCase("ie"))
            return new NewDriver().ieDriver();

        if (browserName.equalsIgnoreCase("safari"))
            return new NewDriver().safariDriver();

        throw new RuntimeException ("invalid browser name, please check out property json file");
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
}
