package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.sql.SQLException;

public class Main extends Application {
    String countryName;

    // Constant for title of window
    private static final String title = "WorldDB";

    /**
     * GUI element which have to be accessed event based.
     */
    private final TableView<Country> table = new TableView<>();
    private final Label meanLabel = new Label();

    /**
     * DAO object for managing DB access.
     */
    private Controller controller = new Controller();

    // Common enty point of the JavaFX Application life cycle.
    @Override
    public void start(Stage stage) throws Exception{
        Scene scene = new Scene(new Pane());
        stage.setTitle(title);
        stage.setHeight(450);

        ObservableList<Country> data = FXCollections.observableArrayList(controller.getCountries());

        // Creating a file chooser in context of the GUI class.
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Filtering DBs", "*.db", "*.db2", "*.db3", "*.sqlite");
        fileChooser.getExtensionFilters().add(extensionFilter);

        // Creating menu item to open SQLite file.
        MenuItem openFileMenuItem = new MenuItem("Open");
        openFileMenuItem.setMnemonicParsing(true);
        openFileMenuItem.setOnAction(e -> {
            File file = fileChooser.showOpenDialog(stage);
            if (null != file) {

                // Reset filters.
                stage.setTitle(title + " - " + file.getName());
                controller.setFilename(file.getAbsolutePath());
            } else {
                stage.setTitle(title);
            }
        });

        // Creating menu entry for file actions.
        Menu fileMenu = new Menu("File");
        fileMenu.setMnemonicParsing(true);
        fileMenu.getItems().add(openFileMenuItem);

        // Creating MenuBar and adding MenuItems.
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);

        // TODO: add label to toolbar
        // TODO: add textfield to toolbar
        // TODO: add listener to textfield, which changes the name filter of the dao class

        // Creating ToolBar.
        ToolBar toolBar = new ToolBar();

        // Creating growing HBox to shift further ToolBar items to the right.
        HBox hBox = new HBox();
        //HBox.setHgrow(hBox, Priority.ALWAYS);
        toolBar.getItems().add(hBox);

        ToolBar secondBar = new ToolBar();
        TextField searchArea = new TextField();
        Label searchTermLB = new Label("search term: ");
        Label meanCountries = new Label("mean population: ");

        //secondBar.getItems().addAll(searchTermLB, searchArea, meanCountries);

        toolBar.getItems().addAll(searchTermLB, searchArea,  meanLabel);

        // Creating the table column for country names.
        TableColumn<Country, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setMinWidth(110.0);

        // Creating the table column for capitals.
        TableColumn<Country, String> capitalColumn = new TableColumn<>("Capital");
        capitalColumn.setCellValueFactory(new PropertyValueFactory<>("capital"));
        capitalColumn.setMinWidth(110.0);

        // Creating the table column for country areas.
        TableColumn<Country, String> areaColumn = new TableColumn<>("Area");
        areaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));
        areaColumn.setMinWidth(110.0);

        // Creating the table column for population.
        TableColumn populationColumn = new TableColumn<>("Population");
        populationColumn.setCellValueFactory(new PropertyValueFactory<>("population"));
        populationColumn.setMinWidth(110.0);

        // Adding table columns to table.
        table.setItems(controller.getCountries());
        table.getColumns().addAll(nameColumn, capitalColumn, areaColumn, populationColumn);

        // Creating VBox and adding MenuBar, ToolBar and TableView to group them horizontal.
        final VBox vbox = new VBox();
        vbox.getChildren().addAll(menuBar, toolBar, table);

        // Adding VBox to root.
        ((Pane) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();



        searchArea.textProperty().addListener((observable, oldValue, newValue) -> {
                countryName = newValue;
                controller.setFilterName(newValue);
                controller.setCountryName(newValue);


        });
        updateMeanPopulationLabel();





    }

    /**
     * Updates the mean population label in the ToolBar.
     */
    private void updateMeanPopulationLabel() {
        meanLabel.setText("mean population: " + controller.getMeanPopulation());

    }



    // Common start of the JavaFX Application life cycle.
    public static void main(String[] args) {
        launch(args);
    }
}
