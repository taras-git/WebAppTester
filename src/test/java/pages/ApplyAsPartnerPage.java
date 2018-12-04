package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

import static utils.Utils.DE;
import static utils.Utils.EN;

public class ApplyAsPartnerPage extends BasePage {

    public ApplyAsPartnerPage(WebDriver driver) {
        super(driver);
    }

    public void verifyApplyAsPartnerPageDisplayed() {
        switch (LANGUAGE) {
            case DE : {
                super.verifyPageDisplayed("partner-werden", "ApplyAsPartnerPageDE");
                break;
            }
            case EN : {
                super.verifyPageDisplayed("apply-as-partner", "ApplyAsPartnerPageEN");
                break;
            }
        }
    }
}
