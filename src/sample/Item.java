package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by ttt on 19.08.2017.
 */
public class Item extends Button {
    public File file;
    public TabPane windowForTextOutput;
    public Item(File file,TabPane scrollPane){
        this.setText(file.getName());
        this.file=file;
        windowForTextOutput=scrollPane;
        initializationButton();
    }

    public String getName(){
        return file.getName();
    }


    public void initializationButton(){
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Tab tab = new Tab();
                tab.setText(getName());
                TextArea boxForText = new TextArea();
                StringBuilder sb = new StringBuilder();
                try {
                    BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
                    try {
                        String s;
                        while ((s = in.readLine()) != null) {
                            sb.append(s);
                            sb.append("\n");
                        }
                    } finally {
                        //Также не забываем закрыть файл
                        in.close();
                    }
                } catch(IOException e) {
                    throw new RuntimeException(e);
                }
                boxForText.setText(sb.toString());
                tab.setContent(boxForText);
                windowForTextOutput.getTabs().add(tab);
            }
        });
    }


}
