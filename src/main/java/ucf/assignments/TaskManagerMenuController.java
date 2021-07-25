package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TaskManagerMenuController implements Initializable {
    @FXML
    public TableView<Item> TaskManagerTable;
    @FXML
    public TableColumn<Item, Double> ValueColumn;
    @FXML
    public TableColumn<Item, String> SerialNumberColumn;
    @FXML
    public TableColumn<Item, String> NameColumn;
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
    @FXML
    public AnchorPane anchorpane;


    public void initialize(URL location, ResourceBundle resources) {
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
      /* FilteredList<Item> filteredData = new FilteredList<>(data, b-> true);
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

       */


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

    public void DeleteItem() {
        //create list to allow app to select the row for deletion
        ObservableList<Item> selectedRows, allTasks;
        //set function to select item from table
        allTasks = TaskManagerTable.getItems();
//set function to get specific items
        selectedRows = TaskManagerTable.getSelectionModel().getSelectedItems();
        //create loop to remove item row selected
        for (Item item : selectedRows) {
            allTasks.remove(item);
        }

    }

    public void AddItemButtonClicked(ActionEvent actionEvent) {
        String itemName = EnterNameTextField.getText();
        String itemID = EnterSerialNumberTextField.getText();
        Double itemValue = Double.parseDouble(EnterValueTextField.getText());
        //create new Item to grab user input from textfields
        Item item = addItems(itemValue, itemID, itemName);


        //add the user input to tableview
        //for loop to stop repeated valuenames and serial numbers?
        TaskManagerTable.getItems().add(item);

    }

    public Item addItems(double itemValue, String itemID, String itemName) {

        return new Item(itemValue, itemID, itemName);

    }


    public void SearchButtonClicked(ActionEvent actionEvent) throws IOException {
       /* FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchItems.fxml"));
        Parent root = loader.load();
        SearchItemsController SearchItemScene = loader.getController();
        SearchItemScene.showInformation(ValueColumn.getText(),SerialNumberColumn.getText(),NameColumn.getText());
                //String.valueOf(EnterValueTextField.getText()), EnterSerialNumberTextField.getText(), EnterNameTextField.getText());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Task Manager");
        stage.show();

        */

    }

    public void SaveListClicked(ActionEvent actionEvent) throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save file");
        Stage stage = (Stage) anchorpane.getScene().getWindow();

       File file = fc.showSaveDialog(stage);
    if(file != null){
        String path = file.getPath();
        FileWriter myWriter = new FileWriter(path);


            for (Item item : TaskManagerTable.getItems()) {

                String formatted = String.format("%s\t %s\t %s\n", item.getItemValue(), item.getItemID(), item.getItemName());
                myWriter.write(formatted);
                System.out.println(formatted);
            }
        myWriter.close();
        }

    }



    public void SaveListasHtml(ActionEvent actionEvent) throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save file");
        Stage stage = (Stage) anchorpane.getScene().getWindow();

        File file = fc.showSaveDialog(stage);
        if(file != null){
            String path = file.getPath();
            FileWriter myWriter = new FileWriter(path);

            myWriter.write("<table style=\"width:100%\">" +
                    "  <tr>" +
                    "    <th>Value</th>" +
                    "    <th>Serial Number</th>" +
                    "    <th>Name</th>" +
                    "  </tr>");

            for (Item item : TaskManagerTable.getItems()) {

                String formatted = String.format(
                        "    <td>%s</td>" +
                        "    <td> %s</td>" +
                        "    <td> %s</td>" +
                        "  </tr>" +
                        "  <tr>", item.getItemValue(), item.getItemID(), item.getItemName());
                myWriter.write(formatted);
                System.out.println(formatted);
            }
            myWriter.close();
        }

    }

    public void SaveListasJSON(ActionEvent actionEvent) {


    }


    public void TSVFileUploadClicked(ActionEvent actionEvent) throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open file");
        Stage stage = (Stage) anchorpane.getScene().getWindow();

        File file = fc.showOpenDialog(stage);

        if (file != null) {
            String path = file.getPath();
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                Double doubles = Double.parseDouble(values[0]);
                final ObservableList<Item> data = FXCollections.observableArrayList(
                        new Item(doubles, values[1], values[2]));
                TaskManagerTable.getItems().addAll(data);


            }


        }
    }

    public void HTMLFileUploadClicked(ActionEvent actionEvent) throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open file");
        Stage stage = (Stage) anchorpane.getScene().getWindow();

        File file = fc.showOpenDialog(stage);
        if (file != null) {

                String path = file.getPath();
                Document doc = Jsoup.parseBodyFragment(path);
            Element element = doc.select("td").get(3);


        }
    }

    public void JSONFileUploadClicked(ActionEvent actionEvent) {


    }
}
