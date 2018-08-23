package tests.web;

import baseclasses.BaseTestCase;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


/**
 * Created by taras on 7/17/18.
 */
@Listeners(VideoListener.class)
public class A2DBookingNowTest extends BaseTestCase {

    private static final Logger LOG = LoggerFactory.getLogger(A2DBookingNowTest.class);

    @Video
    @Test(groups="Booking_Now", priority=2)
    public void loggedUserCanBookVehicle_Now(){
        bookVehicle(true);
    }

    @Video
    @Test(groups="Booking_Now", dependsOnMethods = "loggedUserCanBookVehicle_Now", priority=2)
    public void loggedUserCanVerifyBooking_Now(){
        login();

        bookingPage.verifyBookingPageDisplayed()
                .clickMyAccount();

        accountPage.verifyMyAccountPageDisplayed()
                .clickBookings()
                .verifyMyBookingsDisplayed()
                .verifyCarIsLoaded();
    }
}