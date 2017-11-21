package sample;

import java.io.IOException;
import java.nio.charset.Charset;

public class SaveFileMenu {
   private String savedTextOut;
    public void saveFile(String value){
        try{
            // Java encodes in UTF-16, so fist I turn the text into bytes and change the encoding to UTF-8
            byte[] byteText = value.getBytes(Charset.forName("UTF-8"));
            //Creating a string that takes the byteText and encodes it in UTF-8!
            // the text is write in to the text file
            this.savedTextOut = new String(byteText, "UTF-8");

        } catch (IOException ex) {
            System.out.println(ex.getMessage());

            // if someone decides to not to save and stop the process I catch the error.
            // a wall of red text is annoying! Does not influence anything except the console output
            // program runs normal after!
        }catch (NullPointerException es){
            System.out.println(";)Caught the naughty fella! Seems like someone did not want to save a file! -> "  + es);
        }
    }


    public String getSavedTextOut() {
        return savedTextOut;
    }
}
