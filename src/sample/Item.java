package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by ttt on 19.08.2017.
 */
public class Item extends TreeItem<String> {
    public File file;
    public TabPane windowForTextOutput;

    public Item(File file, TabPane scrollPane) {
        this.setValue(file.getName());
        this.file = file;
        windowForTextOutput = scrollPane;
        initializationTreeItem();
    }

    public String getName() {
        return file.getName();
    }


    public void initializationTreeItem() {
        TreeModificationEvent we = new TreeModificationEvent(MouseEvent.MOUSE_CLICKED, this);

        this.expandedProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {

                Tab tab = new Tab();
                tab.setText(getName());
                TextArea boxForText = new TextArea();
                StringBuilder sb = new StringBuilder();
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new FileReader(file));
                    String s;
//                        boxForText.setText("");
                    while ((s = in.readLine()) != null) {
//                            boxForText.setText(boxForText.getText()+s+"\n");
                        sb.append(s);
                        sb.append("\n");
                    }
                } catch (IOException e) {
                    System.out.println("ошибка при выводе файла на экран");
                } catch (OutOfMemoryError error) {
                    System.out.println("слишком большой файл");
                    sb = new StringBuilder();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка чтения");
                    alert.setHeaderText(null);
                    alert.setContentText("Слишком большой файл! \n Невозможно загрузить полностью. ");
                    alert.showAndWait();
                    try {
                        in = new BufferedReader(new FileReader(file));
                        char[] s = new char[100000];
//                        boxForText.setText("");
                        in.read(s,0,100000);
                        sb.append(s);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                boxForText.setText(sb.toString());
                tab.setContent(boxForText);
                windowForTextOutput.getTabs().add(tab);
            }
        });
    }
}
