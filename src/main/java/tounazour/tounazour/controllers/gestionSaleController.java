package tounazour.tounazour.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.w3c.dom.events.MouseEvent;
import tounazour.tounazour.controllers.SaleController;
import tounazour.tounazour.models.Sale_info;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class gestionSaleController {
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

    @FXML
    public void getAdd(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("Sale_formulaire.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SaleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

