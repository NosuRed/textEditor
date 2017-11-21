package sample;


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
import java.io.FileNotFoundException;
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


        // creating the root layout
        VBox root = new VBox();

        // creating the Hbox for the Button and Label
        HBox buttonBox = new HBox();

        // creating the cut button
        Button cutBtn = new Button();
        cutBtn.setPrefWidth(75);
        cutBtn.setText("Cut ");





        // creating the counter labels. These display the Characters and Words typed!
        Label charCount = new Label();
        Label wordCount = new Label();
        charCount.setText("Characters: ");
        wordCount.setText("Words: ");
        charCount.setPadding(new Insets(0,25,0,0));
        wordCount.setPadding(new Insets(0,25,0,0));

        // creating a pane that always grows together with the Hbox
        // this pushes the labels to the right
        final Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);

        // adding all children to the hbox
        buttonBox.getChildren().addAll(cutBtn, spacer, charCount, sep1, wordCount);

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
        fileMenu.getItems().addAll (saveBtn, openBtn, clearBtn);


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


        Counter counter = new Counter();
        Controller counterSetandGet = new Controller();

        // adding a listener for the text are. This way it is able react to changes in the text are immediately
        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                // creates a String array "parts" that gets words
                //Everything that is not a part of a word, that is not present in the alphabet or a number, is not counted as a word
                // example = " ", ",", "!", "!" these will not count as words if they stand alone but they are used to split!
                String parts = textArea.getText(); //.split("\\W+");
                counterSetandGet.setParts(parts);
                counter.wordCounter(counterSetandGet.getParts());
                wordCount.setText("Words: " + counter.getWordCounter());

                // this will run whenever text is changed
                value = textArea.getText(); //.replaceAll("\n", System.getProperty("line.separator"));
                counterSetandGet.setValue(textArea.getText());
                counter.charCounter(value);
               charCount.setText("Characters: " + counter.getCharCounter());


                //When the text area is empty it will display a word count of 1! This gets rid of the problem
                // sadly it adds an other problem, when a character is put in that is not a valid "word"
                // it will add "" <- to the array, thus counting as a word.
                // example = , Hello -> counts as 2 words.
                // this issue only happens in this instance and after wards it works fine.
            }
        });


        // action that is done when the save button is pressed
        Controller saveMenuFile = new Controller();
        SaveFileMenu fileToSave = new SaveFileMenu();

        saveBtn.setOnAction(action ->{
            saveMenuFile.setValue(value);
            File fileSaveName = fileChooser.showSaveDialog(primaryStage);

        try(PrintWriter saveFilePath = new PrintWriter( fileSaveName)) {
            // save dialog is opened
           fileToSave.saveFile(saveMenuFile.getValue());
           saveFilePath.println(fileToSave.getSavedTextOut());

            // is in a try so that you can save more than once
            // is used to write the text from the text are into the file!

        }catch (FileNotFoundException e){
            System.out.println("Seems like saving is not your thing!");
        }
        });

        // creating the case for the openBtn
        OpenFileMenu newOpening = new OpenFileMenu();
        Controller openFile = new Controller();
        openBtn.setOnAction(action -> {
            try {
            // file chooser opens the open dialog
            File fileOpen = fileChooser.showOpenDialog(primaryStage);
            openFile.setTextIn(fileOpen);
            System.out.println(openFile.getTextIn());
            textArea.setText("");
            newOpening.openFile(openFile.getTextIn());
            textArea.appendText(newOpening.getTextOutput());




            } catch (java.lang.NullPointerException e){
                System.out.println(e +  "This is happening in the openBtn thing");
            }

        });


        //replaces one or more spaces with one empty String = " "
        Controller cutValue = new Controller();
        CutButton cutButton = new CutButton();
        cutBtn.setOnAction(action ->{
            cutValue.setCutValue(value);
            cutButton.removeWhiteSpace(cutValue.getCutValue());
            textArea.setText(cutButton.getValue());



        });


        // just a clear button. Just resets the text area to an empty string.
        clearBtn.setOnAction(action ->{
            textArea.setText("");

        });

    }



    public static void main(String[] args) {
        launch(args);


    }


}
