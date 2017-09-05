package test;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyTest {
    @Test
    public void creatBigLog() throws IOException {
        File log = new File(".//src","testlog.log");
        if(log.exists()){
            log.delete();
            log = new File(".//src","testlog.log");
        }
        FileOutputStream out = new FileOutputStream(log,true);
        String text = "zxcvbnm,,.asdfghjkl;qwertyuiop[123456789asdfsdfgdhtjyjkgkgcvbcvnferror \n";
        byte[] buffer = text.getBytes();
        for(int i =0;i<10000000;i++){
            out.write(buffer);
        }
    }
}
