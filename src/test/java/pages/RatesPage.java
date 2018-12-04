package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by taras on 7/24/18.
 */
public class RatesPage extends BasePage{

    @FindBy(xpath = "//div[@data-tarifname='Basic']")
    WebElement basicRates;

    public RatesPage(WebDriver driver) {
        super(driver);
    }

    public void verifyRatesPageDisplayed() {
        clickOn(basicRates);
    }
}
