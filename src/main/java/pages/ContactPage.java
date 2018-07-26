package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

public class ContactPage extends BasePage {

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    public void verifyContactPageDisplayed() {
        super.verifyPageDisplayed("contact", "Contact");
    }

}