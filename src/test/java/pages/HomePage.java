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
import utils.JsonReader;

import java.util.List;

import static utils.Utils.DE;
import static utils.Utils.EN;

/**
 * Created by taras on 7/19/18.
 */
public class HomePage extends BasePage{

    private static final Logger LOG = LoggerFactory.getLogger(HomePage.class);

    @FindBy(xpath = "//nav[@id=\"top-navigation\"]//*[contains(text(), 'Book Vehicle')]")
    private WebElement bookVehicleEn;

    @FindBy(xpath = "//nav[@id=\"top-navigation\"]//*[contains(text(), 'Fahrzeug buchen')]")
    private WebElement bookVehicleDe;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'Rates')]")
    private WebElement ratesEn;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-1']//a[contains(text(), 'Tarife')]")
    private WebElement ratesDe;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'Fleet')]")
    private WebElement fleetEn;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-1']//a[contains(text(), 'Fahrzeugflotte')]")
    private WebElement fleetDe;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'How it Works')]")
    private WebElement howItWorksEn;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-1']//a[contains(text(), 'So geht´s')]")
    private WebElement howItWorksDe;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'Blog')]")
    private WebElement blogEn;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-1']//a[contains(text(), 'Blog')]")
    private WebElement blogDe;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'Company')]")
    private WebElement companyEn;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-1']//a[contains(text(), 'Unternehmen')]")
    private WebElement companyDe;

    @FindBy(xpath = "//a[contains(text(), 'Apply as partner')]")
    private WebElement applyAsPartnerEn;

    @FindBy(xpath = "//a[contains(text(), 'Partner werden')]")
    private WebElement applyAsPartnerDe;

    @FindBy(xpath = "//a[contains(text(), 'Contact')]")
    private WebElement contactEn;

    @FindBy(xpath = "//a[contains(text(), 'Kontakt')]")
    private WebElement contactDe;

    @FindBy(xpath = "//a[contains(text(), 'Corporate Mobility')]")
    private WebElement corporateMobilityDeEn;

    @FindBy(xpath = "//a[contains(text(), 'Register now')]")
    private WebElement registerNowEn;

    @FindBy(xpath = "//a[contains(text(), 'Jetzt registrieren')]")
    private WebElement registerNowDe;

    @FindBy(xpath = "(//*[contains(text(), 'Mein Konto')])[1]")
    private WebElement loginDeEn;

    @FindBy(xpath = "(//a[contains(text(), 'Locations')])[2]")
    private WebElement locationsEn;

    @FindBy(xpath = "(//a[contains(text(), 'Stationen')])[2]")
    private WebElement locationsDe;

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

    String stationsXpathEn = "(//a[contains(text(), 'Stations')])[2]";
    @FindBy(xpath = "(//a[contains(text(), 'Stations')])[2]")
    private WebElement stationsEn;

    String stationsXpathDe = "(//a[contains(text(), 'Standorte')])[2]";
    @FindBy(xpath = "(//a[contains(text(), 'Standorte')])[2]")
    private WebElement stationsDe;

    String airfieldsXpathEn = "(//a[contains(text(), 'Airfields')])[2]";
    @FindBy(xpath = "(//a[contains(text(), 'Airfields')])[2]")
    private WebElement airfieldsEn;

    String airfieldsXpathDe = "(//a[contains(text(), 'Verkehrslandeplätze')])[2]";
    @FindBy(xpath = "(//a[contains(text(), 'Verkehrslandeplätze')])[2]")
    private WebElement airfieldsDe;

    @FindBy(css = ".desktop#same-station-field")
    private WebElement chooseStation;

    By station = By.cssSelector(".desktop .station");

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

    public void clickBookVehicle() {
        switch (LANGUAGE){
            case DE : {
                clickOn(bookVehicleDe);
                return;
            }
            case EN : {
                clickOn(bookVehicleEn);
                return;
            }
        }
    }

    public void clickLogin(){
        clickOn(loginDeEn);
        return;
    }

    public void clickRates(){
        switch(LANGUAGE){
            case DE : {
                clickOn(ratesDe);
                return;
            }
            case EN : {
                clickOn(ratesEn);
                return;
            }
        }
    }

    public void clickFleet() {
        switch(LANGUAGE){
            case DE : {
                clickOn(fleetDe);
                return;
            }
            case EN : {
                clickOn(fleetEn);
                return;
            }
        }
    }

    public void clickHowItWorks() {
        switch(LANGUAGE){
            case DE : {
                clickOn(howItWorksDe);
                return;
            }
            case EN : {
                clickOn(howItWorksEn);
                return;
            }
        }
    }

    public void clickCompany() {
        switch(LANGUAGE){
            case DE : {
                clickOn(companyDe);
                return;
            }
            case EN : {
                clickOn(companyEn);
                return;
            }
        }
    }

    public void clickBlog() {
        switch(LANGUAGE){
            case DE : {
                clickOn(blogDe);
                return;
            }
            case EN : {
                clickOn(blogEn);
                return;
            }
        }
    }

    public void clickRegisterNow() {
        switch(LANGUAGE){
            case DE : {
                clickOn(registerNowDe);
                return;
            }
            case EN : {
                clickOn(registerNowEn);
                return;
            }
        }
    }

    public void clickApplyAsPartner() {
        switch(LANGUAGE){
            case DE : {
                clickOn(applyAsPartnerDe);
                return;
            }
            case EN : {
                clickOn(applyAsPartnerEn);
                return;
            }
        }
    }

    public void clickCorporateMobility() {
        clickOn(corporateMobilityDeEn);
        return;
    }

    public void clickContact() {
        switch(LANGUAGE){
            case DE : {
                clickOn(contactDe);
                return;
            }
            case EN : {
                clickOn(contactEn);
                return;
            }
        }
    }

    public HomePage clickLocations() {
        switch (LANGUAGE) {
            case DE: {
                clickOn(locationsDe);
                return this;
            }
            case EN: {
                clickOn(locationsEn);
                return this;
            }
        }
        throw new PropertyMisconfigureException();
    }

    public HomePage clickChooseStations(){
        clickOn(chooseStation);
        return this;
    }

    public void moveToLocationsAndClickStations(){
        switch (LANGUAGE) {
            case DE : {
                moveToAndClick(locationsDe, stationsXpathDe);
                break;
            }
            case EN : {
                moveToAndClick(locationsEn, stationsXpathEn);
                break;
            }
        }
    }

    public void moveToLocationsAndClickAirfields(){
        switch (LANGUAGE) {
            case DE : {
                moveToAndClick(locationsDe, airfieldsXpathDe);
                break;
            }
            case EN : {
                moveToAndClick(locationsEn, airfieldsXpathEn);
                break;
            }
        }
    }

    public void verifyStationsDisplayed(){
        List<WebElement>stations =  driver.findElements(station);
        if(stations.isEmpty()){
            Assert.fail("Stations are not displayed!");
        }
        LOG.info("Found stations: " + stations.size());
    }
}
