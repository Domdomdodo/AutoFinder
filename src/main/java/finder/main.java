package finder;

import finder.view.MainWindowController;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application{
    public static void main(String[] args) {
        launch(args);
/*

        String test = "https://www.autoscout24.nl/lst/?sort=standard&desc=0&custtype=D&ustate=N%2CU&size=20&page=1&lon=6.607875&lat=53.222134&zip=9723%20HM%20Groningen&zipr=1&cy=NL&priceto=9700&pricefrom=9700&atype=C&pic=true";

        HashSet<Car> cars = CarReader.fetchCars();

        for (Car car: cars) {
            Elements elements = CarSearcher.search(car);
            System.out.println("test");
        }

        System.out.println("test");

        Document dddd = QueryHandler.getResultPage("https://www.autoscout24.nl/lst/mini?sort=standard&desc=0&ustate=N%2CU&size=20&page=1&cy=NL&fregto=2011&fregfrom=2011&atype=C&");
        Elements elements = dddd.select("div.cldt-summary-full-item");
        System.out.println("dd");

 */
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Auto Finder");
        MainWindowController mainWindowController = new MainWindowController(primaryStage);
        Parent root = mainWindowController.getMainWindow();
        primaryStage.setScene(new Scene(root, 884, 532));
        primaryStage.show();

        mainWindowController.init();
    }






}
