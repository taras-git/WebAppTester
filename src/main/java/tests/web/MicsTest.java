package tests.web;

import baseclasses.BaseTestCase;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(VideoListener.class)
public class MicsTest extends BaseTestCase {
    private static final Logger LOG = LoggerFactory.getLogger(MicsTest.class);

    @Video
    @Test(groups="Register", priority=1)
    public void userCanRegisterAsPrivate_Test(){
        homePage.start()
                .clickRegisterNow();

        registerNowPage.verifyRegisterNowPageDisplayed()
                .setPrivateClientType()
                .setSalutationMr()
                .setFirstName()
                .setLastName()
                .setEmail()
                .setLandDe()
                .setAcceptTerms()
                .clickRegister()
                .verifyUserRegisterSuccess();
    }

    @Video
    @Test
    public void verifyStationsDisplayed_Test(){
        homePage.start(getUrlFromEnvEnum("USER_CABINET_EN"))
                .clickChooseStations()
                .verifyStationsDisplayed();
    }
}
