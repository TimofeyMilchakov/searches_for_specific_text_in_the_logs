package sample;

import javafx.beans.binding.StringBinding;

import java.io.*;
import java.util.LinkedList;

/**
 * Created by ttt on 19.08.2017.
 */
public class Directory extends Thread implements Runnable {
    private File thisFile;
    private String mask;
    private String textSearch;
    public LinkedList<Item> subdocuments;
    public LinkedList<Directory> subfolders;

    Directory(File file, String mask, String textSearch) {
        thisFile = file;
        this.mask=mask;
        this.textSearch=textSearch;
        subdocuments = new LinkedList<>();
        subfolders = new LinkedList<>();
    }

    private boolean checkTextFile(File file, String textSearch) {
        try{
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            String text;
            while ((text=in.readLine())!=null){
                if(text.matches(".*"+textSearch+".*"))
                    return true;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void run() {
//        absolutePath = thisFile.getAbsolutePath();
        File[] files = thisFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().matches("(.*)\\." + mask) && !files[i].isDirectory()) {
                if(textSearch.isEmpty()||checkTextFile(files[i],textSearch))
                    subdocuments.addLast(new Item(files[i]));
            }
            if(files[i].isDirectory()&&files[i].list().length!=0){
                Directory tempDirectory = new Directory(files[i],this.mask,this.textSearch);
                subfolders.add(tempDirectory);
                tempDirectory.start();

            }
        }
        LinkedList<Directory> deleteDirectory = new LinkedList<>();
        for (Directory directory:subfolders){
            try {
                directory.join();
                if(directory.subfolders.size()==0&&directory.subdocuments.size()==0)
                    deleteDirectory.add(directory);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        subfolders.removeAll(deleteDirectory);
    }

}
