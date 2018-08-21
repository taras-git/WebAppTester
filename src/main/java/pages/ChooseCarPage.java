package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.Utils.DE;
import static utils.Utils.EN;

/**
 * Created by taras on 7/26/18.
 */
public class ChooseCarPage extends BasePage {

    private final String chooseFirstCarXpathEn = "(//button[contains(text(), ' Choose car ')])[1]";
    private final String chooseFirstCarXpathDe = "(//button[contains(text(), ' Fahrzeug wählen ')])[1]";

    @FindBy(xpath = "(//button[contains(text(), ' Choose car ')])[1]")
    WebElement choosefirstCarEn;

    @FindBy(xpath = "(//button[contains(text(), ' Fahrzeug wählen ')])[1]")
    WebElement choosefirstCarDe;

    public ChooseCarPage(WebDriver driver) {
        super(driver);
    }


    public void verifyChooseCarPageDisplayed() {
        super.verifyPageDisplayed("choose-car", "Choose Car Page");
    }

    public ChooseCarPage waitChooseCarDisplayed(){
        switch (LANGUAGE){
            case DE : {
                waitElementDisplayed(chooseFirstCarXpathDe, 30);
                break;
            }
            case EN : {
                waitElementDisplayed(chooseFirstCarXpathEn, 30);
                break;
            }
        }

        return this;
    }

    public void chooseFirstCarDisplayed(){
        switch (LANGUAGE){
            case DE : {
                chooseFirstCarDisplayedDe();
                break;
            }
            case EN : {
                chooseFirstCarDisplayedEn();
                break;
            }
        }
    }

    public void chooseFirstCarDisplayedEn(){
        scrollOnePageDown();
        waitElementClickable(chooseFirstCarXpathEn, 10);
        choosefirstCarEn.click();
    }

    public void chooseFirstCarDisplayedDe(){
        scrollOnePageDown();
        waitElementClickable(chooseFirstCarXpathDe, 10);
        choosefirstCarDe.click();
    }


}
