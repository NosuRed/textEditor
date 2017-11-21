package sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class OpenFileMenu {
    private String textOutput;

    public void openFile(File getText){
        try {

            // file chooser opens the open dialog
            // Just shows the path in the console
            // the inputstream reads the file from the chosen path
            FileInputStream file = new FileInputStream(getText.getPath());
            System.out.println(file);
            //turning the text into bytes?
            byte[] data = new byte[file.available()];
            // "resets" the text area, other wise it could happen that, if there is text in the are already, it will just be added. We don't want tha
            // reads the data
            file.read(data);
            // closes the file
            file.close();
            //turning the byteData back into a String so that we can add it to the text area!
            this.textOutput = new String(data);

        }
        catch (IOException e){
            System.out.println(e.getMessage());


            // same reason as the saveBtn
        }catch (NullPointerException z){
            System.out.println(";) Caught you naughty fella! Seems like someone did not want to open a file! -> " + z);
        }
    }

    public String getTextOutput() {
        return textOutput;
    }
}
