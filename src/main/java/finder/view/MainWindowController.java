package finder.view;

import finder.Car;
import finder.CarReader;
import finder.CarSearchTask;
import finder.controller.FileSelectionController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.UnaryOperator;

public class MainWindowController {

    private Parent mainWindow = null;
    private TextField selectedFileText;
    private Button selectFileButton;
    private VBox scrollVBox;
    private ScrollPane scrollPane;
    private ProgressIndicator progressIndicator;
    private Stage stage;
    private HashSet<CarViewWrapper> carViewWrappers = new HashSet<>();
    private CarSearchTask searchTask;
    private Thread searchThread;

    public MainWindowController(Stage stage){
        try {
            this.stage = stage;
            mainWindow = FXMLLoader.load(getClass().getResource("/window.fxml"));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void init(){
        Scene scene = mainWindow.getScene();

        selectedFileText = (TextField) scene.lookup("#selected-file-text");
        selectedFileText.setEditable(false);
        selectFileButton = (Button) scene.lookup("#choose-file-button");
        scrollVBox = (VBox) scene.lookup("#scroll-vbox");
        scrollPane = (ScrollPane) scene.lookup("#cars-scroll-pane");
        progressIndicator = (ProgressIndicator) scene.lookup("#search-progress");
        VBox.setVgrow(scrollVBox, Priority.ALWAYS);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);


        Button selectAllButton = (Button) scene.lookup("#select-all-button");
        Button unSelectAllButton = (Button) scene.lookup("#unselect-all-button");
        Button startSearchButton = (Button) scene.lookup("#search-cars-button");

        MainWindowController thisController = this;

        startSearchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                searchTask = new CarSearchTask(carViewWrappers, thisController);
                searchThread = new Thread(searchTask);
                searchThread.start();
            }
        });

        selectAllButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                selectAll();
            }
        });

        unSelectAllButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                unSelectAll();
            }
        });

        selectFileButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                FileSelectionController.chooseFile(stage, selectedFileText);
                updateCarsView(scrollVBox, scrollPane);
                scrollPane.setVmax(1000000);
                scrollVBox.requestLayout();
                scrollPane.requestLayout();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                checkSearchButton();
                            }
                        });
                    }
                    catch(Exception e){

                    }
                }
            }
        }).start();


        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String input = change.getText();
            if (input.matches("[0-9]*")) {
                return change;
            }
            return null;
        };

        TextField searchInterval = (TextField) scene.lookup("#search-interval");
        searchInterval.setTextFormatter(new TextFormatter<String>(integerFilter));


        updateSelection();
    }

    public Parent getMainWindow(){
        return mainWindow;
    }

    public void updateCarsView(VBox vbox, ScrollPane scrollPane){
        if (FileSelectionController.VALID_FILE_SELECTED) {
            vbox.getChildren().removeAll();
            carViewWrappers.clear();
            int height = 0;
            for (CarViewWrapper carViewWrapper : getCarViews(CarReader.fetchCars())) {
                vbox.getChildren().add(carViewWrapper.getCarView());
                height += carViewWrapper.getCarView().getPrefHeight();
            }
            scrollPane.setVmax(height);
        }
    }

    public HashSet<CarViewWrapper> getCarViews(HashSet<Car> cars){
        HashSet<CarViewWrapper> carViewWrappers = new HashSet<>();

        for (Car car :cars){
            CarViewWrapper carViewWrapper = new CarViewWrapper(car);

            carViewWrapper.carView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        carViewWrapper.toggleSelected();
                        updateSelection();
                    }
                }
            });

            this.carViewWrappers.add(carViewWrapper);
            carViewWrappers.add(carViewWrapper);
        }

        return carViewWrappers;
    }

    public void updateSelection(){
        HashSet<CarViewWrapper> selectedWrappers = new HashSet<>();
        for (CarViewWrapper carViewWrapper: this.carViewWrappers){
            if (carViewWrapper.selected) {
                selectedWrappers.add(carViewWrapper);
            }
            Text selectedCount = (Text) getMainWindow().lookup("#selected-count");
            selectedCount.setText(String.valueOf(selectedWrappers.size()));
        }

        Text name = (Text) stage.getScene().lookup("#selected-name");

        Text plate = (Text) stage.getScene().lookup("#selected-plate");
        Text brand = (Text) stage.getScene().lookup("#selected-brand");
        Text price = (Text) stage.getScene().lookup("#selected-price");
        Text year = (Text) stage.getScene().lookup("#selected-year");
        Text fuel = (Text) stage.getScene().lookup("#selected-fuel");
        Text doors = (Text) stage.getScene().lookup("#selected-doors");

        Button searchLink = (Button)stage.getScene().lookup("#open-search-button");

        if (selectedWrappers.size() == 1){

            NumberFormat currencyFormatter =
                    NumberFormat.getCurrencyInstance(new Locale("EN", "NL"));

            CarViewWrapper selectedWrapper  = selectedWrappers.iterator().next();
            Car selectedCar = selectedWrapper.car;
            name.setText(CarViewWrapper.formatString(selectedCar.getBrand() + " " + selectedCar.getModel(), 20));
            plate.setText(selectedCar.getLicensePlate());
            brand.setText(selectedCar.getBrand());
            price.setText((currencyFormatter.format(selectedCar.getPrice())));
            year.setText(String.valueOf(selectedCar.getConstructionYear()));
            fuel.setText(selectedCar.getFuelType().toString());
            doors.setText(String.valueOf(selectedCar.getDoors()));




            if (selectedWrapper.searchUrl != "") {
                searchLink.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            Desktop.getDesktop().browse(new URL(selectedWrapper.searchUrl).toURI());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                });
                searchLink.setDisable(false);
            } else {
                searchLink.setDisable(true);
            }

        } else {

            searchLink.setDisable(true);

            name.setText("Please select a single car");

            plate.setText("");
            brand.setText("");
            price.setText("");
            year.setText("");
            fuel.setText("");
            doors.setText("");
        }
    }

    public void selectAll(){
        for (CarViewWrapper carViewWrapper: carViewWrappers){
            if (!carViewWrapper.selected){
                carViewWrapper.toggleSelected();
            }
            updateSelection();
        }
    }

    public void unSelectAll(){
        for (CarViewWrapper carViewWrapper: carViewWrappers){
            if (carViewWrapper.selected){
                carViewWrapper.toggleSelected();
            }
            updateSelection();
        }
    }

    public synchronized void updateProgress(double progress){
        progressIndicator.setProgress(progress);
        progressIndicator.progressProperty().setValue(progress);
    }

    public synchronized int getSearchDelay(){
        TextField delay = (TextField)stage.getScene().lookup("#search-interval");
        return Integer.parseInt(delay.getText());
    }

    public synchronized void checkSearchButton(){
        if (searchTask != null){
            if (searchTask.searching){
                Button startSearchButton = (Button) stage.getScene().lookup("#search-cars-button");
                startSearchButton.setDisable(true);
                startSearchButton.setText("Searching...");
                return;
            }
        }
        Button startSearchButton = (Button) stage.getScene().lookup("#search-cars-button");
        startSearchButton.setDisable(false);
        startSearchButton.setText("Search for cars");
    }
}
