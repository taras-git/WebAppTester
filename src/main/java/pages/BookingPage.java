package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by taras on 7/19/18.
 */
public class BookingPage extends BasePage{

    public BookingPage(WebDriver driver) {
        super(driver);
    }

    public void verifyBookingPageDisplayed() {
        if (!wait.until(ExpectedConditions.urlContains("find_and_book_a_vehicle")))
            throw new RuntimeException("booking page is not displayed");
    }
}
