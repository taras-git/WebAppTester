package pages;

import baseclasses.BasePage;
import exceptions.PropertyMisconfigureException;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static utils.Utils.DE;
import static utils.Utils.EN;

/**
 * Created by taras on 7/30/18.
 */
public class AccountPage extends BasePage {

    private static final Logger LOG = LoggerFactory.getLogger(AccountPage.class);

    private String editAccountXpath = "(//a[contains(@href,'editaccount')])[1]";
    @FindBy(xpath = "(//a[contains(@href,'editaccount')])[1]")
    private WebElement editAccount;

    @FindBy(xpath = "(//button[@class='btn btn-primary'])[1]")
    private WebElement submitAccountChanges;

    @FindBy(css = "#regLand")
    private WebElement land;

    @FindBy(xpath = "//a[contains(text(), 'Logout')]")
    private WebElement logout;

    @FindBy(xpath = "//a[contains(text(), 'Bookings')]")
    private WebElement bookingsEn;

    @FindBy(xpath = "//a[contains(text(), 'Buchungen')]")
    private WebElement bookingsDe;


    By reservedEn = By.xpath("//td[contains(text(), 'reserved')]");
    By reservedDe = By.xpath("//td[contains(text(), 'Reserviert')]");

    @FindBy(xpath = "//td[contains(text(), 'reserved')]/following-sibling::td/a")
    private WebElement reservedDetailsEn;

    @FindBy(xpath = "//td[contains(text(), 'Reserviert')]/following-sibling::td/a")
    private WebElement reservedDetailsDe;

    @FindBy(css = "a#btn-cancelbooking")
    private WebElement cancelBooking;

    @FindBy(css = "button#closemodal~a.btn.btn-danger")
    private WebElement cancelBookingOnAlertWindow;

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public AccountPage clickEditAccount(){
        waitElementClickable(editAccountXpath, 10);
        editAccount.click();
        return this;
    }

    public AccountPage submitAccountChanges(){
        submitAccountChanges.click();
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

    public AccountPage verifyMyAccountPageDisplayed() {
        switch (LANGUAGE){
            case DE : {
                super.verifyPageDisplayed("mein_konto", "Mein Konto");
                return this;
            }
            case EN : {
                super.verifyPageDisplayed("my_account", "My Account");
                return this;
            }
        }
        throw new PropertyMisconfigureException();
    }

    public AccountPage verifyMyBookingsDisplayed() {
        switch (LANGUAGE) {
            case DE : {
                super.verifyPageDisplayed("mein_konto,buchungen", "My Buchungen");
                return this;
            }
            case EN : {
                super.verifyPageDisplayed("my_account,bookings", "My Bookings");
                return this;
            }
        }
        throw new PropertyMisconfigureException();
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
        switch (LANGUAGE){
            case EN : {
                bookingsEn.click();
                return this;
            }
            case DE : {
                bookingsDe.click();
                return this;
            }
        }
        throw new PropertyMisconfigureException();
    }

    public AccountPage verifyCarIsReserved() {
        switch (LANGUAGE){
            case DE: {
                waitElementDisplayed(reservedDe, 10);
                break;
            }
            case EN: {
                waitElementDisplayed(reservedEn, 10);
                break;
            }
        }
        return this;
    }

    public void verifyNoCarsReserved() {
        switch (LANGUAGE){
            case DE : verifyNoCarsReservedDe();
            case EN : verifyNoCarsReservedEn();
        }
    }

    public void verifyNoCarsReservedEn() {
        try {
            waitElementDisplayed(reservedEn, 5);
        } catch (TimeoutException to) {
            LOG.info(">>> Booking is cancelled");
            return;
        }
        throw new RuntimeException(">>> BOOKING IS NOT CANCELLED! <<<");
    }

    public void verifyNoCarsReservedDe() {
        try {
            waitElementDisplayed(reservedDe, 5);
        } catch (TimeoutException to) {
            LOG.info(">>> Booking is cancelled");
            return;
        }
        throw new RuntimeException(">>> BOOKING IS NOT CANCELLED! <<<");
    }

    public AccountPage clickDetails(){
        switch (LANGUAGE) {
            case EN : {
                reservedDetailsEn.click();
                break;
            }
            case DE : {
                reservedDetailsDe.click();
                break;
            }
        }
        return this;
    }

    public AccountPage cancelBooking(){
        cancelBooking.click();
        cancelBookingOnAlertWindow.click();
        return this;
    }

}
