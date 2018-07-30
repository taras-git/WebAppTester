package tests;

import baseclasses.BaseTestCase;
import org.testng.annotations.Test;
import utils.JsonReader;
import utils.Utils;

/**
 * Created by taras on 7/17/18.
 */
public class A2DTest extends BaseTestCase {

    private final String locationName = JsonReader.getPropertyFileValue("location");
    private final String email = Utils.getTestUserEmail();
    private final String password = Utils.getTestUserPassword();

    @Test
    public void navigateToBookingPage() {
        homePage.start()
                .clickBookVehicle();
        bookingPage.verifyBookingPageDisplayed();
    }

    @Test
    public void navigateToRatesPage() {
        homePage.start()
                .clickRates();
        ratesPage.verifyRatesPageDisplayed();
    }

    @Test
    public void navigateToFleetPage() {
        homePage.start()
                .clickFleet();
        fleetPage.verifyFleetPageDisplayed();
    }

    @Test
    public void navigateToHowItWorksPage() {
        homePage.start()
                .clickHowItWorks();
        howItWorksPage.verifyHowItWorksPageDisplayed();
    }

    @Test
    public void navigateToCompanyPage() {
        homePage.start()
                .clickCompany();
        companyPage.verifyCompanyPageDisplayed();
    }

    @Test
    public void navigateToBlog() {
        homePage.start()
                .clickBlog();
        blogPage.verifyBlogPageDisplayed();
    }

    @Test
    public void navigateToRegisterNowPage() {
        homePage.start()
                .clickRegisterNow();

        registerNowPage.verifyRegisterNowPageDisplayed();
    }

    @Test
    public void navigateToApplyAsPartnerPage() {
        homePage.start()
                .clickApplyAsPartner();

        applyAsPartnerPage.verifyApplyAsPartnerPageDisplayed();
    }

    @Test
    public void navigateToCorporateMobilityPage() {
        homePage.start()
                .clickCorporateMobility();

        corporateMobilityPage.verifyCorporateMobilityPageDisplayed();
    }

    @Test
    public void navigateToContactPage() {
        homePage.start()
                .clickContact();

        contactPage.verifyContactPageDisplayed();
    }


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