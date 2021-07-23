package ucf.assignments;

import javafx.beans.property.SimpleDoubleProperty;

import javafx.beans.property.SimpleStringProperty;

import java.math.BigDecimal;

public class Item
{
    private final SimpleDoubleProperty itemValue;
    private final SimpleStringProperty itemID;
    private final SimpleStringProperty itemName;



public Item(Double itemValue, String itemID, String itemName){
    this.itemValue =new SimpleDoubleProperty(itemValue);
    this.itemID = new SimpleStringProperty(itemID);
    this.itemName  = new SimpleStringProperty(itemName);

}

    public String getItemName() {
        return itemName.get();
    }

    public SimpleStringProperty itemNameProperty() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName.set(itemName);
    }

    public String getItemID() {
        return itemID.get();
    }

    public SimpleStringProperty itemIDProperty() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID.set(itemID);
    }

    public double getItemValue() {
        return itemValue.get();
    }

    public SimpleDoubleProperty itemValueProperty() {
        return itemValue;
    }

    public void setItemValue(double itemValue) {
        this.itemValue.set(itemValue);
    }
}
