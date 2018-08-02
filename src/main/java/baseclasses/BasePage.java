package baseclasses;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by taras on 7/19/18.
 */
public class BasePage {

    protected WebDriver driver;
    public WebDriverWait wait;

    protected BasePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, 20);
    }

    public void verifyPageDisplayed(String textInUrl, String pageName) {
        if (!wait.until(ExpectedConditions.urlContains(textInUrl)))
            throw new RuntimeException(pageName + " page is not displayed");
    }

    public void verifyPageTitleContains(String textInTitle, String pageName) {
        if (!wait.until(ExpectedConditions.titleContains(textInTitle)))
            throw new RuntimeException(pageName + " page has not title << " + textInTitle + " >>in it");
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

    public void scrollPageDown(){
        WebElement root = driver.findElement(By.xpath("(//*)[1]"));
        root.sendKeys(Keys.PAGE_DOWN);
    }
}
