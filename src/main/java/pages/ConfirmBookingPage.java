package pages;

import baseclasses.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ConfirmBookingPage extends BasePage {

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement bookCar;

    public ConfirmBookingPage(WebDriver driver) {
        super(driver);
    }

    public ConfirmBookingPage bookCar(){
        bookCar.click();
        return this;
    }

    public void verifyCarBooked(){
        waitElementDisplayed(By.cssSelector("div.alert.alert-success"), 20);
    }

}
