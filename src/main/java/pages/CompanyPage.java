package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

/**
 * Created by taras on 7/25/18.
 */
public class CompanyPage extends BasePage {

    public CompanyPage(WebDriver driver) {
        super(driver);
    }

    public void verifyCompanyPageDisplayed() {
        super.verifyPageDisplayed("company", "Company");
    }
}
