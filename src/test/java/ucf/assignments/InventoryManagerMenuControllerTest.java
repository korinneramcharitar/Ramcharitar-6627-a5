package ucf.assignments;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Test;

class InventoryManagerMenuControllerTest {

    @FXML
    public TableView<Item> TaskManagerTable;
    @FXML
    public TextField EnterValueTextField;
    @FXML
    public TextField EnterSerialNumberTextField;
    @FXML
    public TextField EnterNameTextField;

///there are some requirements that dont need a test such as the sorting inventory items
    //this is because tableview has it's own sort function implemented

    @Test
    void check_items_added() {
        //push constants


        //check table for new added item

        ;
        //if statement to make sure constant is being passed through

                // assert true if tableview has new data

            }

    @Test
    void check_items_deleted() {
        //hard code items in tableview

        //check that after item is deleted

        //instance of deleted item is no longer detected


    }
    @Test
    void check_TSV_save(){
        //check that filewriter function creates file with .txt extension
    }
    @Test
    void check_HTML_save(){

        //check that filewriter function creates file with .html extension
    }
    @Test
    void check_JSON_save(){
        //put values in tableview
        //print values in filewriter
        //check that filewriter function creates file with .json extension
    }
    @Test
    void check_TSV_upload(){
        //check that filereader pulls file that matches TSV format
        //check data from file was imported to tableview
    }
    @Test
    void check_HTML_format(){
        //check that filereader pulls file that matches HTML format
        //check data from file was imported to tableview
    }
    @Test
    void check_JSON_format(){
        //check that filereader pulls file that matches JSON format
        //check data from file was imported to tableview
    }
    @Test
    void check_Table_is_editable(){
        //check table is editable
    }


}
