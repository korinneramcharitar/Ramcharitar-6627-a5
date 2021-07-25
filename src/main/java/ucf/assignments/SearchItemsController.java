package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchItemsController implements Initializable {
    @FXML
    public TableColumn<Item, Double> ValueColumn2;
    @FXML
    public TableColumn<Item, String> SerialNumberColumn2;
    @FXML
    public TableColumn<Item, String> ItemNameColumn2;
    @FXML
    public TextField SearchBarTextField;
    @FXML
    public TableView<Item> TaskManagerTableView;

    public void initialize(URL location, ResourceBundle resources) {
        //set Value Column values
            ValueColumn2.setCellValueFactory(new PropertyValueFactory<Item, Double>("ItemValue"));
            //set Number Column values
            SerialNumberColumn2.setCellValueFactory(new PropertyValueFactory<Item, String>("ItemID"));
            //set Name Column values
            ItemNameColumn2.setCellValueFactory(new PropertyValueFactory<Item, String>("ItemName"));


//create list to hold data that will automatically be pushed into tableview when user first opens application
            ObservableList<Item> data = FXCollections.observableArrayList(
                    new Item(399.99, "AXB124AXY3", "Xbox One"),
                    new Item(599.99, "S40AZBDE47", "Samsung TV")
            );
            TaskManagerTableView.setItems(data);
        //allow Table to be editable
        TaskManagerTableView.setEditable(true);
//allow user to edit Value Column
        ValueColumn2.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
//allow user to edit Serial Number Column
        SerialNumberColumn2.setCellFactory((TextFieldTableCell.forTableColumn()));
//Allow user to edit Name Column
        ItemNameColumn2.setCellFactory((TextFieldTableCell.forTableColumn()));
//allow user to select multiple columns
        TaskManagerTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


     private Item selectedTasks;


   /*  public void showInformation(ObservableList<Item> items) {
      ValueColumn2.setText(String.valueOf(selectedTasks.getItemValue()));
        SerialNumberColumn2.setText(selectedTasks.getItemID());
        ItemNameColumn2.setText(selectedTasks.getItemName());


    }*/

    public void showInformation(String value, String itemID, String name) {
       ValueColumn2.setText(value);
        SerialNumberColumn2.setText(itemID);
        ItemNameColumn2.setText(name);
    }
}
