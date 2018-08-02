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

    protected final String homePageEn = JsonReader.getString("home_page_en");
    protected final String homePageDe = JsonReader.getString("home_page_de");
    protected final String interaBasePage = JsonReader.getString("intera_base_page");
    protected final String interaBasePageDe = JsonReader.getString("intera_base_page_de");
    protected final String bookingPageEn = interaBasePage + "find_and_book_a_vehicle#/rental-data";
    protected final String findHotspotsPageEn = interaBasePage + "find_hotspots_and_stations";
    protected final String ratesPageEn = interaBasePage + "rates";
    protected final String contactPageEn = interaBasePage + "siteservice,contact";
    protected final String registrationPageEn = interaBasePage + "siteservice,register";
    protected final String loginPageEn = interaBasePage + "login";
    protected final String corporateMobilityPageEn = interaBasePage + "more,become_a_corporate_customer?open&TYPE=CORPORATE";
    protected final String bookingPageDe = interaBasePageDe + "fahrzeug_finden_und_buchen#/rental-data";
    protected final String findHotspotsPageDe = interaBasePageDe + "hotspots_und_stationen_finden";
    protected final String ratesPageDe = interaBasePageDe + "tarife";
    protected final String contactPageDe = interaBasePageDe + "siteservice,kontakt";
    protected final String registrationPageDe = interaBasePageDe + "siteservice,kunde_werden";
    protected final String loginPageDe = interaBasePageDe + "login";
    protected final String corporateMobilityPageDe = interaBasePageDe + "more,firmenkunde_werden?open&TYPE=CORPORATE";
    protected final String fleetPageEn = homePageEn + "fleet";
    protected final String fleetPageDe = homePageDe + "fahrzeugflotte";
    protected final String howItWorksPageEn = homePageEn + "how-it-works";
    protected final String howItWorksPageDe = homePageDe + "so-funktioniert-es";
    protected final String companyPageEn = homePageEn + "company";
    protected final String companyPageDe = homePageDe + "unternehmen";
    protected final String blogPageEn = homePageEn + "category/news-en";
    protected final String blogPageDe = homePageDe + "category/news";





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
                {corporateMobilityPageEn},
                {bookingPageDe},
                {findHotspotsPageDe},
                {ratesPageDe},
                {contactPageDe},
                {registrationPageDe},
                {loginPageDe},
                {corporateMobilityPageDe},
                {fleetPageEn},
                {fleetPageDe},
                {howItWorksPageEn},
                {howItWorksPageDe},
                {companyPageEn},
                {companyPageDe},
                {blogPageEn},
                {blogPageDe}

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