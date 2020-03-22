package finder;

public class SearchResult {

    private double price;
    private double kilometers;
    private String companyName;
    private String id;
    private String url;

    public SearchResult(double price, double kilometers, String companyName, String id, String url) {
        this.price = price;
        this.kilometers = kilometers;
        this.companyName = companyName;
        this.id = id;
        this.url = url;
    }
}
