package sample;

import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.LinkedList;
import java.util.concurrent.RecursiveAction;

/**
 * Created by ttt on 19.08.2017.
 */
public class Directory extends RecursiveAction {
    public LinkedList<Item> subdocuments;
    public LinkedList<Directory> subfolders;
    private File thisFile;
    private String mask;
    private String textSearch;
    private TabPane windowForTextOutput;


    Directory(File file, String mask, String textSearch, TabPane scrollPane) {
        thisFile = file;
        this.mask = mask;
        this.textSearch = textSearch;
        subdocuments = new LinkedList<>();
        subfolders = new LinkedList<>();
        windowForTextOutput = scrollPane;
    }

    public String getNameDirectory() {
        return thisFile.getName();
    }

    public int getNumberOfFilesFound() {
        int sum = 0;
        for (int i = 0; subfolders.size() > i; i++) {
            sum = sum + subfolders.get(i).getNumberOfFilesFound();
        }
        sum = sum + subdocuments.size();
        return sum;
    }

    private boolean checkTextFile(File file, String textSearch) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            String text;
            while ((text = in.readLine()) != null) {
                if (text.matches(".*" + textSearch + ".*"))
                    return true;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return false;
        }

    }

    @Override
    public void compute() {
//        absolutePath = thisFile.getAbsolutePath();
        File[] files = thisFile.listFiles();

        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().matches("(.*)\\." + mask) && !files[i].isDirectory()) {
                if (textSearch.isEmpty() || checkTextFile(files[i], textSearch))
                    subdocuments.addLast(new Item(files[i], windowForTextOutput));
            }
            try {
                if (files[i].isDirectory() && files[i].list().length != 0) {
                    Directory tempDirectory = new Directory(files[i], this.mask, this.textSearch, windowForTextOutput);
                    subfolders.add(tempDirectory);
                    tempDirectory.fork();

                }
            } catch (Exception e) {
                System.out.println("ошибка при работе с " + files[i].toString());

            }
        }
        LinkedList<Directory> deleteDirectory = new LinkedList<>();
        for (Directory directory : subfolders) {
            directory.join();
            if (directory.subfolders.size() == 0 && directory.subdocuments.size() == 0)
                deleteDirectory.add(directory);
        }
        subfolders.removeAll(deleteDirectory);
    }

    public TreeItem<String> getDisplayTheTree(){
        TreeItem<String> nameDirectory = new TreeItem<>(getNameDirectory());
        for(Directory directory:subfolders){
            nameDirectory.getChildren().add(directory.getDisplayTheTree());
        }
        for(Item item:subdocuments){
            nameDirectory.getChildren().add(item);
        }
        return nameDirectory;

    }

//    public VBox getDisplayTheTree() {
//        VBox box = new VBox();
//        TextField nameDirectory = new TextField(this.getNameDirectory());
//        nameDirectory.setDisable(true);
//        box.getChildren().add(nameDirectory);
//        for (int i = 0; subfolders.size() > i; i++) {
//            VBox tempBox = subfolders.get(i).getDisplayTheTree();
//            HBox tempHBox = new HBox();
//            ImageView image = new ImageView("sample/Point.png");
//            image.setFitHeight(25);
//            image.setFitWidth(25);
//            tempHBox.getChildren().add(image);
//            tempHBox.getChildren().add(tempBox);
////            tempBox.setLayoutX(50);
//            box.getChildren().add(tempHBox);
//        }
//        for (int i = 0; i < subdocuments.size(); i++) {
//            HBox tempHBox = new HBox();
//            ImageView image = new ImageView("sample/Point.png");
//            image.setFitHeight(25);
//            image.setFitWidth(25);
//            tempHBox.getChildren().add(image);
//            tempHBox.getChildren().add(subdocuments.get(i));
////            subdocuments.get(i).setLayoutY(50);
//            box.getChildren().add(tempHBox);
//        }
//        return box;
//    }
}
