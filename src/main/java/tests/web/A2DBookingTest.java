package tests.web;

import baseclasses.BaseTestCase;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.EmailReader;
import utils.JsonReader;

import java.util.Date;

import static utils.Utils.DE;
import static utils.Utils.EN;


/**
 * Created by taras on 7/17/18.
 */
@Listeners(VideoListener.class)
public class A2DBookingTest extends BaseTestCase {

    private static final Logger LOG = LoggerFactory.getLogger(A2DBookingTest.class);
    private final String locationName = JsonReader.getLocation("location1");
    private final String a2dEmail = JsonReader.getUserEmail("app2_driver");
    private final String a2dPassword = JsonReader.getUserPassword("app2_driver");

    private final String homePageProductionEn = JsonReader.getUrl("home_page_en_production");
    private final String homePageProductionDe = JsonReader.getUrl("home_page_de_production");
    private final String bookingPageInteraEn = JsonReader.getUrl("booking_page_intera_en");
    private final String bookingPageInteraDe = JsonReader.getUrl("booking_page_intera_de");

    @Video
    @Test(groups="Booking")
    public void loggedUserCanBookVehicle(){
        login();

        bookingPage.verifyBookingPageDisplayed()
                .fillCheckOut()
                .fillCheckIn()
                .chooseLocation(locationName)
                .clickFindCar();

        chooseCarPage.waitChooseCarDisplayed()
                .chooseFirstCarDisplayed();

        Date withBookingDate = new Date();

        confirmBookingPage.bookCar()
                .verifyCarBooked();

        try {
            EmailReader.checkConfirmationEmailReceived(withBookingDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Video
    @Test(groups="Booking", dependsOnMethods = "loggedUserCanBookVehicle")
    public void loggedUserCanCancelBooking(){
        login();

        bookingPage.verifyBookingPageDisplayed()
                .clickMyAccount();

        accountPage.verifyMyAccountPageDisplayed()
                .clickBookings()
                .verifyMyBookingsDisplayed()
                .verifyCarIsReserved()
                .clickDetails()
                .cancelBooking();
    }

    @Video
    @Test(dependsOnGroups = "Booking")
    public void verifyBookingCanceled(){
        login();

        bookingPage.verifyBookingPageDisplayed()
                .clickMyAccount();

        accountPage.verifyMyAccountPageDisplayed()
                .clickBookings()
                .verifyMyBookingsDisplayed()
                .verifyNoCarsReserved();
    }

    private void loginUserProd(String email, String password, String url) {
        homePage.start(url)
                .clickLogin();

        loginPage.verifyLoginPageDisplayed()
                .waitLoginFieldDisplayed()
                .login(email, password)
                .verifyUserLogged();
    }

    private void loginUserIntera(String email, String password, String url) {
        homePageIntera.start(url)
                .login(email, password);

        loginPage.verifyUserLogged();
    }

    @Video
    @Test
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
    @Test(dependsOnMethods = { "loggedUserCanChangeCountry" })
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


    private void login() {
        switch(ENVIRONMENT){
            case "prod_en": {
                loginUserProd(a2dEmail, a2dPassword, homePageProductionEn);
                return;
            }

            case "prod_de": {
                loginUserProd(a2dEmail, a2dPassword, homePageProductionDe);
                return;
            }

            case "intera_en": {
                loginUserIntera(a2dEmail, a2dPassword, bookingPageInteraEn);
                return;
            }

            case "intera_de": {
                loginUserIntera(a2dEmail, a2dPassword, bookingPageInteraDe);
                return;
            }

        }

        throw new RuntimeException("Environment is not properly set, please check the property.json/urls.json files!!!");
    }

}