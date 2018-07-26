package pages;

import baseclasses.BasePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.Utils.sleep;

/**
 * Created by taras on 7/19/18.
 */
public class BookingPage extends BasePage{

    @FindBy(css = "#button1")
    WebElement findCar;

    @FindBy(css = "#map-location")
    WebElement location;

    public BookingPage(WebDriver driver) {
        super(driver);
    }

    public BookingPage verifyBookingPageDisplayed() {
        super.verifyPageDisplayed("find_and_book_a_vehicle", "Book Vehicle");
        return this;
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
        location.sendKeys(locationName);
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
}
