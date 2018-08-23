package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

import static utils.Utils.DE;
import static utils.Utils.EN;

public class AirfieldsPage extends BasePage {

    public AirfieldsPage(WebDriver driver) {
        super(driver);
    }

    public void verifyAirfieldsPageDisplayed() {
        switch (LANGUAGE) {
            case DE : {
                super.verifyPageDisplayed("verkehrslandeplaetze", "AirfieldsDE");
                break;
            }
            case EN : {
                super.verifyPageDisplayed("airfields", "AirfieldsEN");
                break;
            }
        }
    }
}
