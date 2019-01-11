package pages;

import baseclasses.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.Timeouts.SHORT_TIMEOUT;

public class ConfirmPaymentPage extends BasePage {

    By bookCarXpath = By.xpath("//button[@type='submit']");
    @FindBy(xpath = "//input[@name='submit']")
    private WebElement bookCar;

    @FindBy(css = "div.summary__row div.value")
    private WebElement totalPayment;

    public ConfirmPaymentPage(WebDriver driver) {
        super(driver);
    }

    public boolean isWithoutCents(){
        scrollTo(totalPayment);
        return totalPayment.getText().endsWith(".00");
    }

    public ConfirmPaymentPage confirmCarBooked(){
        scrollTo(bookCar);
        waitElementClickable(bookCar, SHORT_TIMEOUT);
        clickOn(bookCar);
        return this;
    }

}
