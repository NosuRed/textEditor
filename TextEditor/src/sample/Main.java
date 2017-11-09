package sample;

import java.util.Arrays;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;



public class Main extends Application {
    private String value;
    @Override
    public void start(Stage primaryStage) throws Exception{

       // creating a file chooser that filters out everything that is not a .txt file.
        // makes opening and saving the wrong file type harder I guess
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter exFilter = new FileChooser.ExtensionFilter("TXT files",("*.txt"),"*txt");
        fileChooser.getExtensionFilters().addAll(exFilter);


        //creating separators
        Separator sep1 = new Separator();
        sep1.setOrientation(Orientation.VERTICAL);
        Separator sep2 = new Separator();
        sep2.setOrientation(Orientation.VERTICAL);
        Separator sep3 = new Separator();
        sep3.setOrientation(Orientation.VERTICAL);

        // creating the root layout
        VBox root = new VBox();

        // creating the Hbox for the Button and Label
        HBox buttonBox = new HBox();

        // creating the cut button
        Button cutBtn = new Button();
        cutBtn.setPrefWidth(75);
        cutBtn.setText("Cut ");

        Button reverseBtn = new Button("Magic!");
        reverseBtn.setPrefWidth(75);
        reverseBtn.setDisable(true);



        // creating the counter labels. These display the Characters and Words typed!
        Label charCount = new Label();
        Label wordCount = new Label();
        Label noWhitespaceCount = new Label();
        charCount.setText("Characters: ");
        wordCount.setText("Words: ");
        noWhitespaceCount.setText("Characters without white space: ");
        charCount.setPadding(new Insets(0,25,0,0));
        wordCount.setPadding(new Insets(0,25,0,0));
        noWhitespaceCount.setPadding(new Insets(0,25,0,0));

        // creating a pane that always grows together with the Hbox
        // this pushes the labels to the right
        final Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);

        // adding all children to the hbox
        buttonBox.getChildren().addAll(cutBtn, sep3 ,reverseBtn, spacer, charCount, sep1, noWhitespaceCount, sep2, wordCount);

        //creating a text area that grows together with the vbox
        TextArea textArea = new TextArea();
        VBox.setVgrow(textArea, Priority.ALWAYS);
        textArea.setWrapText(true);

        // creating the menus that will be added to the menu bar!
        Menu fileMenu = new Menu("File");
        // creating the menu items that will be displayed when the "file" menu is opened
        MenuItem saveBtn = new MenuItem("Save");
        MenuItem openBtn = new MenuItem("Open");
        MenuItem clearBtn = new Menu("Clear");
        //adding all the items to the file menu
        fileMenu.getItems().addAll(saveBtn, openBtn, clearBtn);


        // creating the menu Bar, the file menu is added to the bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu);


        // adding all the children to my parent(root)
        root.getChildren().addAll(menuBar, buttonBox ,textArea);



        primaryStage.setTitle("Text editor");
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.setMinHeight(275);
        primaryStage.setMinWidth(300);
        primaryStage.show();


        // adding a listener for the text are. This way it is able react to changes in the text are immediately
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                // creates a String array "parts" that gets words
                //Everything that is not a part of a word, that is not present in the alphabet or a number, is not counted as a word
                // example = " ", ",", "!", "!" these will not count as words if they stand alone but they are used to split!
                String[] parts = textArea.getText().split("\\W+");
                // this will run whenever text is changed

                value = textArea.getText().replaceAll("\n", System.getProperty("line.separator"));
                String charCounterWithoutWhitespace = textArea.getText().replace(" ", "");

                // Just counts characters, sorry white space, you are not included, try the charCount!
                noWhitespaceCount.setText("Characters without whitespace " + charCounterWithoutWhitespace.length());
                // Counts every character, they even accepted whitespace! Hooray whitespace!
                charCount.setText("Characters: " + textArea.getText().length());

