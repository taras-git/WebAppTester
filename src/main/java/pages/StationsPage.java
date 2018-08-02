package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

public class StationsPage extends BasePage {

    public StationsPage(WebDriver driver) {
        super(driver);
    }

    public void verifyStationsPageDisplayed() {
        super.verifyPageDisplayed("stations", "Stations");
    }
}
