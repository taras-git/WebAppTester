package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

import static utils.Utils.DE;
import static utils.Utils.EN;

public class RegisterNowPage extends BasePage {

    public RegisterNowPage(WebDriver driver) {
        super(driver);
    }

    public void verifyRegisterNowPageDisplayed() {
        switch (LANGUAGE) {
            case DE : {
                super.verifyPageDisplayed("kunde_werden", "RegisterNowDE");
                break;
            }
            case EN : {
                super.verifyPageDisplayed("register", "RegisterNowEN");
                break;
            }
        }
    }
}
