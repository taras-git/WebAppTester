package tests;

import baseclasses.BaseTestCase;
import org.testng.annotations.Test;

/**
 * Created by taras on 7/30/18.
 */
public class A2DNavigationTests extends BaseTestCase{
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

}
