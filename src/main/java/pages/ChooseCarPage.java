package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by taras on 7/26/18.
 */
public class ChooseCarPage extends BasePage {

    private final String chooseFirstCarXpath = "(//button[contains(text(), ' Choose car ')])[1]";
    @FindBy(xpath = "(//button[contains(text(), ' Choose car ')])[1]")
    WebElement choosefirstCar;

    public ChooseCarPage(WebDriver driver) {
        super(driver);
    }


    public void verifyChooseCarPageDisplayed() {
        super.verifyPageDisplayed("choose-car", "Choose Car Page");
    }

    public ChooseCarPage waitChooseCarDisplayed(){
        waitElementDisplayed(chooseFirstCarXpath, 30);
        return this;
    }

    public void clickChooseCar(){
//        scrollToElement(choosefirstCar);
        scrollPageDown();
        waitElementClickable(chooseFirstCarXpath, 10);
        choosefirstCar.click();
    }


}
