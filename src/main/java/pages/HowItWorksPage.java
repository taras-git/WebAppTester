package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

import static utils.Utils.DE;
import static utils.Utils.EN;

/**
 * Created by taras on 7/25/18.
 */
public class HowItWorksPage extends BasePage {


    public HowItWorksPage(WebDriver driver) {
        super(driver);
    }

    public void verifyHowItWorksPageDisplayed() {
        switch (LANGUAGE) {
            case DE : {
                super.verifyPageDisplayed("so-funktioniert-es", "How It WorksDE");
                break;
            }
            case EN : {
                super.verifyPageDisplayed("how-it-works", "How It WorksEN");
                break;
            }
        }
    }
}
