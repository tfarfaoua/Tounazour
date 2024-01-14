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
    private TextField id;
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
            insertData(honey_type, quantity, total, farm, client);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Your data is entered correctly");
            alert.showAndWait();
        }

    }

    private void insertData(String honeyType, String quantity, String total, String farm, String client) {
        try {
            String query = "INSERT INTO Sale_info(Honey_type, Quantity, Total, Farm, Client) VALUES (?,?,?,?,?)";
            pst = conn.prepareStatement(query);
            pst.setString(1, honeyType);
            pst.setString(2, quantity);
            pst.setString(3, total);
            pst.setString(4, farm);
            pst.setString(5, client);

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

    public void setTextField(String honey_type, String quantity, String total, String farm, String client) {
        Honey_type.setText(honey_type);
        Quantity.setText(quantity);
        Total.setText(total);
        Farm.setText(farm);
        Client.setText(client);
    }


    
    @FXML
    public void handleUpdate(MouseEvent mouseEvent) {
        // Retrieve the updated data from the text fields
        String updatedHoneyType = Honey_type.getText();
        String updatedQuantity = Quantity.getText();
        String updatedTotal = Total.getText();
        String updatedFarm = Farm.getText();
        String updatedClient = Client.getText();

        // Perform the update operation using the retrieved data
        try {
            ConnexionMysql connectionClass = new ConnexionMysql();
            Connection conn = connectionClass.connexionDB();

            // Assuming the id of the sale you want to update is stored in the id text field
            int saleId = 0; // Initialize with a default value

            // Retrieve the Sale_info object from the database based on other properties
            Sale_info existingSale = getSaleFromDatabase(updatedHoneyType, updatedQuantity, updatedTotal, updatedFarm, updatedClient);

            if (existingSale != null) {
                saleId = existingSale.getId();
            }

            // Update the Sale_info table with the new data
            String updateQuery = "UPDATE Sale_info SET Honey_type=?, Quantity=?, Total=?, Farm=?, Client=? WHERE id=?";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, updatedHoneyType);
            updateStatement.setString(2, updatedQuantity);
            updateStatement.setString(3, updatedTotal);
            updateStatement.setString(4, updatedFarm);
            updateStatement.setString(5, updatedClient);
            updateStatement.setInt(6, saleId);

            updateStatement.executeUpdate();

            // Close the SaleFormulaire stage after the update
            Stage stage = (Stage) Honey_type.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            Logger.getLogger(SaleFormulaireController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // Helper method to retrieve Sale_info from the database based on other properties
    private Sale_info getSaleFromDatabase(String honeyType, String quantity, String total, String farm, String client) {
        // Implement the logic to retrieve the Sale_info object from the database based on the provided properties
        // You may need to customize this based on your database schema and structure
        // Return null if the Sale_info is not found
        return null;
    }


}
