package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

import static utils.Utils.DE;
import static utils.Utils.EN;

public class StationsPage extends BasePage {

    public StationsPage(WebDriver driver) {
        super(driver);
    }

    public void verifyStationsPageDisplayed() {
        switch (LANGUAGE) {
            case DE : {
                super.verifyPageDisplayed("standorte", "StationsDE");
                break;
            }
            case EN : {
                super.verifyPageDisplayed("stations", "StationsEN");
                break;
            }
        }
    }
}
