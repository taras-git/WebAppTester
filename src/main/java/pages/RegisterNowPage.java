package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

public class RegisterNowPage extends BasePage {

    public RegisterNowPage(WebDriver driver) {
        super(driver);
    }

    public void verifyRegisterNowPageDisplayed() {
        super.verifyPageDisplayed("register", "RegisterNow");
    }
}
