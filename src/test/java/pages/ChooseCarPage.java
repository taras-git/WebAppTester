package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.Timeouts.LONGER_TIMEOUT;
import static utils.Timeouts.SHORTER_TIMEOUT;
import static utils.Utils.*;

/**
 * Created by taras on 7/26/18.
 */
public class ChooseCarPage extends BasePage {

    private final String chooseFirstCarXpath = "(//button[@class='btn green__btn rounded choose'])[1]";

    @FindBy(xpath = "(//button[@class='btn green__btn rounded choose'])[1]")
    WebElement choosefirstCar;

    public ChooseCarPage(WebDriver driver) {
        super(driver);
    }

    public void verifyChooseCarPageDisplayed() {
        super.verifyPageDisplayed("choose-car", "Choose Car Page");
    }

    public ChooseCarPage waitChooseCarPageDisplayed(){
        waitElementDisplayed(chooseFirstCarXpath, LONGER_TIMEOUT);
        return this;
    }

    public void chooseFirstCarDisplayed(){
        waitElementClickable(choosefirstCar, SHORTER_TIMEOUT);
        clickOn(choosefirstCar);
    }

}
