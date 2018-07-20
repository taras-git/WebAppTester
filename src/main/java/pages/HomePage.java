package pages;

import baseclasses.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.JsonReader;

/**
 * Created by taras on 7/19/18.
 */
public class HomePage extends BasePage{

    protected String homePageEn = JsonReader.getPropertyFileValue("home_page_en");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage start() {
        driver.get(homePageEn);
        return this;
    }

    public void clickBookVehicle() {
        WebElement bookVehicle=driver.findElement(By.xpath("//nav[@id=\"top-navigation\"]" +
                "//*[contains(text(), 'Book Vehicle')]"));
        bookVehicle.click();
    }
}
