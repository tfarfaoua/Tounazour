package tounazour.tounazour.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.events.MouseEvent;
import tounazour.tounazour.ConnexionMysql;
import tounazour.tounazour.models.Sale_info;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleController implements Initializable {
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Sale_info, Integer> id;
    @FXML
    private TableColumn<Sale_info, String> Honey_type;
    @FXML
    private TableColumn<Sale_info, String> Quantity;
    @FXML
    private TableColumn<Sale_info, String> Total;
    @FXML
    private TableColumn<Sale_info, String> Farm;
    @FXML
    private TableColumn<Sale_info, String> Client;

    ObservableList<Sale_info> Tlist = FXCollections.observableArrayList();
    String query = null;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Sale_info sale = null;
    //add sale
    @FXML
    public void getAdd(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/tounazour/tounazour/Sale_formulaire.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SaleFormulaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        Honey_type.setCellValueFactory(new PropertyValueFactory<>("Honey_type"));
        Quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        Total.setCellValueFactory(new PropertyValueFactory<>("Total"));
        Farm.setCellValueFactory(new PropertyValueFactory<>("Farm"));
        Client.setCellValueFactory(new PropertyValueFactory<>("Client"));
        tableView.setItems(Tlist);

        try {
            ConnexionMysql connectionClass = new ConnexionMysql();
            Connection conn = connectionClass.connexionDB();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM `Sale_info`;");

            while (rs.next()) {
                Tlist.add(new Sale_info(
                        rs.getInt("id"),
                        rs.getString("Honey_type"),
                        rs.getString("Quantity"),
                        rs.getString("Total"),
                        rs.getString("Farm"),
                        rs.getString("Client")
                ));
                tableView.setItems(Tlist);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //DELETE SALE

    public void Delete(javafx.scene.input.MouseEvent mouseEvent) {
        Sale_info em = (Sale_info) tableView.getSelectionModel().getSelectedItem();
        String id = String.valueOf(em.idProperty().getValue());
        try {

            ConnexionMysql connectionClass = new ConnexionMysql();
            Connection conn= connectionClass.connexionDB();


            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM `Sale_info` WHERE id='"+id+"';");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int selected = tableView.getSelectionModel().getSelectedIndex();
        tableView.getItems().remove(selected);

    }

    // UPDATE Sales
    @FXML
    public void updateSale(javafx.scene.input.MouseEvent mouseEvent) {
        Sale_info sale = (Sale_info) tableView.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/tounazour/tounazour/Sale_formulaire.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            Logger.getLogger(SaleFormulaireController.class.getName()).log(Level.SEVERE, null, e);
        }

        SaleFormulaireController saleFormulaireController = loader.getController();
        saleFormulaireController.setUpdate(true);
        saleFormulaireController.setTextField(sale.getHoney_type(), sale.getQuantity(), sale.getTotal(), sale.getFarm(), sale.getClient());

        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

}