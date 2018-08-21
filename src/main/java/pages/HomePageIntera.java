package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.JsonReader;

public class HomePageIntera extends HomePage {

    final String homePageInteraUrl = JsonReader.getUrl("booking_page_intera_en");

    @FindBy(xpath = "//a[contains(text(), 'Login')]")
    WebElement login;

    @FindBy(css = "#inputEmail3")
    WebElement loginEmail;

    @FindBy(css = "#inputPassword3")
    WebElement loginPassword;

    @FindBy(xpath = "(//button[@type='submit'])[1]")
    WebElement loginSubmit;


    public HomePageIntera(WebDriver driver) {
        super(driver);
    }

    public HomePageIntera start() {
        start(homePageInteraUrl);
        return this;
    }

    public HomePageIntera start(String url) {
        driver.get(url);
        return this;
    }

    public void login(String email, String password){
        login.click();
        loginEmail.sendKeys(email);
        loginPassword.sendKeys(password);
        loginSubmit.click();
    }

}
