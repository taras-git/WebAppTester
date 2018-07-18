package tests;

import baseclasses.BaseTestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by taras on 7/17/18.
 */
public class A2DTest extends BaseTestCase {

    @Test
    public void navigateToBookingPage() {
        driver.get(homePageEn);
        WebElement bookVehicle=driver.findElement(By.xpath("//nav[@id=\"top-navigation\"]" +
                "//*[contains(text(), 'Book Vehicle')]"));
        bookVehicle.click();

        if (!wait.until(ExpectedConditions.urlContains("find_and_book_a_vehicle")))
            throw new RuntimeException("booking page is not displayed");
    }
}
