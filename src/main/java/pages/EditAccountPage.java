package pages;

import baseclasses.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;


/**
 * Created by taras on 7/30/18.
 */
public class EditAccountPage extends BasePage {

    private String bearbeitenXpath = "(//a[contains(text(), 'Bearbeiten')])[1]";

    @FindBy(xpath = "(//a[contains(text(), 'Bearbeiten')])[1]")
    private WebElement bearbeiten;

    @FindBy(xpath = "(//button[contains(text(), 'Speichern')])[1]")
    private WebElement speichern;

    @FindBy(css = "#regLand")
    private WebElement land;

    @FindBy(xpath = "//a[contains(text(), 'Logout')]")
    private WebElement logout;

    public EditAccountPage(WebDriver driver) {
        super(driver);
    }

    public EditAccountPage clickBearbeiten(){
        waitElementClickable(bearbeitenXpath, 10);
        bearbeiten.click();
        return this;
    }

    public EditAccountPage clickSpeichern(){
        speichern.click();
        return this;
    }

    public EditAccountPage changeCountryTo(String countryCode){
        Select dropdownCountry = new Select(driver.findElement(By.cssSelector("#regLand")));
        dropdownCountry.selectByValue(countryCode);
        return this;
    }

    public EditAccountPage verifyEditAccountPageDisplayed() {
        super.verifyPageDisplayed("editaccount?ReadForm&tg=Default", "Edit Account");
        return this;
    }

    public EditAccountPage verifyMeinKontoPageDisplayed() {
        super.verifyPageDisplayed("mein_konto", "Mein Konto");
        return this;
    }

    public String getCurentUserCountry(){
        Select select = new Select(driver.findElement(By.cssSelector("#regLand")));
        WebElement option = select.getFirstSelectedOption();
        String selectedOption = option.getText();
        return selectedOption;
    }

    public EditAccountPage logoutCurrentUser(){
        logout.click();
        return this;
    }
}
