package pages;

import baseclasses.BasePage;
import exceptions.PropertyMisconfigureException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

import static utils.Timeouts.SHORTER_TIMEOUT;
import static utils.Timeouts.SHORT_TIMEOUT;
import static utils.Utils.*;

/**
 * Created by taras on 7/19/18.
 */
public class BookingPage extends BasePage{

    @FindBy(css = "button.btn-submit > span.text")
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

    @FindBy(css = ".desktop#same-station-field")
    private WebElement chooseStation;

    @FindBy(css = ".mobile#same-station-field")
    private WebElement chooseStationMobile;

    private final By choosePickupStation = By.cssSelector("div.desktop > div.search__block > input");
    private final By choosePickupStationMobile = By.cssSelector("div.mobile > div.search__block > input");

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

    public void verifyFailedPaymentPageDisplayed(){
        verifyPageDisplayed("failure=true", "Payment Failed Page");
    }

    public void verifySuccessPaymentPageDisplayed(){
        verifyPageDisplayed("successful-reservation", "Payment Success Page");
    }

    public BookingPage fillCheckOutCheckIn(String time, WebElement el) {
        if (null != time) {
            clearInputField(el);
            el.sendKeys(time);
            el.sendKeys(Keys.RETURN);
        }
        return this;
    }

    public BookingPage fillCheckOut(String from) {
        return fillCheckOutCheckIn(from, dateFrom);
    }

    public BookingPage fillCheckIn(String to) {
        return fillCheckOutCheckIn(to, dateTo);
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


    public BookingPage chooseLocation() {
        String aschaffenburg = "Aschaffenburg";
        WebElement station;

        switch (LANGUAGE) {
            case DE :
            case EN : clickOn(chooseStation);
                waitElementDisplayed(choosePickupStation, SHORT_TIMEOUT);
                sendTextTo(choosePickupStation, aschaffenburg);

                station = getElement(By.xpath("//div[@class='station__list desktop d-none d-md-block']" +
                        "//li[@data-city='" +
                        aschaffenburg +
                        "']"));
                clickOn(station);

                break;

            case DE_MOBILE:
            case EN_MOBILE: clickOn(chooseStationMobile);
                waitElementDisplayed(choosePickupStationMobile, SHORT_TIMEOUT);
                sendTextTo(choosePickupStationMobile, aschaffenburg);

                station = getElement(By.xpath("//div[@class='station__list mobile d-md-none']" +
                        "//li[@data-city='" +
                        aschaffenburg +
                        "']/p[contains(text(), '" +
                        aschaffenburg +
                        "')]"));
                clickOn(station);

                clickOn(getElement(By.cssSelector("span.apply__station")));
                break;
        }



        return this;
    }

    public BookingPage clickFindCar() {
        switch (LANGUAGE) {
            case DE_MOBILE:
            case EN_MOBILE:
                sleep(0.5);
        }

        clickOn(findCar);
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
        clickOn(myAccountDe);
        return this;
    }

    public BookingPage clickMyAccountEn() {
        clickOn(myAccountEn);
        return this;
    }

}
