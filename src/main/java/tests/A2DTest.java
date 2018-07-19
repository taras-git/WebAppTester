package tests;

import baseclasses.BaseTestCase;
import org.testng.annotations.Test;

import pages.BookingPage;
import pages.HomePage;

/**
 * Created by taras on 7/17/18.
 */
public class A2DTest extends BaseTestCase {

    @Test
    public void navigateToBookingPage() {
        HomePage homePage = openHomePage();
        BookingPage bookingPage = homePage.openBooking();
        bookingPage.verifyBookingPageDisplayed();
    }
}
