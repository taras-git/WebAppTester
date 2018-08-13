package tests.api;

import io.restassured.http.ContentType;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.JsonReader;
import utils.HtmlUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;


/**
 * Created by taras on 7/30/18.
 */
public class RestApiTest {

    private static final Logger LOG = LoggerFactory.getLogger(RestApiTest.class);

    protected final String homePageEnProduction = JsonReader.getUrl("home_page_en_production");
    protected final String homePageDeProduction = JsonReader.getUrl("home_page_de_production");
    protected final String interaBasePageEn = JsonReader.getUrl("intera_base_page_en");
    protected final String interaBasePageDe = JsonReader.getUrl("intera_base_page_de");
    protected final String bookingPageEn = interaBasePageEn + "find_and_book_a_vehicle#/rental-data";
    protected final String findHotspotsPageEn = interaBasePageEn + "find_hotspots_and_stations";
    protected final String ratesPageEn = interaBasePageEn + "rates";
    protected final String contactPageEn = interaBasePageEn + "siteservice,contact";
    protected final String registrationPageEn = interaBasePageEn + "siteservice,register";
    protected final String loginPageEn = interaBasePageEn + "login";
    protected final String corporateMobilityPageEn = interaBasePageEn + "more,become_a_corporate_customer?open&TYPE=CORPORATE";
    protected final String bookingPageDe = interaBasePageDe + "fahrzeug_finden_und_buchen#/rental-data";
    protected final String findHotspotsPageDe = interaBasePageDe + "hotspots_und_stationen_finden";
    protected final String ratesPageDe = interaBasePageDe + "tarife";
    protected final String contactPageDe = interaBasePageDe + "siteservice,kontakt";
    protected final String registrationPageDe = interaBasePageDe + "siteservice,kunde_werden";
    protected final String loginPageDe = interaBasePageDe + "login";
    protected final String corporateMobilityPageDe = interaBasePageDe + "more,firmenkunde_werden?open&TYPE=CORPORATE";
    protected final String fleetPageEn = homePageEnProduction + "fleet";
    protected final String fleetPageDe = homePageDeProduction + "fahrzeugflotte";
    protected final String howItWorksPageEn = homePageEnProduction + "how-it-works";
    protected final String howItWorksPageDe = homePageDeProduction + "so-funktioniert-es";
    protected final String companyPageEn = homePageEnProduction + "company";
    protected final String companyPageDe = homePageDeProduction + "unternehmen";
    protected final String blogPageEn = homePageEnProduction + "category/news-en";
    protected final String blogPageDe = homePageDeProduction + "category/news";


    @DataProvider
    public Object[][] urls(){
        return new Object[][]{
                {homePageEnProduction},
                {homePageDeProduction},
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
        LOG.info(">>> Start new test for: <<<" + url + ">>>");
        try {
            siteIsUp(url);
            LOG.info("<<<" + url + ">>> is up!");
        } catch (Exception e) {
            LOG.error("Can't opening site: " + url + " :: " + e.getMessage());
            throw new RuntimeException("ERROR opening site: " + url + " :: " + e.getStackTrace());
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

    @Test
    public void verifyLinksOnPage(){
        Map<Boolean, List<String>> linksMap = null;

        try{
            Elements elementsWithHref = HtmlUtils.getElements(homePageEnProduction);

            linksMap = elementsWithHref
                    .stream()
                    .map(el -> el.attr("href"))  // get the "href" value of the element
                    .map(String::trim)  // trim the text
                    .filter(el -> el.contains("http"))  // get absolute links only
                    .distinct()// there could be duplicate links , so find unique
                    .collect(Collectors.partitioningBy(l -> HtmlUtils.getResponseCode(l) == 200)); // group the links based on the "200" response code

        } catch (Exception e) {
            LOG.info("Parsing exception: ", e);
        }

        if(!linksMap.get(false).isEmpty()){
            linksMap.get(false).forEach((v) -> {
                LOG.error("FOUND URL WITH CODE != 200 : " + v);
            });
        }
    }
}
