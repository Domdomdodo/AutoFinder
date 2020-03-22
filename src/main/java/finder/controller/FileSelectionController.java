package finder.controller;

import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

import java.io.File;



public class FileSelectionController {

    public static File SELECTED_FILE = null;
    public static boolean VALID_FILE_SELECTED = false;

    public static void chooseFile(Stage stage, TextField selectedFileText){
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooser.setTitle("Open Resource File");
        SELECTED_FILE = fileChooser.showOpenDialog(stage);
        if (SELECTED_FILE != null) {
            if (SELECTED_FILE.isFile() && FilenameUtils.getExtension(SELECTED_FILE.getAbsolutePath()).equals("xlsx")) {
                selectedFileText.setText(SELECTED_FILE.getName());
                VALID_FILE_SELECTED = true;
            } else {
                selectedFileText.setText("Invalid file!");
                VALID_FILE_SELECTED = false;
            }
        } else {
            selectedFileText.setText("No file selected");
            VALID_FILE_SELECTED = false;
        }

    }

}
