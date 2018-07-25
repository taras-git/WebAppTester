package pages;

import baseclasses.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
    }
}
