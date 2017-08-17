package sample;

import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;


public class Controller {
    public Button buttonForOpenDirectoryChooser;
    public TextField folderPath;
    public File selectedDirectory;
    public TextField textSearch;
    public TextField fileNameExtension;

    public void openDirectoryChooser(Event event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        selectedDirectory = directoryChooser.showDialog(Main.temp);
        folderPath.setText(selectedDirectory.toString());
    }

    public void getStartSearch(Event event) throws Exception {
        if(fileNameExtension.getText().isEmpty()||selectedDirectory==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка ввода данных");
            alert.setHeaderText(null);
            alert.setContentText("Не указана начальная папка или не указано расширение файлов");
            alert.showAndWait();
            return;
        }

    }
}