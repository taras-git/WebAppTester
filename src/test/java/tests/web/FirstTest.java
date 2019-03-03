package tests.web;

import baseclasses.BaseTestCase;
import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


/**
 * Created by taras on 7/17/18.
 */
@Listeners(VideoListener.class)
public class FirstTest extends BaseTestCase {

    private static final Logger LOG = LoggerFactory.getLogger(FirstTest.class);

    @Video
    @Test(groups="Smoke", priority=1)
    public void verifyKoopLink(){
        homePage.start()
                .clickKoop()
                .verifyKoopDisplayed()
                .clickZoek()
                .verifyResultsGreaterThanZero();
    }

    @Video
    @Test(groups="Smoke", priority=1)
    public void verifyHuurLink(){
        homePage.start()
                .clickHuur()
                .verifyHuurDisplayed()
                .clickZoek()
                .verifyResultsGreaterThanZero();
    }

    @Video
    @Test(groups="Smoke", priority=1)
    public void verifyNieuwbouwLink(){
        homePage.start()
                .clickNieuwbouw()
                .verifyNieuwbouwDisplayed()
                .clickZoek()
                .verifyResultsGreaterThanZero();
    }

    @Video
    @Test(groups="Smoke", priority=1)
    public void verifyRecreatieLink(){
        homePage.start()
                .clickRecreatie()
                .verifyRecreatieDisplayed()
                .clickZoek()
                .verifyResultsGreaterThanZero();
    }

    @Video
    @Test(groups="Smoke", priority=1)
    public void verifyEuropaLink(){
        homePage.start()
                .clickEuropa()
                .verifyEuropaDisplayed()
                .clickZoek()
                .verifyResultsGreaterThanZero();
    }

}