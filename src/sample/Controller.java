package sample;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;


public class Controller {
    public Button buttonForOpenDirectoryChooser;
    public TextField folderPath;
    public File selectedDirectory;
    public TextField textSearch;
    public TextField fileNameExtension;
    public Button startSearch;
    public ScrollPane displayForTree;
    public TabPane windowForTextOutput;
    public StackPane treePanel;


    public void openDirectoryChooser(Event event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        selectedDirectory = directoryChooser.showDialog(Main.temp);
        try {
            if (!selectedDirectory.toString().isEmpty())
                folderPath.setText(selectedDirectory.toString());
        } catch (Exception e) {
        }
    }

    public void getStartSearch(Event event) throws Exception {
        if (fileNameExtension.getText().isEmpty() || folderPath.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка ввода данных");
            alert.setHeaderText(null);
            alert.setContentText("Не указана начальная папка или не указано расширение файлов");
            alert.showAndWait();
            return;
        }
        startSearch.setDisable(true);
        buttonForOpenDirectoryChooser.setDisable(true);
        textSearch.setDisable(true);
        fileNameExtension.setDisable(true);

        Directory d = new Directory(new File(folderPath.getText()), fileNameExtension.getText(), textSearch.getText(), windowForTextOutput);
        d.fork();
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        alertInfo.setTitle("Поиск");
        alertInfo.setHeaderText(null);
        alertInfo.setContentText("Ожидайте завершения поиска!");
        alertInfo.showAndWait();
        d.join();
        TreeView<String> temp = new TreeView<String>(d.getDisplayTheTree());
        temp.setShowRoot(false);
        treePanel.getChildren().add(temp);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Поиск закончен");
        alert.setHeaderText(null);
        alert.setContentText("Поиск закончен найдено " + d.getNumberOfFilesFound() + " документов!");
        alert.showAndWait();

        startSearch.setDisable(false);
        buttonForOpenDirectoryChooser.setDisable(false);
        textSearch.setDisable(false);
        fileNameExtension.setDisable(false);
    }


}
