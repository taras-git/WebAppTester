package tests.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import io.restassured.http.ContentType;
import utils.JsonReader;


/**
 * Created by taras on 7/30/18.
 */
public class RestApiTest {

    private static final Logger LOG = LoggerFactory.getLogger(RestApiTest.class);

    protected final String homePageEn = JsonReader.getPropertyFileValue("home_page_en");
    protected final String homePageDe = JsonReader.getPropertyFileValue("home_page_de");

    protected final String interaBasePage = JsonReader.getPropertyFileValue("intera_base_page");
    protected final String bookingPageEn = interaBasePage + "find_and_book_a_vehicle#/rental-data";
    protected final String findHotspotsPageEn = interaBasePage + "find_hotspots_and_stations";
    protected final String ratesPageEn = interaBasePage + "rates";
    protected final String contactPageEn = interaBasePage + "siteservice,contact";
    protected final String registrationPageEn = interaBasePage + "siteservice,register";
    protected final String loginPageEn = interaBasePage + "login";
    protected final String corporateMobilityPageEn = interaBasePage + "more,become_a_corporate_customer?open&TYPE=CORPORATE";


    @DataProvider
    public Object[][] urls(){
        return new Object[][]{
                {homePageEn},
                {homePageDe},
                {bookingPageEn},
                {findHotspotsPageEn},
                {ratesPageEn},
                {contactPageEn},
                {registrationPageEn},
                {loginPageEn},
                {corporateMobilityPageEn}

        };
    }

    @Test(dataProvider = "urls")
    public void a2dSiteIsUp(String url) {
        try {
            siteIsUp(url);
        } catch (java.lang.NoSuchMethodError nsme) {
            LOG.info("ERROR opening site: " + url + " :: " + nsme.getMessage());
            throw new RuntimeException();
        }
    }

    private void siteIsUp(String url) {
        get(url)
                .then()
                .contentType(ContentType.HTML)
                .and()
                .assertThat()
                .statusCode(200);
    }
}