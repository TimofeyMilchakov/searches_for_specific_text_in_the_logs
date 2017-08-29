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

        this.expandedProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                Tab tab = new Tab();
                tab.setText(getName());
                TextArea boxForText = new TextArea();
                StringBuilder sb = new StringBuilder();
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.US_ASCII));
                    try {
                        String s;
                        while ((s = in.readLine()) != null) {
                            sb.append(s);
                            sb.append("\n");
                        }
                    } finally {
                        in.close();
                    }
                } catch (IOException e) {
                    System.out.println("ошибка при выводе файла на экран");
                }
                boxForText.setText(sb.toString());
                tab.setContent(boxForText);
                windowForTextOutput.getTabs().add(tab);
            }
        });
    }
}
