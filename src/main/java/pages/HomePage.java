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
    WebElement bookVehicleEn;

    @FindBy(xpath = "//nav[@id=\"top-navigation\"]//*[contains(text(), 'Fahrzeug buchen')]")
    WebElement bookVehicleDe;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'Rates')]")
    WebElement ratesEn;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-1']//a[contains(text(), 'Tarife')]")
    WebElement ratesDe;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'Fleet')]")
    WebElement fleetEn;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-1']//a[contains(text(), 'Fahrzeugflotte')]")
    WebElement fleetDe;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'How it Works')]")
    WebElement howItWorksEn;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-1']//a[contains(text(), 'So geht´s')]")
    WebElement howItWorksDe;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'Blog')]")
    WebElement blogEn;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-1']//a[contains(text(), 'Blog')]")
    WebElement blogDe;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'Company')]")
    WebElement companyEn;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-1']//a[contains(text(), 'Unternehmen')]")
    WebElement companyDe;

    @FindBy(xpath = "//a[contains(text(), 'Apply as partner')]")
    WebElement applyAsPartnerEn;

    @FindBy(xpath = "//a[contains(text(), 'Partner werden')]")
    WebElement applyAsPartnerDe;

    @FindBy(xpath = "//a[contains(text(), 'Contact')]")
    WebElement contactEn;

    @FindBy(xpath = "//a[contains(text(), 'Kontakt')]")
    WebElement contactDe;

    @FindBy(xpath = "//a[contains(text(), 'Corporate Mobility')]")
    WebElement corporateMobilityDeEn;

    @FindBy(xpath = "//a[contains(text(), 'Register now')]")
    WebElement registerNowEn;

    @FindBy(xpath = "//a[contains(text(), 'Jetzt registrieren')]")
    WebElement registerNowDe;

    @FindBy(xpath = "//span[contains(text(), 'Login')]")
    WebElement loginDeEn;

    @FindBy(xpath = "(//a[contains(text(), 'Locations')])[2]")
    WebElement locationsEn;

    @FindBy(xpath = "(//a[contains(text(), 'Stationen')])[2]")
    WebElement locationsDe;

    String stationsXpathEn = "(//a[contains(text(), 'Stations')])[2]";
    @FindBy(xpath = "(//a[contains(text(), 'Stations')])[2]")
    WebElement stationsEn;

    String stationsXpathDe = "(//a[contains(text(), 'Standorte')])[2]";
    @FindBy(xpath = "(//a[contains(text(), 'Standorte')])[2]")
    WebElement stationsDe;

    String airfieldsXpathEn = "(//a[contains(text(), 'Airfields')])[2]";
    @FindBy(xpath = "(//a[contains(text(), 'Airfields')])[2]")
    WebElement airfieldsEn;

    String airfieldsXpathDe = "(//a[contains(text(), 'Verkehrslandeplätze')])[2]";
    @FindBy(xpath = "(//a[contains(text(), 'Verkehrslandeplätze')])[2]")
    WebElement airfieldsDe;

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
        /*switch(LANGUAGE){
            case DE : {*/
                loginDeEn.click();
                return;
            /*}
            case EN : {
                loginEn.click();
                return;
            }
        }*/
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
        switch(LANGUAGE){
            case DE : {
                fleetDe.click();
                return;
            }
            case EN : {
                fleetEn.click();
                return;
            }
        }
    }

    public void clickHowItWorks() {
        switch(LANGUAGE){
            case DE : {
                howItWorksDe.click();
                return;
            }
            case EN : {
                howItWorksEn.click();
                return;
            }
        }
    }

    public void clickCompany() {
        switch(LANGUAGE){
            case DE : {
                companyDe.click ();
                return;
            }
            case EN : {
                companyEn.click();
                return;
            }
        }
    }

    public void clickBlog() {
        switch(LANGUAGE){
            case DE : {
                blogDe.click();
                return;
            }
            case EN : {
                blogEn.click();
                return;
            }
        }
    }

    public void clickRegisterNow() {
        switch(LANGUAGE){
            case DE : {
                registerNowDe.click();
                return;
            }
            case EN : {
                registerNowEn.click();
                return;
            }
        }
    }

    public void clickApplyAsPartner() {
        switch(LANGUAGE){
            case DE : {
                applyAsPartnerDe.click();
                return;
            }
            case EN : {
                applyAsPartnerEn.click();
                return;
            }
        }
    }

    public void clickCorporateMobility() {
        /*switch(LANGUAGE){
            case DE : {*/
                corporateMobilityDeEn.click();
                return;
            /*}
            case EN : {
                corporateMobilityEn.click();
                return;
            }
        }*/
    }

    public void clickContact() {
        switch(LANGUAGE){
            case DE : {
                contactDe.click();
                return;
            }
            case EN : {
                contactEn.click();
                return;
            }
        }
    }

    public HomePage clickLocations() {
        switch (LANGUAGE) {
            case DE: {
                locationsDe.click();
                return this;
            }
            case EN: {
                locationsEn.click();
                return this;
            }
        }
        throw new PropertyMisconfigureException();
    }

    public void clickAirfields() { airfieldsEn.click(); }

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
}
