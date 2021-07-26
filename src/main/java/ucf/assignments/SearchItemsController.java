package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Korinne Ramcharitar
 */

public class SearchItemsController implements Initializable {
private ObservableList<Item> selectedItems;

    @FXML
    public TableColumn<Item, Double> ValueColumn2;
    @FXML
    public TableColumn<Item, String> SerialNumberColumn2;

    @FXML
    public TableColumn<Item, String> ItemNameColumn2;
    @FXML
    public TextField SearchBarTextField;
    @FXML
    public TableView<Item> TaskManagerTableView2;



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
       TaskManagerTableView2.setItems(data);

      FilteredList<Item> filteredData = new FilteredList<>(data, b-> true);
        SearchBarTextField.textProperty().addListener(((observable, oldValue, newValue) -> {
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
        sortedData.comparatorProperty().bind(TaskManagerTableView2.comparatorProperty());

        //add the filtered list to table
        TaskManagerTableView2.setItems(sortedData)
        ;

       //allow Table to be editable
       TaskManagerTableView2.setEditable(true);
//allow user to edit Value Column
       ValueColumn2.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
//allow user to edit Serial Number Column
       SerialNumberColumn2.setCellFactory((TextFieldTableCell.forTableColumn()));
//Allow user to edit Name Column
       ItemNameColumn2.setCellFactory((TextFieldTableCell.forTableColumn()));
//allow user to select multiple columns
       TaskManagerTableView2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
   }






}
