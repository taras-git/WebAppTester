package pages;

import baseclasses.BasePage;
import exceptions.PropertyMisconfigureException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static utils.Timeouts.LONG_TIMEOUT;
import static utils.Timeouts.SHORT_TIMEOUT;
import static utils.Utils.DE;
import static utils.Utils.EN;

/**
 * Created by taras on 7/30/18.
 */
public class AccountPage extends BasePage {

    private static final Logger LOG = LoggerFactory.getLogger(AccountPage.class);

    private By carBookingStatus = By.cssSelector("tbody > tr > td:nth-child(7)");

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
    By loadedEn = By.xpath("(//td[contains(text(), 'loaded')])[1]");
    By loadedDe = By.xpath("(//td[contains(text(), 'Geladen')])[1]");

    @FindBy(xpath = "//td[contains(text(), 'reserved')]/following-sibling::td/a")
    private WebElement reservedDetailsEn;

    @FindBy(xpath = "//td[contains(text(), 'Reserviert')]/following-sibling::td/a")
    private WebElement reservedDetailsDe;

    @FindBy(xpath = "(//td[contains(text(), 'Geladen')]/following-sibling::td/a)[2]")
    private WebElement loadedDetailsDe;

    @FindBy(xpath = "(//td[contains(text(), 'loaded')]/following-sibling::td/a)[2]")
    private WebElement loadedDetailsEn;

    @FindBy(css = "a#btn-cancelbooking")
    private WebElement cancelBooking;

    @FindBy(css = "button#closemodal~a.btn.btn-danger")
    private WebElement cancelBookingOnAlertWindow;

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public AccountPage clickEditAccount(){
        waitElementClickable(editAccountXpath, SHORT_TIMEOUT);
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
                waitElementDisplayed(reservedDe, LONG_TIMEOUT);
                break;
            }
            case EN: {
                waitElementDisplayed(reservedEn, LONG_TIMEOUT);
                break;
            }
        }
        return this;
    }

    public void verifyNoCarsLoaded() {
        if (isFirstCarLoaded()) {
            throw new RuntimeException(">>> BOOKING IS NOT CANCELLED! <<<");
        } else {
            LOG.info(">>> Booking is cancelled");
        }
    }

    private boolean isFirstCarLoaded(){
        String firstCarStatus = getFirstCarStatus();

        if(firstCarStatus.equalsIgnoreCase("loaded")
            ||firstCarStatus.equalsIgnoreCase("Geladen")){
            return true;
        }
        return false;
    }

    private boolean isFirstCarReserved(){
        String firstCarStatus = getFirstCarStatus();

        if(firstCarStatus.equalsIgnoreCase("reserved")
                ||firstCarStatus.equalsIgnoreCase("Reserviert")){
            return true;
        }
        return false;
    }

    private String getFirstCarStatus() {
        List<WebElement> carStatusList = getElements(carBookingStatus);
        return carStatusList.get(0).getText();
    }

    public void verifyNoCarsReserved() {
        if (isFirstCarReserved()) {
            throw new RuntimeException(">>> BOOKING IS NOT CANCELLED! <<<");
        } else {
            LOG.info(">>> Booking is cancelled");
        }
    }


    public AccountPage clickReservedDetails(){
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

    public AccountPage clickLoadedDetails(){
        switch (LANGUAGE) {
            case EN : {
                loadedDetailsEn.click();
                break;
            }
            case DE : {
                loadedDetailsDe.click();
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

    public AccountPage verifyCarIsLoaded() {
        switch (LANGUAGE){
            case DE: {
                waitElementDisplayed(loadedDe, SHORT_TIMEOUT);
                break;
            }
            case EN: {
                waitElementDisplayed(loadedEn, SHORT_TIMEOUT);
                break;
            }
        }
        return this;
    }
}
