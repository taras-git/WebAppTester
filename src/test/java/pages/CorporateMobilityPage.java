package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

import static utils.Utils.DE;
import static utils.Utils.EN;

public class CorporateMobilityPage extends BasePage {

    public CorporateMobilityPage(WebDriver driver) {
        super(driver);
    }

    public void verifyCorporateMobilityPageDisplayed() {
        switch (LANGUAGE) {
            case DE : {
                super.verifyPageDisplayed("firmenkunde_werden", "CorporateMobilityDE");
                break;
            }
            case EN: {
                super.verifyPageDisplayed("become_a_corporate_customer", "CorporateMobiityEN");
                break;
            }
        }
    }
}
