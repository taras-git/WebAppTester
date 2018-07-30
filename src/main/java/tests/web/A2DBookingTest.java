package tests.web;

import baseclasses.BaseTestCase;
import org.testng.annotations.Test;
import utils.JsonReader;
import utils.Utils;

/**
 * Created by taras on 7/17/18.
 */
public class A2DBookingTest extends BaseTestCase {

    private final String locationName = JsonReader.getPropertyFileValue("location");
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
}