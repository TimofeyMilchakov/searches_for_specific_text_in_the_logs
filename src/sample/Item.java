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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
                    BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
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
                    throw new RuntimeException(e);
                }
                boxForText.setText(sb.toString());
                tab.setContent(boxForText);
                windowForTextOutput.getTabs().add(tab);
            }
        });
    }
}
