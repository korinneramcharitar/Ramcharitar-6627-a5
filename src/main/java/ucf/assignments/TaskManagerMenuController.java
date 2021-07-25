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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.URL;
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
        double itemValue = Double.parseDouble(EnterValueTextField.getText());

        //create new Item to grab user input from textfields
     Item item = addItems(itemValue, itemID, itemName);


    TaskManagerTable.getItems().add(item);

    }






    public Item addItems(double itemValue, String itemID, String itemName) {


        return new Item(itemValue, itemID, itemName);

    }


    public void SearchButtonClicked(ActionEvent actionEvent) throws IOException {

        try {
            Parent SearchItems = FXMLLoader.load(getClass().getResource("SearchItems.fxml"));
            Scene SearchItemScene = new Scene(SearchItems);
            Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(SearchItemScene);
            window.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public void SaveListasJSON(ActionEvent actionEvent) throws IOException {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save file");
        Stage stage = (Stage) anchorpane.getScene().getWindow();

        File file = fc.showSaveDialog(stage);
        if (file != null) {
            String path = file.getPath();
            FileWriter myWriter = new FileWriter(path);

            for (Item item : TaskManagerTable.getItems()) {
                String formatted = String.format("{\"Items\":{\"SerialNumber\":\"%s\",\"Value\":%s,\"Name\":\"%s\"}}", item.getItemID(), item.getItemValue(), item.getItemName());
                myWriter.write(formatted);
                System.out.println(formatted);
            }
            myWriter.close();
        }

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
