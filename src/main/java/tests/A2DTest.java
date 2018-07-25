package tests;

import baseclasses.BaseTestCase;
import org.testng.annotations.Test;

/**
 * Created by taras on 7/17/18.
 */
public class A2DTest extends BaseTestCase {

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

        HowItWorksPage.verifyHowItWorksPageDisplayed();
    }

    @Test
    public void navigateToCompanyPage() {
        homePage.start()
                .clickCompany();

        companyPage.verifyCompanyPageDisplayed();
    }

    @Test
    public void navigateToBlog90 {
        homePage.start()
                .clickBlog();

        BlogPage.verifyBlogPageDisplayed();
    }
}
