package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class InventoryManagerMenuController implements Initializable {
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
    public Button SearchButton;
    @FXML
    public AnchorPane anchorpane;
    /*
     *  UCF COP3330 Summer 2021 Assignment 5 Solution
     *  Copyright 2021 Korinne Ramcharitar
     */


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


        //get input from textfield
        String itemName = EnterNameTextField.getText();
        String itemID = EnterSerialNumberTextField.getText();
        double itemValue = Double.parseDouble(EnterValueTextField.getText());

        //create new Item to grab user input from textfields
        Item item = addItems(itemValue, itemID, itemName);


        //add textfield input to table
        //show alert box if itemID is already in tableview
        if (!TaskManagerTable.getItems().contains(itemID)) {
            TaskManagerTable.getItems().add(item);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.showAndWait();

        }


    }


    public Item addItems(double itemValue, String itemID, String itemName) {

        //create a template for new Item
        return new Item(itemValue, itemID, itemName);

    }


    public void SearchButtonClicked(ActionEvent actionEvent) throws IOException {
        //takes user to new tableview to search items

        try {
            Parent SearchItems = FXMLLoader.load(getClass().getResource("SearchItems.fxml"));
            Scene SearchItemScene = new Scene(SearchItems);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(SearchItemScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void SaveListClicked(ActionEvent actionEvent) throws IOException {
        //open menu for chooosing Files
        FileChooser fc = new FileChooser();
        fc.setTitle("Save file");
        Stage stage = (Stage) anchorpane.getScene().getWindow();

        File file = fc.showSaveDialog(stage);
        if (file != null) {
            //turn user input path into string
            String path = file.getPath();
            FileWriter myWriter = new FileWriter(path);

            //loop through table data
            for (Item item : TaskManagerTable.getItems()) {
                //seperate data into tsv form
                String formatted = String.format("%s\t %s\t %s\n", item.getItemValue(), item.getItemID(), item.getItemName());
                //write to file
                myWriter.write(formatted);
                //print file to console
                System.out.println(formatted);
            }
            //close file
            myWriter.close();
        }

    }


    public void SaveListasHtml(ActionEvent actionEvent) throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save file");
        Stage stage = (Stage) anchorpane.getScene().getWindow();

        File file = fc.showSaveDialog(stage);
        if (file != null) {
            //tuyrn path from fileChooser to String
            String path = file.getPath();
            FileWriter myWriter = new FileWriter(path);
            //include header information for Table data
            myWriter.write("<table style=\"width:100%\">" +
                    "  <tr>" +
                    "    <th>Value</th>" +
                    "    <th>Serial Number</th>" +
                    "    <th>Name</th>" +
                    "  </tr>");

            for (Item item : TaskManagerTable.getItems()) {
                //set table data in html table format
                String formatted = String.format(
                        "    <td>%s</td>" +
                                "    <td> %s</td>" +
                                "    <td> %s</td>" +
                                "  </tr>" +
                                "  <tr>", item.getItemValue(), item.getItemID(), item.getItemName());
                //write to file
                myWriter.write(formatted);

            }
            //close file
            myWriter.close();
        }

    }

    public void SaveListasJSON(ActionEvent actionEvent) throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save file");
        Stage stage = (Stage) anchorpane.getScene().getWindow();

        File file = fc.showSaveDialog(stage);
        if (file != null) {
            //turn file path to string
            String path = file.getPath();
            //write file to path choosen by fileChooser
            FileWriter myWriter = new FileWriter(path);
            //loop through each inventory column
            for (Item item : TaskManagerTable.getItems()) {
                //format the Table data as a JSON file
                String formatted = String.format("{\"Items\":{\"SerialNumber\":\"%s\",\"Value\":%s,\"Name\":\"%s\"}}", item.getItemID(), item.getItemValue(), item.getItemName());
                //write to the file
                myWriter.write(formatted);
                //print out the file to console
                System.out.println(formatted);
            }
            //close file
            myWriter.close();
        }

    }

    public void TSVFileUploadClicked(ActionEvent actionEvent) throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open file");
        Stage stage = (Stage) anchorpane.getScene().getWindow();

        File file = fc.showOpenDialog(stage);

        if (file != null) {
            //turn file path to string
            String path = file.getPath();
            //read in file
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            //go through each line of file
            while ((line = br.readLine()) != null) {

                //grab each value that is tabbed
                String[] values = line.split("\t");
                //set double value for Observable List
                Double doubles = Double.parseDouble(values[0]);
                //create list instance to put in table with objects from file
                final ObservableList<Item> data = FXCollections.observableArrayList(
                        new Item(doubles, values[1], values[2]));
                //add Inventory Items to list as formatted
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
            String content = "";
            try {
                BufferedReader in = new BufferedReader(new FileReader(path));
                String str;
                while ((str = in.readLine()) != null) {
                    content += str;
                }
                in.close();
            } catch (IOException e) {
            }
            FileWriter writer = new FileWriter("/Users/korinneramcharitar/Desktop/html.txt");
            Document doc = Jsoup.parseBodyFragment(content);

            Elements rows = doc.getElementsByTag("tr");
//loop through each row in the table element
            for (Element row : rows) {
                Elements cells = row.getElementsByTag("td");
                //loop through the data in each row
                for (Element cell : cells) {
                    //seperate each column with a "," to access each info
                    writer.write(cell.text().concat(","));
                }
                //new line to reate new InventoryItem
                writer.write("\n");


            }

            writer.close();

        }
    }

    public void JSONFileUploadClicked(ActionEvent actionEvent) throws IOException, ParseException {

        FileChooser fc = new FileChooser();
        fc.setTitle("Open file");
        Stage stage = (Stage) anchorpane.getScene().getWindow();

        File file = fc.showOpenDialog(stage);
        if (file != null) {

            String path = file.getPath();
           /* JSONParser parser = new JSONParser();

            Object obj = parser.parse(new FileReader(path));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray inventoryList = (JSONArray) jsonObject.get("Inventory List");

            Iterator<JSONObject> iterator = inventoryList.iterator();
            while (iterator.hasNext()) {

            }
*/

        }
    }
}


