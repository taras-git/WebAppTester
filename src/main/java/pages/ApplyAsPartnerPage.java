package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

public class ApplyAsPartnerPage extends BasePage {

    public ApplyAsPartnerPage(WebDriver driver) {
        super(driver);
    }

    public void verifyApplyAsPartnerPageDisplayed() {
        super.verifyPageDisplayed("apply-as-partner", "ApplyAsPartnerPage");
    }
}
