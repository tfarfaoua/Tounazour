package tounazour.tounazour.controllers;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tounazour.tounazour.ConnexionMysql;
import tounazour.tounazour.models.Sale_info;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleFormulaireController implements Initializable {
    @FXML
    private TextField Honey_type;
    @FXML
    private TextField Quantity;
    @FXML
    private TextField Total;
    @FXML
    private TextField Farm;
    @FXML
    private TextField Client;

    String query = null;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    int saleid;

    @FXML

    public void AddSale() {
        ConnexionMysql connectionClass = new ConnexionMysql();
        conn = connectionClass.connexionDB(); // Use class-level conn

        String honey_type = Honey_type.getText();
        String quantity = Quantity.getText();
        String total = Total.getText();
        String farm = Farm.getText();
        String client = Client.getText();

        if (honey_type.isEmpty() || quantity.isEmpty() || total.isEmpty() || farm.isEmpty() || client.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("please fill all data");
            alert.showAndWait();
        } else {
            getQuery();
            insertData(honey_type, quantity, total, farm, client);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Your data is entered correctly");
            alert.showAndWait();
        }

    }

    private void insertData(String honeyType, String quantity, String total, String farm, String client) {
        try {
            getQuery();  // Call getQuery to set the correct query
            pst = conn.prepareStatement(query);
            pst.setString(1, honeyType);
            pst.setString(2, quantity);
            pst.setString(3, total);
            pst.setString(4, farm);
            pst.setString(5, client);

            if (update) {
                pst.setInt(6, saleid);  // Set the id for update
            }

            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }


    private void getQuery() {
        if (!update) {
            query = "INSERT INTO Sale_info(Honey_type, Quantity, Total, Farm, Client) VALUES (?,?,?,?,?)";
        } else {
            query = "UPDATE Sale_info SET Honey_type=?, Quantity=?, Total=?, Farm=?, Client=? WHERE id=?";
        }
    }





    @Override
    public void initialize(URL location, ResourceBundle resources){
        // Initialization tasks, if any, can be performed here
        // This method is called automatically when the controller is loaded
    }


    private boolean update;  // flag to indicate if it's an update operation

    // ... other methods ...

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public void setTextField(int id,String honey_type, String quantity, String total, String farm, String client) {

        saleid=id;
        Honey_type.setText(honey_type);
        Quantity.setText(quantity);
        Total.setText(total);
        Farm.setText(farm);
        Client.setText(client);
    }





    // Helper method to retrieve Sale_info from the database based on other properties
    private Sale_info getSaleFromDatabase(String honeyType, String quantity, String total, String farm, String client) {
        // Implement the logic to retrieve the Sale_info object from the database based on the provided properties
        // You may need to customize this based on your database schema and structure
        // Return null if the Sale_info is not found
        return null;
    }


}
