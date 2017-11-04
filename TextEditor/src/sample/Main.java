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
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


public class Main extends Application {
    private String value;
    @Override
    public void start(Stage primaryStage) throws Exception{
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter exFilter = new FileChooser.ExtensionFilter("TXT files",("*.txt"),"*txt");
        fileChooser.getExtensionFilters().addAll(exFilter);

        Separator sep1 = new Separator();
        sep1.setOrientation(Orientation.VERTICAL);

        VBox root = new VBox();

        HBox buttonBox = new HBox();
        Button cutBtn = new Button();
        cutBtn.setPrefWidth(75);
        cutBtn.setText("Cut ");
        Label charCount = new Label();
        Label wordCount = new Label();

        final Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMinSize(10, 1);


        charCount.setText("Characters: ");
        wordCount.setText("Words: ");
        charCount.setPadding(new Insets(0,25,0,0));
        wordCount.setPadding(new Insets(0,25,0,0));


        buttonBox.getChildren().addAll(cutBtn ,spacer , charCount, sep1, wordCount);

        TextArea textArea = new TextArea();

        VBox.setVgrow(textArea, Priority.ALWAYS);

        Menu fileMenu = new Menu("File");
        MenuItem saveBtn = new MenuItem("Save");
        MenuItem openBtn = new MenuItem("Open");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu);


        fileMenu.getItems().addAll(saveBtn, openBtn);
        root.getChildren().addAll(menuBar, buttonBox ,textArea);



        primaryStage.setTitle("Text editor");
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.setMinHeight(275);
        primaryStage.setMinWidth(300);
        primaryStage.show();

        textArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {

                // creates a String array "parts" that gets words
                //Everything that is not a part of a word, that is not present in the alphabet or a number, is not counted as a word
                // example = " ", ",", "!", "!" these will not count as words if they stand alone but they are used to split!
                //
                String[] parts = textArea.getText().split("\\W+");
                // this will run whenever text is changed
                value = textArea.getText().replaceAll("\n", System.getProperty("line.separator"));

                charCount.setText("Characters: "+ textArea.getText().length());
                wordCount.setText("Words: " + parts.length);


            }
        });

        saveBtn.setOnAction(action ->{
            File fileSaveName = fileChooser.showSaveDialog(primaryStage);
            System.out.println(fileSaveName);

            try(  PrintWriter out = new PrintWriter( fileSaveName)){
                byte[] byteText = value.getBytes(Charset.forName("UTF-8"));
                String original = new String(byteText, "UTF-8");
                out.println(original);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }catch (NullPointerException es){
                System.out.println(";)Caught the naughty fella! Seems like someone did not want to save a file! -> "  + es);
            }



        });

        openBtn.setOnAction(action ->{
            textArea.setText("");
            try {
                File fileopen = fileChooser.showOpenDialog(primaryStage);
                System.out.println(fileopen.getPath());
                ReadFile file = new ReadFile((fileopen.getPath()));
                String[] aryLines = file.OpenFile();
                int i;
                for(i = 0; i < aryLines.length; i++){
                    textArea.appendText(aryLines[i] + "\n");
                }
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }catch (NullPointerException z){
                System.out.println(";) Caught you naughty fella! Seems like someone did not want to open a file! -> " + z);
            }
        });


        //replaces one or more spaces with one empty String = " "
        cutBtn.setOnAction(action ->{
            textArea.setText(textArea.getText().replaceAll("\\s+", " "));

        });
    }



    public static void main(String[] args) {
        launch(args);


    }


}
