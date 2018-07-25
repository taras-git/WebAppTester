package pages;

import baseclasses.BasePage;
import org.openqa.selenium.WebDriver;

/**
 * Created by taras on 7/25/18.
 */
public class BlogPage extends BasePage {

    public BlogPage(WebDriver driver) {
        super(driver);
    }

    public void verifyBlogPageDisplayed() {
        super.verifyPageDisplayed("news-en", "Blog");
    }
}
