package tests.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import org.json.JSONArray;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HtmlUtils;
import utils.JsonReader;
import utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;
import static java.util.Arrays.asList;


/**
 * Created by taras on 7/30/18.
 */
public class RestApiTestHelper {

    private static final Logger LOG = LoggerFactory.getLogger(RestApiTestHelper.class);

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

    protected final List<String> pageNames = asList(
            homePageEnProduction,
            homePageDeProduction,
            bookingPageEn,
            findHotspotsPageEn,
            ratesPageEn,
            contactPageEn,
            registrationPageEn,
            loginPageEn,
            corporateMobilityPageEn,
            bookingPageDe,
            findHotspotsPageDe,
            ratesPageDe,
            contactPageDe,
            registrationPageDe,
            loginPageDe,
            corporateMobilityPageDe,
            fleetPageEn,
            fleetPageDe,
            howItWorksPageEn,
            howItWorksPageDe,
            companyPageEn,
            companyPageDe,
            blogPageEn,
            blogPageDe
    );

    private final String ENVIRONMENT = Utils.getRestApiTestEnvironment();

    Object[][] getDataProviderFromList(List<?> list) {
        return list.stream()
                .map(element -> new Object[] { element } )
                .toArray(Object[][]::new);
    }

    void siteIsUp(String url) {
        get(url)
        .then()
            .contentType(ContentType.HTML)
        .and()
        .assertThat()
            .statusCode(200);
    }

    Map<String, Object> getPostBody(JsonObject jsonObject) {
        // create a POST body
        Map<String, Object> jsonBody = new HashMap<>();
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();

        for(Map.Entry<String,JsonElement> entry : entrySet){
            jsonBody.put(entry.getKey(), jsonObject.get(entry.getKey()));
            LOG.info("API CALL POST BODY: " +entry.getKey()+ " : " + jsonObject.get(entry.getKey()));
        }
        return jsonBody;
    }

    String getPostEndpoint(JsonObject jsonObject) {
        String endpoint = ENVIRONMENT + jsonObject.get("endpoint").getAsString();
        LOG.info("POST API CALL ENDPOINT: " + endpoint);
        return endpoint;
    }

    String getGetEndpoint(String url) {
        String endpoint = ENVIRONMENT + url;
        LOG.info("GET API CALL ENDPOINT: " + endpoint);
        return endpoint;
    }

    Set<String> findDuplicates(List<String> listDuplicates) {
        final Set<String> setDuplicates = new HashSet<>();
        final Set<String> setUniqueValues = new HashSet<>();

        for (String s : listDuplicates) {
            if(!s.isEmpty()) {
                if (!setUniqueValues.add(s)) {
                    setDuplicates.add(s);
                }
            }
        }
        return setDuplicates;
    }

    Map<Boolean, List<String>> collectHttpResponses(Elements elementsWithHref) {
        Map<Boolean, List<String>> linksMap;
        linksMap = elementsWithHref
                .stream()
                .map(el -> el.attr("href"))  // get the "href" value of the element
                .map(String::trim)  // trim the text
                .filter(el -> el.contains("http"))  // get absolute links only
                .distinct()// there could be duplicate links , so find unique
                .collect(Collectors.partitioningBy(l -> HtmlUtils.getResponseCode(l) == 200)); // group the links based on the "200" response code
        return linksMap;
    }

    List<String> getLocationIdList(JSONArray locationsArray) {
        // populate locationsIdsList with actual ids
        List<String> locationsIdsList = new ArrayList<>();

        for(int i = 0; i < locationsArray.length(); i++) {
            String id = locationsArray
                    .getJSONObject(i)
                    .get("id")
                    .toString();
            locationsIdsList.add(id);
        }

        return locationsIdsList;
    }
}
