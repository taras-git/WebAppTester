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
    @Test(groups="Booking", priority=1)
    public void openHomePage(){
        homePage.start();
    }

}