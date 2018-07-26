package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

public class ApplyAsPartner extends BasePage {

    public ApplyAsPartnerPage(WebDriver driver) {
        super(driver);
    }

    public void verifyApplyAsPartnerPageDisplayed() {
        super.verifyPageDisplayed("apply-as-partner", "ApplyAsPartner");
    }
}
