package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

public class CorporateMobility extends BasePage {

    public CorporateMobilityPage(WebDriver driver) {
        super(driver);
    }

    public void verifyCorporateMobilityPageDisplayed() {
        super.verifyPageDisplayed("CORPORATE", "CorporateMobiity");
    }
}
