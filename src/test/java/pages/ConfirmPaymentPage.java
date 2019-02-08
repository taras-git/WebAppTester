package pages;

import baseclasses.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static utils.Timeouts.SHORTEST_TIMEOUT;
import static utils.Timeouts.SHORT_TIMEOUT;
import static utils.Utils.sleep;

public class ConfirmPaymentPage extends BasePage {

    private static final Logger LOG = LoggerFactory.getLogger(ConfirmPaymentPage.class);

    By bookCarXpath = By.xpath("//button[@type='submit']");
    @FindBy(xpath = "//input[@name='submit']")
    private WebElement bookCar;

    By totalPaymentBy = By.cssSelector("div.summary__row div.value");
    By sum = By.xpath("//div[@class='summary__row']//div[@class='value' and contains(text(), '€')]");

    @FindBy(css = "div.summary__row div.value")
    private WebElement totalPayment;

    public ConfirmPaymentPage(WebDriver driver) {
        super(driver);
    }

    public boolean isWithoutCents(){
        sleep(SHORTEST_TIMEOUT);
        scrollTo(bookCar);
        LOG.info("Total Payment1: {}", totalPayment.getText());
        return totalPayment.getText().endsWith(".00");
    }

    public boolean confirmCarBooked(){
        scrollTo(bookCar);
        sleep(SHORTEST_TIMEOUT);
        waitElementClickable(bookCar, SHORT_TIMEOUT);

        String totalSum = totalPayment.getText();
        LOG.info("Total Payment2: {}", totalSum);

        clickOn(bookCar);
        return totalSum.endsWith(".00");
    }

}
