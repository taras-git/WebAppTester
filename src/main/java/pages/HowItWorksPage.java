package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

/**
 * Created by taras on 7/25/18.
 */
public class HowItWorksPage extends BasePage {


    public HowItWorksPage(WebDriver driver) {
        super(driver);
    }

    public void verifyHowItWorksPageDisplayed() {
        super.verifyPageDisplayed("how-it-works", "How It Works");
    }

}
