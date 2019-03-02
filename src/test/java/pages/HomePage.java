package pages;

import baseclasses.BasePage;
import baseclasses.BaseTestCase;
import exceptions.PropertyMisconfigureException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static utils.Utils.*;

/**
 * Created by taras on 7/19/18.
 */
public class HomePage extends BasePage{

    private static final Logger LOG = LoggerFactory.getLogger(HomePage.class);

    @FindBy(xpath = "//")
    private WebElement elEn;

    @FindBy(xpath = "//")
    private WebElement elDe;


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage start() {
        switch (LANGUAGE){
            case DE : {
                driver.get(BaseTestCase.getUrlFromProperty());
                return this;
            }
            case EN : {
                driver.get(BaseTestCase.getUrlFromProperty());
                return this;
            }
        }
        throw new PropertyMisconfigureException();
    }

    public HomePage start(String url) {
        driver.get(url);
        return this;
    }


    public void clickEl(){

        switch (LANGUAGE){
            case DE :
            case DE_MOBILE : {
                clickOn(elDe);
                return;
            }

            case EN:
            case EN_MOBILE: {
                clickOn(elEn);
                return;
            }
        }

    }

}
