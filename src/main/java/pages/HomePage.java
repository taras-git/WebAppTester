package pages;

import baseclasses.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by taras on 7/19/18.
 */
public class HomePage extends BasePage{

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public BookingPage openBooking() {
        WebElement bookVehicle=driver.findElement(By.xpath("//nav[@id=\"top-navigation\"]" +
                "//*[contains(text(), 'Book Vehicle')]"));
        bookVehicle.click();
        return new BookingPage(this.driver);
    }
}
