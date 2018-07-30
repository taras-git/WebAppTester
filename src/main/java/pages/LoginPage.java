package pages;

import baseclasses.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by taras on 7/27/18.
 */
public class LoginPage extends BasePage{

    private final String loginWarningXpath = "//h1[contains(text(), 'You must be logged in to book a car')]";
    @FindBy(xpath = "//h1[contains(text(), 'You must be logged in to book a car')]")
    WebElement loginWarning;

    @FindBy(id = "loginID")
    WebElement emailInput;

    @FindBy(id = "loginPW")
    WebElement passwordInput;

    @FindBy(xpath = "(//button[@type='submit'])[2]")
    WebElement submitButton;

    private final String logoutXpath = "//a[contains(text(), 'Logout ')]";
    @FindBy(xpath = "//a[contains(text(), 'Logout ')]")
    WebElement logout;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage verifyLoginWarningDisplayed(){
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginWarningXpath)));
        return this;
    }

    public LoginPage login(String email, String password){
        scrollPageDown();
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        submitButton.click();
        return this;
    }

    public LoginPage verifyUserLogged(){
        waitElementDisplayed(logoutXpath, 20);
        return this;
    }


}
