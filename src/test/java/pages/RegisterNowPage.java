package pages;

import baseclasses.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.Timeouts.LONG_TIMEOUT;
import static utils.Utils.DE;
import static utils.Utils.EN;

public class RegisterNowPage extends BasePage {

    @FindBy(id = "regKunde")
    WebElement clientType;
    @FindBy(id = "regAnrede")
    WebElement clientSalutation;
    @FindBy(id = "regVorname")
    WebElement clientFirstname;
    @FindBy(id = "regNachname")
    WebElement clientLastname;
    @FindBy(id = "regEmail")
    WebElement clientEmail;
    @FindBy(id = "regEmail2")
    WebElement clientEmailRepeat;
    @FindBy(id = "regLand")
    WebElement clientLand;
    @FindBy(name = "agb")
    WebElement acceptTerms;
    @FindBy(css = "button.btn.btn-primary.btn-block.alwaysactive")
    WebElement btnRegister;

    By registerSuccess = By.cssSelector("div.alert.alert-success");

    private final String PRIVATE_CLIENT = "PRIVATE";
    private final String BUSINESS_CLIENT = "BUSINESS";
    private final String CORPORATE_CLIENT = "CORPORATE";

    public RegisterNowPage(WebDriver driver) {
        super(driver);
    }

    public RegisterNowPage verifyRegisterNowPageDisplayed() {
        switch (LANGUAGE) {
            case DE :
                super.verifyPageDisplayed("siteservice,kunde_werden", "RegisterNowDE");
                return this;
            case EN :
                super.verifyPageDisplayed("siteservice,register", "RegisterNowEN");
                return this;
        }
        return null;
    }

    private void setClientLand(String land){
        setSelect(clientLand, land);
    }

    public RegisterNowPage setLandDe(){
        setSelect(clientLand, "DE");
        return this;
    }

    private void setClientType(String type){
        setSelect(clientType, type);
    }

    public RegisterNowPage setPrivateClientType(){
        setClientType(PRIVATE_CLIENT);
        return this;
    }

    public RegisterNowPage setBusinessClientType(){
        setClientType(BUSINESS_CLIENT);
        return this;
    }

    public RegisterNowPage setCorporateClientType(){
        setClientType(CORPORATE_CLIENT);
        return this;
    }

    public RegisterNowPage setSalutationMr(){
        setSelect(clientSalutation, "MR");
        return this;
    }

    public RegisterNowPage setSalutationMs(){
        setSelect(clientSalutation, "MS");
        return this;
    }

    public RegisterNowPage setFirstName(){
        clientFirstname.sendKeys("John");
        return this;
    }

    public RegisterNowPage setLastName(){
        clientLastname.sendKeys("Doe");
        return this;
    }

    public RegisterNowPage setEmail(){
        clientEmail.sendKeys("john.doe@gmail.com");
        clientEmailRepeat.sendKeys("john.doe@gmail.com");
        return this;
    }

    public RegisterNowPage setAcceptTerms(){
        if ( !acceptTerms.isSelected() ) {
            clickOn(acceptTerms);
        }
        return this;
    }

    public RegisterNowPage clickRegister(){
        clickOn(btnRegister);
        return this;
    }

    public void verifyUserRegisterSuccess(){
        waitElementDisplayed(registerSuccess, LONG_TIMEOUT);
    }

}
