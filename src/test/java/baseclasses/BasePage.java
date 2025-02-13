package baseclasses;

import com.testautomationguru.ocular.Ocular;
import com.testautomationguru.ocular.comparator.OcularResult;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Utils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static utils.Timeouts.LONG_TIMEOUT;
import static utils.Timeouts.MIDDLE_TIMEOUT;

/**
 * Created by taras on 7/19/18.
 */
public class BasePage {
    protected WebDriver driver;
    public WebDriverWait wait;
    public final static String LANGUAGE = Utils.getLanguage();
    private static final Logger LOG = LoggerFactory.getLogger(BasePage.class);

    @FindBy(css = "div.mobile__btn")
    public WebElement burgerMenu;

    protected BasePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, MIDDLE_TIMEOUT);
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }

    public OcularResult compare() {
        return Ocular.snapshot().from(this)
                .sample().using(driver)
                .compare();
    }

    public void verifyPageDisplayed(String textInUrl, String pageName) {
        try{
            wait.until(ExpectedConditions.urlContains(textInUrl));
        } catch (TimeoutException e) {
            LOG.error(pageName + " page is not displayed");
            throw new RuntimeException("ERROR opening: " + pageName + " :: " + e.getStackTrace());
        }
    }

    public void verifyPageTitleContains(String textInTitle, String pageName) {
        try{
            wait.until(ExpectedConditions.urlContains(textInTitle));
        } catch (TimeoutException e) {
            LOG.error(pageName + " page has not title << " + textInTitle + " >> in it");
            throw new RuntimeException("ERROR opening: " + pageName + " :: " + e.getStackTrace());
        }
    }

    public void waitElementClickable(String xpath, int timeout){
        waitElementClickable(By.xpath(xpath), timeout);
    }

    public void waitElementClickable(By by, int timeout){
        LOG.info("Wait Element clickable > " + by + " Timeout: " + timeout);
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public void waitElementClickable(WebElement el, int timeout){
        String locator = el.toString();
        locator = locator.substring(locator.lastIndexOf("->") + 2);
        LOG.info("Wait Element clickable > " + locator + " Timeout: " + timeout);

        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.elementToBeClickable(el));
    }

    public void waitElementDisplayed(String xpath, int timeout){
        waitElementDisplayed(By.xpath(xpath), timeout);
    }

    public void waitElementDisplayed(By by, int timeout){
        LOG.info("Wait Element displayed > " + by + " Timeout: " + timeout);
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitElementPresent(By by, int timeout){
        LOG.info("Wait Element present > " + by + " Timeout: " + timeout);
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait
                .withMessage("Cannot find element: " + by.toString())
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public boolean isElementVisible(WebElement e, int timeout){
        LOG.info("Wait Element present > " + e.toString() + " Timeout: " + timeout);
        WebDriverWait wait = new WebDriverWait(driver, timeout);

        try {
            wait
                .withMessage("Cannot find element: " + e.toString())
                .until(ExpectedConditions.visibilityOf(e)
                );
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isElementPresent(By by) {
        boolean found = false;
        try {
            if (driver.findElements(by).size() > 0) {
                found = true;
            }
        } catch (NoSuchElementException e) {
                found = false;
        }
        return found;
    }

    public WebElement getElementWaitFound(By by, int timeout){
        WebElement element;
        element = (new WebDriverWait(driver, timeout))
                .until((ExpectedCondition<WebElement>) d -> d.findElement(by));

        return element;
    }


    public WebElement getElementFluentWait(By by, int timeout){
        LOG.info("Get Element FluentWait > " + by + " Timeout: " + timeout);
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
            .pollingEvery(100,  TimeUnit.MILLISECONDS)
            .withTimeout(timeout, TimeUnit.SECONDS)
            .ignoring(NoSuchElementException.class);

        Function<WebDriver, WebElement> waitFunction = new Function<WebDriver, WebElement>(){
            public WebElement apply(WebDriver d) {
                LOG.info("Checking for the element: " + by);
                WebElement element = d.findElement(by);

                if(element != null) {
                    LOG.info("Target element found");
                }

                return element;
            }
        };

        return wait.until(waitFunction);
    }


    public void scrollTo(WebElement element){
        LOG.info("Scrolling to Element > " + element);
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    public void scrollOnePageDown(){
        LOG.info("Scrolling down one page");
        WebElement root = driver.findElement(By.xpath("(//*)[1]"));
        root.sendKeys(Keys.PAGE_DOWN);
    }

    protected void moveToAndClick(WebElement moveTo, String clickOn){
        LOG.info("Move to Element > " + moveTo);
        LOG.info("Click on > " + clickOn);
        WebElement el = driver.findElement(By.xpath(clickOn));
        Actions builder = new Actions(driver);

        // Workaround - perform move to 2 times instead of 1
        // this way the click always executes
        builder.moveToElement(moveTo)
                .moveToElement(el)
                .moveToElement(moveTo)
                .moveToElement(el)
                .moveToElement(moveTo)
                .moveToElement(el)
                .click()
                .build()
                .perform();
    }

    protected void hoverOver(WebElement hoverOver){
        Actions builder = new Actions(driver);
        builder.moveToElement(hoverOver).perform();
    }

    public void clearInputField(WebElement element){
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.BACK_SPACE);
    }

    public List<WebElement> getElements(By by){
        waitElementClickable(by, LONG_TIMEOUT);
        List<WebElement> list = driver.findElements(by);
        return list;
    }

    public WebElement getElement(By by){
        return driver.findElement(by);
    }

    protected void clickOn(WebElement element){
        clickOn(element, true);
    }

    protected void clickOn(By by){
        clickOn(by, true);
    }

    protected void clickOn(WebElement element, boolean shouldWait){
        if(shouldWait) {
            waitElementClickable(element, LONG_TIMEOUT);
        }
        element.click();

        String locator = element.toString();
        locator = locator.substring(locator.lastIndexOf("->") + 2);
        LOG.info("Click on > " + locator);
    }

    protected void clickOn(By by, boolean shouldWait){
        if(shouldWait) {
            waitElementClickable(by, LONG_TIMEOUT);
        }
        WebElement element = driver.findElement(by);
        element.click();

        String locator = element.toString();
        locator = locator.substring(locator.lastIndexOf("->") + 2);
        LOG.info("Click on > " + locator);
    }

    protected void clickOn(By by, int timeout){
        waitElementClickable(by, timeout);

        WebElement element = driver.findElement(by);
        element.click();

        String locator = element.toString();
        locator = locator.substring(locator.lastIndexOf("->") + 2);
        LOG.info("Click on > " + locator);
    }

    public void sendTextTo(By by, String text){
        getElement(by).sendKeys(text);
        LOG.info("Send text:{} to:", text, by);
    }

    public void sendTextTo(WebElement el, String text){
        el.sendKeys(text);
        LOG.info("Send text:{} to:", text, el);
    }


    public void setSelect(WebElement element, String value){
        Select select = new Select(element);
        select.selectByValue(value);
        LOG.info("Set select: {} to: {}", element, value);
    }


}
