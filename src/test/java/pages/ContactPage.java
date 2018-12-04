package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

import static utils.Utils.DE;
import static utils.Utils.EN;

public class ContactPage extends BasePage {

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    public void verifyContactPageDisplayed() {
        switch (LANGUAGE) {
            case DE : {
                super.verifyPageDisplayed("kontakt", "ContactDE");
                break;
            }
            case EN : {
                super.verifyPageDisplayed("contact", "ContactEN");
                break;
            }
        }
    }
}