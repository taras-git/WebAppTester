package pages;

import baseclasses.BasePage;
import exceptions.PropertyMisconfigureException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.JsonReader;

import static utils.Utils.DE;
import static utils.Utils.EN;

/**
 * Created by taras on 7/19/18.
 */
public class HomePage extends BasePage{

    protected String homePageEnProduction = JsonReader.getUrl("home_page_en_production");
    protected String homePageDeProduction = JsonReader.getUrl("home_page_de_production");

    @FindBy(xpath = "//nav[@id=\"top-navigation\"]//*[contains(text(), 'Book Vehicle')]")
    private WebElement bookVehicleEn;

    @FindBy(xpath = "//nav[@id=\"top-navigation\"]//*[contains(text(), 'Fahrzeug buchen')]")
    private WebElement bookVehicleDe;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'Rates')]")
    private WebElement ratesEn;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-1']//a[contains(text(), 'Tarife')]")
    private WebElement ratesDe;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'Fleet')]")
    private WebElement fleet;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'How it Works')]")
    private WebElement howItWorks;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'Blog')]")
    private WebElement blog;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'Company')]")
    private WebElement company;

    @FindBy(xpath = "//a[contains(text(), 'Apply as partner')]")
    private WebElement applyAsPartner;

    @FindBy(xpath = "//a[contains(text(), 'Contact')]")
    private WebElement contact;

    @FindBy(xpath = "//a[contains(text(), 'Corporate Mobility')]")
    private WebElement corporateMobility;

    @FindBy(xpath = "//a[contains(text(), 'Register now')]")
    private WebElement registerNow;

    @FindBy(xpath = "//span[contains(text(), 'Login')]")
    private WebElement login;

    @FindBy(xpath = "(//a[contains(text(), 'Locations')])[2]")
    private WebElement locations;

    String stationsXpath = "(//a[contains(text(), 'Stations')])[2]";
    @FindBy(xpath = "(//a[contains(text(), 'Stations')])[2]")
    private WebElement stations;

    String airfieldsXpath = "(//a[contains(text(), 'Airfields')])[2]";
    @FindBy(xpath = "(//a[contains(text(), 'Airfields')])[2]")
    private WebElement airfields;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage start() {
        switch (LANGUAGE){
            case DE : {
                driver.get(homePageDeProduction);
                return this;
            }
            case EN : {
                driver.get(homePageEnProduction);
                return this;
            }
        }
        throw new PropertyMisconfigureException();
    }

    public HomePage start(String url) {
        driver.get(url);
        return this;
    }

    public void clickBookVehicle() {
        switch (LANGUAGE){
            case DE : {
                bookVehicleDe.click();
                return;
            }
            case EN : {
                bookVehicleEn.click();
                return;
            }
        }
    }

    public void clickLogin(){
        login.click();
    }

    public void clickRates(){
        switch(LANGUAGE){
            case DE : {
                ratesDe.click();
                return;
            }
            case EN : {
                ratesEn.click();
                return;
            }
        }
    }

    public void clickFleet() {
        fleet.click();
    }

    public void clickHowItWorks() {
        howItWorks.click();
    }

    public void clickCompany() {
        company.click();
    }

    public void clickBlog() {
        blog.click();
    }

    public void clickRegisterNow() {
        registerNow.click();
    }

    public void clickApplyAsPartner() {
        applyAsPartner.click();
    }

    public void clickCorporateMobility() {
        corporateMobility.click();
    }

    public void clickContact() {
        contact.click();
    }

    public HomePage clickLocations() {
        locations.click();
        return this;
    }

    public void clickAirfields() { airfields.click(); }

    public void moveToLocationsAndClickStations(){
        moveToAndClick(locations, stationsXpath);
    }

    public void moveToLocationsAndClickAirfields(){
        moveToAndClick(locations, airfieldsXpath);
    }

}
