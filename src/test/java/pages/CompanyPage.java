package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

import static utils.Utils.DE;
import static utils.Utils.EN;

/**
 * Created by taras on 7/25/18.
 */
public class CompanyPage extends BasePage {

    public CompanyPage(WebDriver driver) {
        super(driver);
    }

    public void verifyCompanyPageDisplayed() {
        switch (LANGUAGE) {
            case DE : {
                super.verifyPageDisplayed("unternehmen", "CompanyDE");
                break;
            }
            case EN : {
                super.verifyPageDisplayed("company", "CompanyEN");
                break;
            }
        }
    }
}