                int wordCounter = parts.length;
                //When the text area is empty it will display a word count of 1! This gets rid of the problem
                // sadly it adds an other problem, when a character is put in that is not a valid "word"
                // it will add "" <- to the array, thus counting as a word.
                // example = , Hello -> counts as 2 words.
                // this issue only happens in this instance and after wards it works fine.
                if (value.equals("")) {
                    wordCount.setText("Words " + 0);

                } else {
                    wordCount.setText("Words: " + wordCounter);
                }

                // for the reverse button! Only works if at least one "word" is written! :D
                try {
                    int charCounter = noWhitespaceCount.getText().length();
                    if (charCounter >= 2) {
                        reverseBtn.setDisable(false);
                    } else {
                        reverseBtn.setDisable(true);
                    }
                } catch (Exception e) {
                    System.out.println("Why" + e);
                }
            }
        });


        // action that is done when the save button is pressed
        saveBtn.setOnAction(action ->{
            // save dialog is opened
            File fileSaveName = fileChooser.showSaveDialog(primaryStage);
            System.out.println(fileSaveName);

            // is in a try so that you can save more than once
            // is used to write the text from the text are into the file!
            try(  PrintWriter saveFilePath = new PrintWriter( fileSaveName)){
                // Java encodes in UTF-16, so fist I turn the text into bytes and change the encoding to UTF-8
                byte[] byteText = value.getBytes(Charset.forName("UTF-8"));
                //Creating a sting that takes the byteText and encodes it in UTF-8!
                String original = new String(byteText, "UTF-8");
                // the text is writ in to the text file
                saveFilePath.println(original);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());

             // if someone decides to not to save and stop the process I catch the error.
             // a wall of red text is annoying! Does not influence anything except the console output
             // program runs normal after!
            }catch (NullPointerException es){
                System.out.println(";)Caught the naughty fella! Seems like someone did not want to save a file! -> "  + es);
            }



        });

        // creating the case for the openBtn
        openBtn.setOnAction(action ->{
            try {
                // file chooser opens the open dialog
                File fileOpen = fileChooser.showOpenDialog(primaryStage);
                // Just shows the path in the console
                System.out.println(fileOpen.getPath());

                // the inputstream reads the file from the chosen path
                FileInputStream file = new FileInputStream(fileOpen.getPath());
                //turning the text into bytes?
                byte[] data = new byte[file.available()];

                // "resets" the text area, other wise it could happen that, if there is text in the are already, it will just be added. We don't want that
                textArea.setText("");

                // reads the data
                file.read(data);
                // closes the file
                file.close();
                //turning the byteData back into a String so that we can add it to the text area!
                String textOut = new String(data);
                // adding the the text from the file to the text area!
                textArea.appendText(textOut);
                /*ReadFile file = new ReadFile((fileOpen.getPath()));
                //String[] aryLines = file.OpenFile();
                int i;
                for(i = 0; i < aryLines.length; i++){
                    textArea.appendText(aryLines[i] + "\n");

                }*/
            }
            catch (IOException e){
                System.out.println(e.getMessage());


                // same reason as the saveBtn
            }catch (NullPointerException z){
                System.out.println(";) Caught you naughty fella! Seems like someone did not want to open a file! -> " + z);
            }
        });


        //replaces one or more spaces with one empty String = " "
        cutBtn.setOnAction(action ->{
            textArea.setText(textArea.getText().replaceAll("\\s+", " "));

        });


        // just a clear button. Just resets the text area to an empty string.
        clearBtn.setOnAction(action ->{
            textArea.setText("");

        });

    // reverses the text ;D
    reverseBtn.setOnAction(action -> {
        try {
                StringBuffer reverseValue = new StringBuffer(value);
                reverseValue.reverse();
                String changedValue = new String(reverseValue);
                textArea.setText(changedValue);
        }catch(NullPointerException e){
            System.out.println(" This is a null " + e);

        }
    });


    }



    public static void main(String[] args) {
        launch(args);


    }


    }
