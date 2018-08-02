package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

public class AirfieldsPage extends BasePage {

    public AirfieldsPage(WebDriver driver) {
        super(driver);
    }

    public void verifyAirfieldsPageDisplayed() {
        super.verifyPageDisplayed("airfields", "Airfields");
    }
}
