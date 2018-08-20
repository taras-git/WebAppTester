package pages;

import baseclasses.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static utils.Utils.DE;
import static utils.Utils.EN;

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

    @FindBy(xpath = "//button[contains(text(), 'submit ')]")
    WebElement submitButton;

    @FindBy(xpath = "//button[contains(text(), 'Login ')]")
    WebElement loginButton;

    private final String logoutXpath = "//a[contains(text(), 'Logout ')]";
    @FindBy(xpath = "//a[contains(text(), 'Logout ')]")
    WebElement logout;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage verifyLoginPageDisplayed() {
        super.verifyPageDisplayed("login", "Login");
        return this;
    }

    public LoginPage verifyLoginWarningDisplayed(){
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginWarningXpath)));
        return this;
    }

    public LoginPage login(String email, String password){
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);

        switch (LANGUAGE){
            case DE : {
                loginButton.click();
                break;
            }
            case EN : {
                submitButton.click();
                break;
            }
        }

        return this;
    }

    public LoginPage verifyUserLogged(){
        waitElementDisplayed(logoutXpath, 20);
        return this;
    }

    public LoginPage waitLoginFieldDisplayed() {
        waitElementPresent(By.cssSelector("#loginID"), 5);
        return this;
    }
}
