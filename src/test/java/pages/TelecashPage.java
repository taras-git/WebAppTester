package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.Timeouts.SHORTEST_TIMEOUT;
import static utils.Timeouts.SHORT_TIMEOUT;

public class TelecashPage extends BasePage {

    @FindBy(xpath = "(//img[@alt='VISA'])[1]")
    WebElement cardVisa;
    @FindBy(id = "cardNumber")
    WebElement cardNumber;
    @FindBy(id = "cardholderName")
    WebElement cardholderName;
    @FindBy(id = "expiryMonth")
    WebElement expiryMonth;
    @FindBy(id = "expiryYear")
    WebElement expiryYear;
    @FindBy(id = "cardCode_masked")
    WebElement cardCode;
    @FindBy(id = "nextBtn")
    WebElement nextButton;

    public TelecashPage(WebDriver driver) { super(driver); }

    public void verifyTelecashPageDisplayed(){
        verifyPageDisplayed("test.ipg-online.com", "Telecash Page");
    }

    public void makePayment(){
        clickOn(cardVisa);
        waitElementClickable(cardNumber, SHORT_TIMEOUT);
        sendTextTo(cardNumber, "4921 8180 8989 8988");
        sendTextTo(cardholderName, "Tester");
        setSelect(expiryMonth, "12");
        setSelect(expiryYear, "2020");
        sendTextTo(cardCode, "123");
        clickOn(nextButton);
    }
}
