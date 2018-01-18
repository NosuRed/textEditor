package sample;

import com.sun.javafx.collections.ObservableSequentialListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.util.Callback;

import java.util.Optional;

public class Controller {
    @FXML
    private Button btnEdit;
    @FXML
    private ListView<Item> listMain;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnRemove;

    private ObservableList<Item> model;
    private Database database;

    public void init() {
        // set data model
        database = new Database();
        model = FXCollections.observableArrayList( database.getItems() );

        // FIXME: adapt to Item
        // optional: sort list alphabetically
        // (otherwise add model to listMain directly)
         SortedList<Item> sortedList = new SortedList<Item>(model,((o1, o2) -> o1.getValue().compareToIgnoreCase(o2.getValue())));


        listMain.setItems(model);

        // initial button state
        updateBtnRemove();
        // selection change should update button state
        listMain.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Item>) c -> updateBtnRemove());


    }

    /** enable or disable button for remove according to selection */
    public void updateBtnRemove() {
        btnRemove.setDisable(!(listMain.getSelectionModel().getSelectedItems().size() > 0));

        listMain.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
            @Override
            public ListCell<Item> call(ListView<Item> param) {
                return new ListCell<Item>(){

                    @Override
                    protected void updateItem(Item item, boolean empty){
                        super.updateItem(item, empty);

                        if (empty || item == null){
                            setText(null);
                            setGraphic(null);
                        }else{
                            setText(item.getValue());
                        }

                    }

                };
            }
        });
    }

    public void onBtnInsert(ActionEvent actionEvent) {

    }


    public void onBtnRemove(ActionEvent actionEvent) {
        // FIXME: implement multi selections

        // important: delete from database first
        database.deleteItem(listMain.getSelectionModel().getSelectedItem());
        model.remove(listMain.getSelectionModel().getSelectedIndex());
    }

    public void onBtnEdit(ActionEvent actionEvent) {
        Item item= database.updateItem(listMain.getSelectionModel().getSelectedItem());
        //database.updateItem(listMain.getSelectionModel().getSelectedItem());
        model.remove(listMain.getSelectionModel().getSelectedIndex());

        }

    }
