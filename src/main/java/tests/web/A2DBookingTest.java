package tests.web;

import baseclasses.BaseTestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import utils.EmailReader;
import utils.JsonReader;

/**
 * Created by taras on 7/17/18.
 */
public class A2DBookingTest extends BaseTestCase {

    private static final Logger LOG = LoggerFactory.getLogger(A2DBookingTest.class);
    private final String locationName = JsonReader.getLocation("location1");
    private final String a2dEmail = JsonReader.getUserEmail("app2_driver");
    private final String a2dPassword = JsonReader.getUserPassword("app2_driver");
    private final String tarNimetsEmail = JsonReader.getUserEmail("tar_nimets");
    private final String tarNimetsPassword = JsonReader.getUserPassword("tar_nimets");


    @Test
    public void bookVehicleUserNotLogged(){
        homePage.start()
                .clickBookVehicle();

        bookingPage.verifyBookingPageDisplayed()
                .fillCheckOut()
                .fillCheckIn()
                .chooseLocation(locationName);

        bookingPage.clickFindCar();

        chooseCarPage.waitChooseCarDisplayed()
                .chooseFirstCarDisplayed();

        loginPage.verifyLoginWarningDisplayed();
    }

    @Test(groups="Booking")
    public void loggedUserCanBookVehicle(){
        homePage.start()
                .clickBookVehicle();

        bookingPage.verifyBookingPageDisplayed()
                .fillCheckOut()
                .fillCheckIn()
                .chooseLocation(locationName);

        bookingPage.clickFindCar();

        chooseCarPage.waitChooseCarDisplayed()
                .chooseFirstCarDisplayed();

        loginPage.verifyLoginWarningDisplayed()
                .login(a2dEmail, a2dPassword)
                .verifyUserLogged();

        confirmBookingPage.bookCar()
                .verifyCarBooked();

        try {
            EmailReader.checkConfirmationEmailReceived();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test(groups="Booking", dependsOnMethods = "loggedUserCanBookVehicle")
    public void loggedUserCanCancelBooking(){
        loginToMyAccount();

        accountPage.verifyMyAccountPageDisplayed()
                .clickBookings()
                .verifyMyBookingsDisplayed()
                .verifyCarIsReserved()
                .clickDetails()
                .cancelBooking();
    }

    @Test(dependsOnGroups = "Booking")
    public void verifyBookingCanceled(){
        loginToMyAccount();

        accountPage.verifyMyAccountPageDisplayed()
                .clickBookings()
                .verifyMyBookingsDisplayed()
                .verifyNoCarsReserved();
    }

    private void loginToMyAccount() {
        homePage.start()
                .clickLogin();

        loginPage.verifyLoginPageDisplayed()
                .waitLoginFieldDisplayed()
                .login(a2dEmail, a2dPassword)
                .verifyUserLogged();

        bookingPage.verifyBookingPageDisplayed()
                .clickMyAccount();
    }

    @Test
    public void loggedUserCanChangeCountry(){
        homePageIntera.start()
                .login(tarNimetsEmail, tarNimetsPassword);

        bookingPage.verifyDEBookingPageDisplayed()
                .clickMeinKonto();

        accountPage.verifyMeinKontoPageDisplayed()
                .clickBearbeiten()
                .verifyEditAccountDisplayed()
                .changeCountryTo("BE")
                .clickSpeichern()
                .logoutCurrentUser();

        homePageIntera.login(tarNimetsEmail, tarNimetsPassword);
        bookingPage.verifyDEBookingPageDisplayed()
                .clickMeinKonto();

        String currentCountry = accountPage.verifyMeinKontoPageDisplayed()
                .getCurentUserCountry();

        if (!currentCountry.equalsIgnoreCase("BELGIEN")) {
            throw new RuntimeException("Failed to change land! Current land is still : " + currentCountry);
        }
    }

    @Test(dependsOnMethods = { "loggedUserCanChangeCountry" })
    public void loggedUserCenRestoreDefaultCountry(){
        homePageIntera.start()
                .login(tarNimetsEmail, tarNimetsPassword);

        bookingPage.verifyDEBookingPageDisplayed()
                .clickMeinKonto();

        String currentCountry = accountPage.verifyMeinKontoPageDisplayed()
                .getCurentUserCountry();

        //current country is Deutschland, no need to change it
        if (currentCountry.equalsIgnoreCase("Deutschland")) {
            return;
        }

        accountPage.verifyMeinKontoPageDisplayed()
                .clickBearbeiten()
                .verifyEditAccountDisplayed()
                .changeCountryTo("DE")
                .clickSpeichern()
                .logoutCurrentUser();

        homePageIntera.login(tarNimetsEmail, tarNimetsPassword);
        bookingPage.verifyDEBookingPageDisplayed()
                .clickMeinKonto();

        currentCountry = accountPage.verifyMeinKontoPageDisplayed()
                .getCurentUserCountry();

        if (!currentCountry.equalsIgnoreCase("Deutschland")) {
            throw new RuntimeException("Failed to restore land! Current land is still: " + currentCountry);
        }
    }

}