package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.Utils.DE;
import static utils.Utils.EN;

/**
 * Created by taras on 7/25/18.
 */
public class FleetPage extends BasePage{

    public FleetPage(WebDriver driver) {
        super(driver);
    }

    public void verifyFleetPageDisplayed() {
        switch (LANGUAGE) {
            case    DE : {
                super.verifyPageDisplayed("fahrzeugflotte", "FleetDE");
                break;
            }
            case EN : {
                super.verifyPageDisplayed("fleet", "FleetEN");
                break;
            }
        }
    }
}
