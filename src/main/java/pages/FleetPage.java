package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by taras on 7/25/18.
 */
public class FleetPage extends BasePage{


    public FleetPage(WebDriver driver) {
        super(driver);
    }

    public void verifyFleetPageDisplayed() {
        if (!wait.until(ExpectedConditions.urlContains("fleet")))
            throw new RuntimeException("Fleet page is not displayed");
    }
}
