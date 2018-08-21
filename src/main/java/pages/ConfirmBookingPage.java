package pages;

import baseclasses.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.Timeouts.MIDDLE_TIMEOUT;

public class ConfirmBookingPage extends BasePage {

    By bookCarXpath = By.xpath("//button[@type='submit']");
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement bookCar;

    public ConfirmBookingPage(WebDriver driver) {
        super(driver);
    }

    public ConfirmBookingPage bookCar(){
        waitElementDisplayed(bookCarXpath, MIDDLE_TIMEOUT);
        bookCar.click();
        return this;
    }

    public void verifyCarBooked(){
        waitElementDisplayed(By.cssSelector("div.alert.alert-success"), MIDDLE_TIMEOUT);
    }

}
