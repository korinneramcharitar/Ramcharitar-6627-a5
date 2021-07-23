package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.css.converter.StringConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;
import ucf.assignments.Item;

import java.awt.geom.Line2D;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class TaskManagerMenuController implements Initializable {
    @FXML
    public TableView<Item> TaskManagerTable;
    @FXML
    public TableColumn<Item,Double> ValueColumn;
    @FXML
    public TableColumn<Item,String> SerialNumberColumn;
    @FXML
    public TableColumn<Item,String> NameColumn;
    @FXML
    public Button DeleteItemButton;
    @FXML
    public Button AddItemButton;
    @FXML
    public TextField EnterValueTextField;
    @FXML
    public TextField EnterSerialNumberTextField;
    @FXML
    public TextField EnterNameTextField;
    @FXML
    public javafx.scene.control.MenuBar MenuBar;
    @FXML
    public TextField SearchTextField;
    @FXML
    public Button SearchButton;


    public void initialize(URL location, ResourceBundle resources){
        //set Value Column values
        ValueColumn.setCellValueFactory(new PropertyValueFactory<Item, Double>("ItemValue"));
        //set Number Column values
        SerialNumberColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("ItemID"));
        //set Name Column values
        NameColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("ItemName"));


//create list to hold data that will automatically be pushed into tableview when user first opens application
        ObservableList<Item> data = FXCollections.observableArrayList(
                new Item(399.99, "AXB124AXY3", "Xbox One"),
                new Item(599.99, "S40AZBDE47", "Samsung TV")
        );
        TaskManagerTable.setItems(data);



        //create new list to sort through tableview
        FilteredList<Item> filteredData = new FilteredList<>(data, b-> true);
        SearchTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredData.setPredicate(item -> {

                //if the textfield is empty keep display of tableview
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                //compare first name and last name of every person with textfield.
                String lowerCaseFilter = newValue.toLowerCase();
                if (item.getItemName().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else if (item.getItemID().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;

                } else if(String.valueOf(item.getItemValue()).indexOf(lowerCaseFilter) != -1)
                    return true;
                else
                    //if nothing comes up show nothing
                    return false;
            });
        }));

        //put new list in a sorted list
        SortedList<Item> sortedData = new SortedList<>(filteredData);

        //bind sorted list to tableview
        sortedData.comparatorProperty().bind(TaskManagerTable.comparatorProperty());

        //add the filtered list to table
        TaskManagerTable.setItems(sortedData);


//allow Table to be editable
        TaskManagerTable.setEditable(true);
//allow user to edit Value Column
        ValueColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
//allow user to edit Serial Number Column
        SerialNumberColumn.setCellFactory((TextFieldTableCell.forTableColumn()));
//Allow user to edit Name Column
        NameColumn.setCellFactory((TextFieldTableCell.forTableColumn()));
//allow user to select multiple columns
        TaskManagerTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    public void DeleteItemButtonCLicked(ActionEvent actionEvent) {
        DeleteItem();
    }
    public void DeleteItem(){
        //create list to allow app to select the row for deletion
        ObservableList<Item> selectedRows, allTasks;
        //set function to select item from table
        allTasks = TaskManagerTable.getItems();
//set function to get specific items
        selectedRows = TaskManagerTable.getSelectionModel().getSelectedItems();
        //create loop to remove item row selected
        for(Item item : selectedRows){
            allTasks.remove(item);
        }

    }

    public void AddItemButtonClicked(ActionEvent actionEvent) {
AddItem();
    }
    public void AddItem(){
        //create new Item to grab user input from textfields
Item item = new Item(Double.parseDouble(EnterValueTextField.getText()),EnterNameTextField.getText(), EnterSerialNumberTextField.getText());
  //add the user input to tableview after pressing Add Button
   TaskManagerTable.getItems().add(item);
    }



}
