package pages;

import baseclasses.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static utils.Timeouts.*;
import static utils.Utils.*;

/**
 * Created by taras on 7/27/18.
 */
public class LoginPage extends BasePage{

    private static final Logger LOG = LoggerFactory.getLogger(LoginPage.class);

    @FindBy(xpath = "//nav//*[contains(text(), 'Book a vehicle')]")
    private WebElement bookVehicleEn;

    @FindBy(xpath = "//nav//*[contains(text(), 'Fahrzeug buchen')]")
    private WebElement bookVehicleDe;

    private final String loginWarningXpath = "//h1[contains(text(), 'You must be logged in to book a car')]";
    @FindBy(xpath = "//h1[contains(text(), 'You must be logged in to book a car')]")
    private WebElement loginWarning;

    private By loginForm = By.xpath("//h1[contains(text(), 'Login')]");

    @FindBy(xpath = "(//input[@type='email'])[1]")
    private WebElement emailInput;

    @FindBy(xpath = "(//input[@type='password'])[1]")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[contains(text(), 'submit ')]")
    private WebElement submitButton;

    @FindBy(xpath = "//button[contains(text(), 'Login')]")
    private WebElement loginButton;

    private final String logoutXpath = "//a[contains(text(), 'Logout ')]";
    @FindBy(xpath = "//a[contains(text(), 'Logout ')]")
    private WebElement logout;

    private final By closeActiveBookingAlertCss = By.cssSelector("button#closemodal");
    @FindBy(css = "button#closemodal")
    private WebElement closeActiveBookingAlert;

    private final String greetingXpath = "(//span[contains(text(), 'Hallo, ')])[1]";

    private final String greetingXpathMobile = "(//span[contains(text(), 'Hallo, ')])[2]";

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
            case DE :
            case DE_MOBILE:{
                clickOn(loginButton);
                break;
            }
            case EN : {
                clickOn(submitButton);
                break;
            }
        }
        return this;
    }

    public LoginPage verifyUserLogged(){
        switch (LANGUAGE){
            case EN_MOBILE:
            case DE_MOBILE:{
                clickOn(burgerMenu);
                waitElementDisplayed(greetingXpathMobile, LONG_TIMEOUT);
                clickOn(burgerMenu);
                break;
            }
            case DE :
            case EN : {
                waitElementDisplayed(greetingXpath, LONG_TIMEOUT);
                break;
            }
        }

//        waitElementDisplayed(greetingXpath, LONG_TIMEOUT);
        WebElement closeAlert = null;

        try {
            closeAlert = getElementFluentWait(closeActiveBookingAlertCss, SHORTEST_TIMEOUT);
            clickOn(closeAlert, false);
        } catch (Exception e) {
            LOG.info("FAILED: getElementFluentWait for Element " + closeActiveBookingAlertCss );
        }

        return this;
    }

    public LoginPage waitLoginFieldDisplayed() {
        waitElementPresent(loginForm, SHORTER_TIMEOUT);
        return this;
    }
}
