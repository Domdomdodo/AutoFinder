package finder.view;

import finder.Car;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;

import java.text.NumberFormat;
import java.util.Locale;

public class CarViewWrapper {


    private static Color UNSELECTED_FOUND = Color.LIGHTGREEN;
    private static Color SELECTED_FOUND = Color.GREEN;
    private static Color UNSELECTED_NOT_FOUND = Color.INDIANRED;
    private static Color SELECTED_NOT_FOUND = Color.DARKRED;
    //private static Color SELECTED_NEUTRAL = Color.BLUE;
    //private static Color UNSELECTED_NEUTRAL = Color.LIGHTBLUE;

    public Car car;
    public Pane carView;
    private Text name;
    private Text price;
    private Text plate;
    private Text model;
    public boolean selected = false;
    public boolean found = false;
    public String searchUrl = "";
    public String foundUrl = "";

    public CarViewWrapper(Car car){
        this.car = car;
        try {
            this.carView = FXMLLoader.load(getClass().getClassLoader().getResource("car.fxml"));

            name = (Text) carView.lookup("#car-name");
            price = (Text) carView.lookup("#car-price");
            model = (Text) carView.lookup("#car-model");
            plate = (Text) carView.lookup("#car-plate");


            NumberFormat currencyFormatter =
                    NumberFormat.getCurrencyInstance(new Locale("EN", "NL"));

            name.setText(formatString(car.getBrand() + " " + car.getModel(), 15));
            price.setText(currencyFormatter.format(car.getPrice()));
            model.setText(formatString(car.getType(), 10));
            plate.setText(car.getLicensePlate());


            updateBackGround();
            }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Pane getCarView(){
        return carView;
    }

    public static String formatString(String string, int maxLength){
        if (string.length() > maxLength){
            return string.substring(0,maxLength);
        } else {
            return string;
        }
    }

    public void toggleSelected() {
        selected = !selected;
        updateBackGround();
    }

    private void setBackGroundColor(Color color){
        carView.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void setFound(boolean found){
        this.found = found;
        updateBackGround();
    }

    public void updateBackGround(){
        if (selected){
            if (found){
                setBackGroundColor(SELECTED_FOUND);
            } else {
                setBackGroundColor(SELECTED_NOT_FOUND);
            }
        } else {
            if (found){
                setBackGroundColor(UNSELECTED_FOUND);
            } else {
                setBackGroundColor(UNSELECTED_NOT_FOUND);
            }
        }
    }
}
