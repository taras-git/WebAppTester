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

    private final String homePageEnProduction = JsonReader.getUrl("home_page_en_production");
    private final String homePageDeProduction = JsonReader.getUrl("home_page_de_production");
    private final String bookingPageIntera = JsonReader.getUrl("booking_page_intera_en");

    @Test(groups="Booking")
    public void loggedUserCanBookVehicle(){
        login();

        bookingPage.verifyENBookingPageDisplayed()
                .fillCheckOut()
                .fillCheckIn()
                .chooseLocation(locationName)
                .clickFindCar();

        chooseCarPage.waitChooseCarDisplayed()
                .chooseFirstCarDisplayed();

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
        login();

        bookingPage.verifyENBookingPageDisplayed()
                .clickMyAccount();

        accountPage.verifyMyAccountPageDisplayed()
                .clickBookings()
                .verifyMyBookingsDisplayed()
                .verifyCarIsReserved()
                .clickDetails()
                .cancelBooking();
    }

    @Test(dependsOnGroups = "Booking")
    public void verifyBookingCanceled(){
        login();

        bookingPage.verifyENBookingPageDisplayed()
                .clickMyAccount();

        accountPage.verifyMyAccountPageDisplayed()
                .clickBookings()
                .verifyMyBookingsDisplayed()
                .verifyNoCarsReserved();
    }

    private void loginUser(String email, String password, String url) {
        homePage.start(url)
                .clickLogin();

        loginPage.verifyLoginPageDisplayed()
                .waitLoginFieldDisplayed()
                .login(email, password)
                .verifyUserLogged();
    }

    @Test
    public void loggedUserCanChangeCountry(){
        login();

        bookingPage.verifyENBookingPageDisplayed()
                .clickMyAccount();

        accountPage.verifyMyAccountPageDisplayed()
                .clickEditAccount()
                .verifyEditAccountDisplayed()
                .changeCountryTo("BE")
                .submitAccountChanges()
                .logoutCurrentUser();

        login();

        bookingPage.verifyENBookingPageDisplayed()
                .clickMyAccount();

        String currentCountry = accountPage.verifyMyAccountPageDisplayed()
                .getCurentUserCountry();

        if (!currentCountry.equalsIgnoreCase("BELGIEN")) {
            throw new RuntimeException("Failed to change land! Current land is still : " + currentCountry);
        }
    }

    @Test(dependsOnMethods = { "loggedUserCanChangeCountry" })
    public void loggedUserCenRestoreDefaultCountry(){
        login();

        bookingPage.verifyENBookingPageDisplayed()
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

        bookingPage.verifyENBookingPageDisplayed()
                .clickMyAccount();

        currentCountry = accountPage.verifyMyAccountPageDisplayed()
                .getCurentUserCountry();

        if (!currentCountry.equalsIgnoreCase("Deutschland")) {
            throw new RuntimeException("Failed to restore land! Current land is still: " + currentCountry);
        }
    }


    private void login() {
        String env;
        try {
            env = System.getProperty("env");
        } catch (Exception e){
            env = JsonReader.getString("env").toLowerCase();
        }

        switch(env){
            case "prod": {
                loginUser(a2dEmail, a2dPassword, homePageEnProduction);
                return;
            }
            case "intera": {
                homePageIntera.start().login(a2dEmail, a2dPassword);
                return;
            }
        }

        throw new RuntimeException("Environment is not properly set, please check the property.json file!!!");

    }

}