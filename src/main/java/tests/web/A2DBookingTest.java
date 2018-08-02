package tests.web;

import baseclasses.BaseTestCase;
import org.testng.annotations.Test;
import utils.JsonReader;
import utils.Utils;

/**
 * Created by taras on 7/17/18.
 */
public class A2DBookingTest extends BaseTestCase {

    private final String locationName = JsonReader.getString("location");
    private final String email = Utils.getTestUserEmail();
    private final String password = Utils.getTestUserPassword();

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
                .clickChooseCar();

        loginPage.verifyLoginWarningDisplayed();
    }

    @Test
    public void loggedUserCanBookVehicle(){
        homePage.start()
                .clickBookVehicle();

        bookingPage.verifyBookingPageDisplayed()
                .fillCheckOut()
                .fillCheckIn()
                .chooseLocation(locationName);

        bookingPage.clickFindCar();

        chooseCarPage.waitChooseCarDisplayed()
                .clickChooseCar();

        loginPage.verifyLoginWarningDisplayed()
                .login(email, password)
                .verifyUserLogged();
    }

    @Test
    public void loggedUserCanChangeCountry(){
        final String homePageUrl = JsonReader.getString("home_page_test_env");

        homePage.start(homePageUrl)
                .login();

        bookingPage.verifyDEBookingPageDisplayed()
                .clickMeinKonto();

        editAccountPage.verifyMeinKontoPageDisplayed()
                .clickBearbeiten()
                .verifyEditAccountPageDisplayed()
                .changeCountryTo("BE")
                .clickSpeichern()
                .logoutCurrentUser();

        homePage.login();
        bookingPage.verifyDEBookingPageDisplayed()
                .clickMeinKonto();

        String currentCountry = editAccountPage.verifyMeinKontoPageDisplayed()
                .getCurentUserCountry();

        if (currentCountry.equalsIgnoreCase("Deutschland")) {
            throw new RuntimeException("Failed to change land! Current land is still : " + currentCountry);
        }
    }

    @Test(dependsOnMethods = { "loggedUserCanChangeCountry" })
    public void restoreLoggedUserDefaultCountry(){
        final String homePageUrl = JsonReader.getString("home_page_test_env");

        homePage.start(homePageUrl)
                .login();

        bookingPage.verifyDEBookingPageDisplayed()
                .clickMeinKonto();

        String currentCountry = editAccountPage.verifyMeinKontoPageDisplayed()
                .getCurentUserCountry();

        //current country is Deutschland, no need to change it
        if (currentCountry.equalsIgnoreCase("Deutschland")) {
            return;
        }

        editAccountPage.verifyMeinKontoPageDisplayed()
                .clickBearbeiten()
                .verifyEditAccountPageDisplayed()
                .changeCountryTo("DE")
                .clickSpeichern()
                .logoutCurrentUser();

        homePage.login();
        bookingPage.verifyDEBookingPageDisplayed()
                .clickMeinKonto();

        currentCountry = editAccountPage.verifyMeinKontoPageDisplayed()
                .getCurentUserCountry();

        if (!currentCountry.equalsIgnoreCase("Deutschland")) {
            throw new RuntimeException("Failed to restore land! Current land is still: " + currentCountry);
        }
    }

}