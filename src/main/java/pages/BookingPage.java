package pages;

import baseclasses.BasePage;
import exceptions.PropertyMisconfigureException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import static utils.Timeouts.SHORT_TIMEOUT;
import static utils.Utils.*;

/**
 * Created by taras on 7/19/18.
 */
public class BookingPage extends BasePage{

    @FindBy(css = "#button1")
    private WebElement findCar;

    @FindBy(css = "#map-location")
    private WebElement location;

    private String myAccountXpathDe = "//a[contains(text(), 'Mein Konto')]";
    @FindBy(xpath = "//a[contains(text(), 'Mein Konto')]")
    private WebElement myAccountDe;

    private String myAccountXpathEn = "//a[contains(text(), 'My account')]";
    @FindBy(xpath = "//a[contains(text(), 'My account')]")
    private WebElement myAccountEn;

    @FindBy(id = "datetimepicker")
    private WebElement dateFrom;

    @FindBy(id = "datetimepicker2")
    private WebElement dateTo;

    public BookingPage(WebDriver driver) {
        super(driver);
    }

    public BookingPage verifyBookingPageDisplayed() {
        switch (LANGUAGE){
            case DE : {
                super.verifyPageDisplayed("ahrzeug_finden_und_buchen", "Book Vehicle DE ");
                return this;
            }
            case EN : {
                super.verifyPageDisplayed("ind_and_book_a_vehicle", "Book Vehicle EN ");
                return this;
            }
        }
        throw new RuntimeException("Language is not properly set, please check config files!!!");
    }

    public BookingPage fillCheckOut(boolean bookNow) {
        if (bookNow) {
            clearInputField(dateFrom);
            dateFrom.sendKeys(getCurrentDateTime(2));
            dateFrom.sendKeys(Keys.RETURN);
        }
        return this;
    }

    public void fillCheckIn(boolean bookNow) {
        if (bookNow) {
            clearInputField(dateTo);
            dateTo.sendKeys(getCurrentDateTime(5));
            dateTo.sendKeys(Keys.RETURN);
        }
    }

    private String getDefaultEndDate() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        return jse.executeScript(
                "return (window.bookingConfig || window.wrappedJSObject.bookingConfig).endDate;").toString();
    }

    private String getDefaultStartDate() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        return jse.executeScript(
                "return (window.bookingConfig || window.wrappedJSObject.bookingConfig).startDate;").toString();
    }


    public BookingPage chooseLocation(String locationName) {
        waitElementPresent(By.id("map-location"), SHORT_TIMEOUT);

        if (locationName == null) {
            // location will be set to "Aschaffenburg"
            location.sendKeys("A");
            location.sendKeys("s");
            location.sendKeys("c");
            location.sendKeys("h");
        } else {
            location.sendKeys(locationName);
        }

        sleep(0.5);
        location.sendKeys(Keys.DOWN);
        sleep(0.2);
        location.sendKeys(Keys.RETURN);
        sleep(0.2);
        return this;
    }

    public BookingPage clickFindCar() {
        findCar.click();
        return this;
    }

    public BookingPage clickMyAccount() {
        switch (LANGUAGE) {
            case DE : return clickMyAccountDe();
            case EN : return clickMyAccountEn();
        }
        throw new PropertyMisconfigureException();
    }

    public BookingPage clickMyAccountDe() {
        waitElementClickable(myAccountXpathDe, SHORT_TIMEOUT);
        myAccountDe.click();
        return this;
    }

    public BookingPage clickMyAccountEn() {
        waitElementClickable(myAccountXpathEn, SHORT_TIMEOUT);
        myAccountEn.click();
        return this;
    }

}
