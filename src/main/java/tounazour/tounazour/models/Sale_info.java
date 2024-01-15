package tounazour.tounazour.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Sale_info implements Serializable {
    public SimpleIntegerProperty id = new SimpleIntegerProperty();
    public SimpleStringProperty Honey_type = new SimpleStringProperty();
    public SimpleStringProperty Quantity = new SimpleStringProperty();
    public SimpleStringProperty Total = new SimpleStringProperty();
    public SimpleStringProperty Farm = new SimpleStringProperty();
    public SimpleStringProperty Client = new SimpleStringProperty();

    public Sale_info() {
        // Default constructor
    }

    public Sale_info( int id,String Honey_type, String Quantity, String Total, String Farm, String Client) {
        this.id.set(id);
        this.Honey_type.set(Honey_type);
        this.Quantity.set(Quantity);
        this.Total.set(Total);
        this.Farm.set(Farm);
        this.Client.set(Client);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getHoney_type() {
        return Honey_type.get();
    }

    public SimpleStringProperty honey_typeProperty() {
        return Honey_type;
    }

    public void setHoney_type(String honey_type) {
        this.Honey_type.set(honey_type);
    }

    public String getQuantity() {
        return Quantity.get();
    }

    public SimpleStringProperty quantityProperty() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        this.Quantity.set(quantity);
    }

    public String getTotal() {
        return Total.get();
    }

    public SimpleStringProperty totalProperty() {
        return Total;
    }

    public void setTotal(String total) {
        this.Total.set(total);
    }

    public String getFarm() {
        return Farm.get();
    }

    public SimpleStringProperty farmProperty() {
        return Farm;
    }

    public void setFarm(String farm) {
        this.Farm.set(farm);
    }

    public String getClient() {
        return Client.get();
    }

    public SimpleStringProperty clientProperty() {
        return Client;
    }

    public void setClient(String client) {
        this.Client.set(client);
    }
}