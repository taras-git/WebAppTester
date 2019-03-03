package pages;

import baseclasses.BasePage;
import baseclasses.BaseTestCase;
import exceptions.PropertyMisconfigureException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import static utils.Timeouts.SHORTER_TIMEOUT;
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

    @FindBy(xpath = "//a[contains(text(), 'Koop')]")
    private WebElement koop;

    @FindBy(xpath = "//a[contains(text(), 'Huur')]")
    private WebElement huur;

    @FindBy(xpath = "//a[contains(text(), 'Nieuwbouw')]")
    private WebElement nieuwbouw;

    @FindBy(xpath = "//a[contains(text(), 'Recreatie')]")
    private WebElement recreatie;

    @FindBy(xpath = "//a[contains(text(), 'Europa')]")
    private WebElement europa;

    @FindBy(css = "button.button-primary-alternative")
    private WebElement buttonZoek;

    By searchResultLocator = By.cssSelector("div.container.search-main > div.search-output-result-count > span");



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

    public HomePage clickKoop(){
        clickOn(koop);
        return this;
    }

    public HomePage clickHuur(){
        clickOn(huur);
        return this;
    }

    public HomePage clickNieuwbouw(){
        clickOn(nieuwbouw);
        return this;
    }

    public HomePage clickRecreatie(){
        clickOn(recreatie);
        return this;
    }

    public HomePage clickEuropa(){
        clickOn(europa);
        return this;
    }

    public HomePage verifyKoopDisplayed() {
        super.verifyPageDisplayed("koop", "Koop");
        return this;
    }

    public HomePage verifyHuurDisplayed() {
        super.verifyPageDisplayed("huur", "Huur");
        return this;
    }

    public HomePage verifyNieuwbouwDisplayed() {
        super.verifyPageDisplayed("nieuwbouw", "Nieuwbouw");
        return this;
    }

    public HomePage verifyRecreatieDisplayed() {
        super.verifyPageDisplayed("recreatie", "Recreatie");
        return this;
    }

    public HomePage verifyEuropaDisplayed() {
        super.verifyPageDisplayed("europe", "Europa");
        return this;
    }

    public HomePage clickZoek(){
        clickOn(buttonZoek);
        return this;
    }

    public HomePage verifyResultsGreaterThanZero(){
        waitElementDisplayed(searchResultLocator, SHORTER_TIMEOUT);
        WebElement results = getElement(searchResultLocator);
        String resultCount = results.getText().split(" ")[0];
        resultCount = resultCount.replace(".","");
        Integer resultInt = Integer.valueOf(resultCount);
        Assert.assertTrue(resultInt > 0);
        return this;
    }

}
