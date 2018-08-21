package pages;

import baseclasses.BasePage;
import exceptions.PropertyMisconfigureException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.Timeouts.SHORT_TIMEOUT;
import static utils.Utils.DE;
import static utils.Utils.EN;
import static utils.Utils.sleep;

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

    public BookingPage fillCheckOut() {
        //TODO
        return this;
    }

    public BookingPage fillCheckIn() {
        //TODO
        return this;
    }

    public BookingPage chooseLocation(String locationName) {
        waitElementPresent(By.id("map-location"), SHORT_TIMEOUT);
        location.sendKeys("A");
        location.sendKeys("s");
        location.sendKeys("c");

        sleep(0.2);
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
