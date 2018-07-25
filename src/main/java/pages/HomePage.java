package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.JsonReader;

/**
 * Created by taras on 7/19/18.
 */
public class HomePage extends BasePage{

    protected String homePageEn = JsonReader.getPropertyFileValue("home_page_en");

    @FindBy(xpath = "//nav[@id=\"top-navigation\"]//*[contains(text(), 'Book Vehicle')]")
    WebElement bookVehicle;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'Rates')]")
    WebElement rates;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'Fleet')]")
    WebElement fleet;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'How it Works')]")
    WebElement howItWorks;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage start() {
        driver.get(homePageEn);
        return this;
    }

    public void clickBookVehicle() {
        bookVehicle.click();
    }

    public void clickRates(){
        rates.click();
    }

    public void clickFleet() {
        fleet.click();
    }

    public void clickHowItWorks() {
        howItWorks.click();
    }
}
