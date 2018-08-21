package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

import static utils.Utils.DE;
import static utils.Utils.EN;

/**
 * Created by taras on 7/25/18.
 */
public class BlogPage extends BasePage {

    public BlogPage(WebDriver driver) {
        super(driver);
    }

    public void verifyBlogPageDisplayed() {

        switch (LANGUAGE) {
            case DE : {
                super.verifyPageDisplayed("news", "BlogDE");
                break;
            }
            case EN : {
                super.verifyPageDisplayed("news-en", "Blog");
                break;
            }
        }
    }
}
