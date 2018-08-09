package baseclasses;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by taras on 7/19/18.
 */
public class BasePage {

    protected WebDriver driver;
    public WebDriverWait wait;

    private static final Logger LOG = LoggerFactory.getLogger(BasePage.class);

    protected BasePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, 10);
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
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public void waitElementDisplayed(String xpath, int timeout){
        waitElementDisplayed(By.xpath(xpath), timeout);
    }

    public void waitElementDisplayed(By by, int timeout){
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitElementPresent(By by, int timeout){
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait
                .withMessage("Cannot find element: " + by.toString())
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitElementFound(By by, int timeout){
        WebElement element;
        element = (new WebDriverWait(driver, timeout))
                .until((ExpectedCondition<WebElement>) d -> d.findElement(by));

        return element;
    }


    public void scrollToElement(WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    public void scrollOnePageDown(){
        WebElement root = driver.findElement(By.xpath("(//*)[1]"));
        root.sendKeys(Keys.PAGE_DOWN);
    }
}
