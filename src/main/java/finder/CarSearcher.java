package finder;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;

public class CarSearcher {

    private static final String BASE_URL = "https://www.autoscout24.nl/lst/";
    private static final String COMPANY_NAME = "Bockhoudt";

    public static Elements search(Car car){
        Document webPage = QueryHandler.getResultPage(buildSearchUrl(car));
        Elements elements = webPage.select("div.cldt-summary-full-item");
        //return elements;

        Elements candidates = new Elements();
        for (Element element: elements){
            String name = element.getElementsByAttributeValueMatching("data-test", "company-name").first().text();
            if (name.contains(COMPANY_NAME)){
                candidates.add(element);
            }
        }

        return candidates;
    }

    private static String buildSearchUrl(Car car){

        String searchUrl = BASE_URL;

        String sorting = "?sort=price";

        if (car.getBrand() != null || car.getBrand() != ""){
            searchUrl += car.getBrand();
        }

        searchUrl += sorting;

        if (car.getPrice() != 0){
            searchUrl += "&priceto=" + (int)car.getPrice() +"&pricefrom=" + (int)car.getPrice();
        }

        if (car.getConstructionYear() != 0){
            searchUrl += "&fregto=" + car.getConstructionYear() +"&fregfrom=" + car.getConstructionYear();
        }

        if (car.getKilometers() != 0){
            searchUrl += "&kmto=" + car.getKilometers() +"&kmfrom=" + car.getKilometers();
        }

        return searchUrl;
    }

}
