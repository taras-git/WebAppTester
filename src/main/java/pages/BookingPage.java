package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

/**
 * Created by taras on 7/19/18.
 */
public class BookingPage extends BasePage{

    public BookingPage(WebDriver driver) {
        super(driver);
    }

    public void verifyBookingPageDisplayed() {
        super.verifyPageDisplayed("find_and_book_a_vehicle", "Book Vehicle");
    }
}
