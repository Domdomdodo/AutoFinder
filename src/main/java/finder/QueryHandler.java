package finder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class QueryHandler {

    private static String AUTOSCOUT_URL = "https://www.autoscout24.nl/lst/?";

    public static Document getResultPage(String url) {

        Document result = null;

        try {
            result = Jsoup.connect(url).get();
            Elements newsHeadlines = result.select("#mp-itn b a");
            for (Element headline : newsHeadlines) {

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

}
