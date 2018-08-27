package pages;

import baseclasses.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static utils.Timeouts.*;
import static utils.Utils.DE;
import static utils.Utils.EN;

/**
 * Created by taras on 7/27/18.
 */
public class LoginPage extends BasePage{

    @FindBy(xpath = "//nav//*[contains(text(), 'Book a vehicle')]")
    private WebElement bookVehicleEn;

    @FindBy(xpath = "//nav//*[contains(text(), 'Fahrzeug buchen')]")
    private WebElement bookVehicleDe;

    private final String loginWarningXpath = "//h1[contains(text(), 'You must be logged in to book a car')]";
    @FindBy(xpath = "//h1[contains(text(), 'You must be logged in to book a car')]")
    private WebElement loginWarning;

    @FindBy(id = "loginID")
    private WebElement emailInput;

    @FindBy(id = "loginPW")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[contains(text(), 'submit ')]")
    private WebElement submitButton;

    @FindBy(xpath = "//button[contains(text(), 'Login ')]")
    private WebElement loginButton;

    private final String logoutXpath = "//a[contains(text(), 'Logout ')]";
    @FindBy(xpath = "//a[contains(text(), 'Logout ')]")
    private WebElement logout;

    private final By closeActiveBookingAlertCss = By.cssSelector("button#closemodal");
    @FindBy(css = "button#closemodal")
    private WebElement closeActiveBookingAlert;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage verifyLoginPageDisplayed() {
        super.verifyPageDisplayed("login", "Login");
        return this;
    }

    public LoginPage verifyLoginWarningDisplayed(){
        WebDriverWait wait = new WebDriverWait(driver, MIDDLE_TIMEOUT);
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
        waitElementDisplayed(logoutXpath, LONG_TIMEOUT);
        WebElement closeAlert = null;
        try {
            closeAlert = getElementFluentWait(closeActiveBookingAlertCss, SHORTEST_TIMEOUT);
            closeAlert.click();

            switch (LANGUAGE) {
                case DE : bookVehicleDe.click();
                    break;
                case EN : bookVehicleEn.click();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public LoginPage waitLoginFieldDisplayed() {
        waitElementPresent(By.cssSelector("#loginID"), SHORTER_TIMEOUT);
        return this;
    }
}
