package ucf.assignments;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerMenuControllerTest {

    @FXML
    public TableView<Item> TaskManagerTable;
    @FXML
    public TextField EnterValueTextField;
    @FXML
    public TextField EnterSerialNumberTextField;
    @FXML
    public TextField EnterNameTextField;


    @Test
    void deleteItem(Double itemValue, String itemID, String itemName) {

    }

    @Test
    void addItems() {
       /* TaskManagerMenuController tasks = new TaskManagerMenuController();
        tasks.addItems(399.99, "ASDFGHJKL", "Plsystation");
        assertTrue(TaskManagerTable.getItems().contains(tasks));*/

    }

    @Test
    void deleteItemButtonCLicked() {

    }

    @Test
    void addItemButtonClicked() {
        //push constants
        TaskManagerMenuController tasks = new TaskManagerMenuController();

        //check table for new added item

        ;
        //if statement to make sure constant is being passed through

                //true of false

            }
}