package tests.web;

import baseclasses.BaseTestCase;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static utils.Utils.getCurrentDateTime;


/**
 * Created by taras on 7/17/18.
 */
@Listeners(VideoListener.class)
public class W3_BookingLoadedTest extends BaseTestCase {

    private static final Logger LOG = LoggerFactory.getLogger(W3_BookingLoadedTest.class);

    @Video
    @Test(groups="BookingLoaded", priority=1)
    public void loggedUserCanBookVehicle_Loaded(){
        String from = getCurrentDateTime(2);
        String to = getCurrentDateTime(6);
        bookVehicle(TimeUnit.HOURS, 2, 6);
    }

    @Video
    @Test(groups="BookingLoaded", dependsOnMethods = "loggedUserCanBookVehicle_Loaded", priority=2)
    public void loggedUserCanCancelBooking_Loaded(){
        login();

        bookingPage.verifyBookingPageDisplayed()
                .clickMyAccount();

        accountPage.verifyMyAccountPageDisplayed()
                .clickBookings()
                .verifyMyBookingsDisplayed()
                .verifyCarIsLoaded()
                .clickLoadedDetails()
                .cancelBooking();
    }

    @Video
    @Test(dependsOnGroups = "BookingLoaded", priority=3)
    public void verifyLoadedBookingCanceled(){
        login();

        bookingPage.verifyBookingPageDisplayed()
                .clickMyAccount();

        accountPage.verifyMyAccountPageDisplayed()
                .clickBookings()
                .verifyMyBookingsDisplayed()
                .verifyNoCarsLoaded();
    }
}