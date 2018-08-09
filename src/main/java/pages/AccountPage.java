package pages;

import baseclasses.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by taras on 7/30/18.
 */
public class AccountPage extends BasePage {

    private static final Logger LOG = LoggerFactory.getLogger(AccountPage.class);

    private String bearbeitenXpath = "(//a[contains(text(), 'Bearbeiten')])[1]";

    @FindBy(xpath = "(//a[contains(text(), 'Bearbeiten')])[1]")
    private WebElement bearbeiten;

    @FindBy(xpath = "(//button[contains(text(), 'Speichern')])[1]")
    private WebElement speichern;

    @FindBy(css = "#regLand")
    private WebElement land;

    @FindBy(xpath = "//a[contains(text(), 'Logout')]")
    private WebElement logout;

    @FindBy(xpath = "//a[contains(text(), 'Bookings')]")
    private WebElement bookings;

    By reserved = By.xpath("//td[contains(text(), 'reserved')]");

    @FindBy(xpath = "//td[contains(text(), 'reserved')]/following-sibling::td/a")
    private WebElement reservedDetails;

    @FindBy(css = "a#btn-cancelbooking")
    private WebElement cancelBooking;

    @FindBy(css = "button#closemodal~a.btn.btn-danger")
    private WebElement cancelBookingOnAlertWindow;

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public AccountPage clickBearbeiten(){
        waitElementClickable(bearbeitenXpath, 10);
        bearbeiten.click();
        return this;
    }

    public AccountPage clickSpeichern(){
        speichern.click();
        return this;
    }

    public AccountPage changeCountryTo(String countryCode){
        Select dropdownCountry = new Select(driver.findElement(By.cssSelector("#regLand")));
        dropdownCountry.selectByValue(countryCode);
        return this;
    }

    public AccountPage verifyEditAccountDisplayed() {
        super.verifyPageDisplayed("editaccount?ReadForm&tg=Default", "Edit Account");
        return this;
    }

    public AccountPage verifyMeinKontoPageDisplayed() {
        super.verifyPageDisplayed("mein_konto", "Mein Konto");
        return this;
    }

    public AccountPage verifyMyAccountPageDisplayed() {
        super.verifyPageDisplayed("my_account", "My Account");
        return this;
    }

    public AccountPage verifyMyBookingsDisplayed() {
        super.verifyPageDisplayed("my_account,bookings", "My Bookings");
        return this;
    }

    public String getCurentUserCountry(){
        Select select = new Select(driver.findElement(By.cssSelector("#regLand")));
        WebElement option = select.getFirstSelectedOption();
        String selectedOption = option.getText();
        return selectedOption;
    }

    public AccountPage logoutCurrentUser(){
        logout.click();
        return this;
    }

    public AccountPage clickBookings() {
        bookings.click();
        return this;
    }

    public AccountPage verifyCarIsReserved() {
        waitElementDisplayed(reserved, 10);
        return this;
    }

    public void verifyNoCarsReserved() {
        try {
            waitElementDisplayed(reserved, 5);
        } catch (TimeoutException to) {
            LOG.info(">>> Booking is cancelled");
            return;
        }
        throw new RuntimeException(">>> BOOKING IS NOT CANCELLED! <<<");
    }

    public AccountPage clickDetails(){
        reservedDetails.click();
        return  this;
    }

    public AccountPage cancelBooking(){
        cancelBooking.click();
        cancelBookingOnAlertWindow.click();
        return this;
    }

}
