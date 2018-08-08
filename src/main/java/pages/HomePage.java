package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.JsonReader;

import static utils.Utils.sleep;

/**
 * Created by taras on 7/19/18.
 */
public class HomePage extends BasePage{

    protected String homePageEnProduction = JsonReader.getUrl("home_page_en_production");

    @FindBy(xpath = "//nav[@id=\"top-navigation\"]//*[contains(text(), 'Book Vehicle')]")
    WebElement bookVehicle;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'Rates')]")
    WebElement rates;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'Fleet')]")
    WebElement fleet;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'How it Works')]")
    WebElement howItWorks;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'Blog')]")
    WebElement blog;

    @FindBy(xpath = "//ul[@id='menu-top-navigation-en-1']//a[contains(text(), 'Company')]")
    WebElement company;

    @FindBy(xpath = "//a[contains(text(), 'Apply as partner')]")
    WebElement applyAsPartner;

    @FindBy(xpath = "//a[contains(text(), 'Contact')]")
    WebElement contact;

    @FindBy(xpath = "//a[contains(text(), 'Corporate Mobility')]")
    WebElement corporateMobility;

    @FindBy(xpath = "//a[contains(text(), 'Register now')]")
    WebElement registerNow;

    @FindBy(xpath = "//a[contains(text(), 'Login')]")
    WebElement login;

    @FindBy(xpath = "//button[contains(text(), 'Login')]")
    WebElement loginSubmit;

    @FindBy(css = "#inputEmail3")
    WebElement loginEmail;

    @FindBy(css = "#inputPassword3")
    WebElement loginPassword;

    @FindBy(xpath = "//a[contains(text(), 'Locations')]")
    WebElement locations;

    @FindBy(xpath = "//a[contains(text(), 'Stations')]")
    WebElement stations;

    @FindBy(xpath = "//a[contains(text(), 'Airfields')]")
    WebElement airfields;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage start() {
        driver.get(homePageEnProduction);
        return this;
    }

    public HomePage start(String url) {
        driver.get(url);
        return this;
    }

    public void clickBookVehicle() {
        bookVehicle.click();
    }

    public void login(String email, String password){
        login.click();
        loginEmail.sendKeys(email);
        loginPassword.sendKeys(password);
        loginSubmit.click();
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
//        sleep(1);
    return this;}

    public void clickStations() { stations.click(); }

    public void clickAirfields() { airfields.click(); }
}
