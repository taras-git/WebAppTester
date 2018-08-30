package tests.web;

import baseclasses.BaseTestCase;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static utils.Utils.DE;
import static utils.Utils.EN;


/**
 * Created by taras on 7/17/18.
 */
@Listeners(VideoListener.class)
public class A2DBookingTest extends BaseTestCase {

    private static final Logger LOG = LoggerFactory.getLogger(A2DBookingTest.class);

    @Video
    @Test(groups="Booking", priority=1)
    public void loggedUserCanBookVehicle(){
        bookVehicle(null, null);
    }


    @Video
    @Test(groups="Booking", dependsOnMethods = "loggedUserCanBookVehicle", priority=2)
    public void loggedUserCanCancelBooking(){
        login();

        bookingPage.verifyBookingPageDisplayed()
                .clickMyAccount();

        accountPage.verifyMyAccountPageDisplayed()
                .clickBookings()
                .verifyMyBookingsDisplayed()
                .verifyCarIsReserved()
                .clickReservedDetails()
                .cancelBooking();
    }

    @Video
    @Test(dependsOnGroups = "Booking", priority=3)
    public void verifyBookingCanceled(){
        login();

        bookingPage.verifyBookingPageDisplayed()
                .clickMyAccount();

        accountPage.verifyMyAccountPageDisplayed()
                .clickBookings()
                .verifyMyBookingsDisplayed()
                .verifyNoCarsReserved();
    }

    @Video
    @Test(priority=10)
    public void loggedUserCanChangeCountry(){
        login();

        bookingPage.verifyBookingPageDisplayed()
                .clickMyAccount();

        accountPage.verifyMyAccountPageDisplayed()
                .clickEditAccount()
                .verifyEditAccountDisplayed()
                .changeCountryTo("BE")
                .submitAccountChanges()
                .logoutCurrentUser();

        login();

        bookingPage.verifyBookingPageDisplayed()
                .clickMyAccount();

        String currentCountry = accountPage.verifyMyAccountPageDisplayed()
                .getCurentUserCountry();

        String expexctedCountry = null;
        switch (LANGUAGE){
            case DE : expexctedCountry = "BELGIEN";
                    break;
            case EN : expexctedCountry = "BELGIUM";
                    break;
        }

        if (!currentCountry.equalsIgnoreCase(expexctedCountry)) {
            throw new RuntimeException("Failed to change land! Current land is still : " + currentCountry);
        }
    }

    @Video
    @Test(dependsOnMethods = { "loggedUserCanChangeCountry" }, priority=10)
    public void loggedUserCenRestoreDefaultCountry(){
        login();

        bookingPage.verifyBookingPageDisplayed()
                .clickMyAccount();

        String currentCountry = accountPage.verifyMyAccountPageDisplayed()
                .getCurentUserCountry();

        //current country is Deutschland, no need to change it
        if (currentCountry.equalsIgnoreCase("Deutschland")) {
            return;
        }

        accountPage.verifyMyAccountPageDisplayed()
                .clickEditAccount()
                .verifyEditAccountDisplayed()
                .changeCountryTo("DE")
                .submitAccountChanges()
                .logoutCurrentUser();

        login();

        bookingPage.verifyBookingPageDisplayed()
                .clickMyAccount();

        currentCountry = accountPage.verifyMyAccountPageDisplayed()
                .getCurentUserCountry();

        String expexctedCountry = null;
        switch (LANGUAGE){
            case DE : expexctedCountry = "Deutschland";
                break;
            case EN : expexctedCountry = "GERMANY";
                break;
        }
        if (!currentCountry.equalsIgnoreCase(expexctedCountry)) {
            throw new RuntimeException("Failed to restore land! Current land is still: " + currentCountry);
        }
    }
}