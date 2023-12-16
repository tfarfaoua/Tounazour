package tounazour.tounazour.controllers;




import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tounazour.tounazour.ConnexionMysql;
import tounazour.tounazour.models.Commande;

import java.sql.*;
import java.time.LocalDate;

public class Commendcontroller {
    @FXML
    TextField name ,produit ,quantite,id;
    @FXML
    DatePicker date;
    @FXML
    TableView<Commande> tablecommandes;
    @FXML
    TableColumn<Commande,String> columnName = new TableColumn<>();
    @FXML
    TableColumn<Commande,Integer> columnId = new TableColumn<>();
    @FXML
    TableColumn<Commande,String> columnDate = new TableColumn<>();
    @FXML
    TableColumn<Commande,String> columnProduit = new TableColumn<>();
    @FXML
    TableColumn<Commande,Integer> columnQuantite = new TableColumn<>();
    Connection con;
    PreparedStatement stmt ;
    ResultSet rs ;







    public void initialize(){

        try {
            con= ConnexionMysql.connexionDB();
            System.out.println("good");

        }catch (Exception e) {
            System.out.println(e.toString());
        }

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnProduit.setCellValueFactory(new PropertyValueFactory<>("produit"));
        columnQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        getData();
        getItem();
    }

@FXML
    void saveDate(){
    int commandeQT =Integer.parseInt(quantite.getText());
  try {
      stmt = con.prepareStatement("INSERT INTO `commande`(ClientName, `ProduitCommander`,`Quantité`, `DateCommande`) VALUES (?,?,?,? )");
         stmt.setString(1,name.getText());
      stmt.setString(2,produit.getText());
      stmt.setInt(3,commandeQT);
      stmt.setString(4,date.getValue().toString());
      stmt.executeUpdate();

      System.out.println("ajoutit commande");

  }catch(Exception e){
      System.out.println(e.toString());
  }

  getData();

    }

    void getData (){

        tablecommandes.getItems().clear();
        System.out.println("khwit la table");

        try {
            stmt = con.prepareStatement("SELECT * FROM `commande`  ");
            rs = stmt.executeQuery();
            System.out.println("jbt les donnes mn la base");
            while (rs.next()){
                Commande commande = new Commande(rs.getString("ClientName"), rs.getString("DateCommande"), rs.getString("ProduitCommander"), rs.getInt("Quantité"),rs.getInt("Id") );
                tablecommandes.getItems().add(commande);
            }
            System.out.println("3mmert la table");

        }catch (Exception e){
            System.out.println(e.toString());
        }

    }
    /* @FXML
    void delete(ActionEvent event) {
        int id=tablecommandes.getSelectionModel().getSelectedItem().getId();
        try {
            stmt=con.prepareStatement("Delete FROM `commande` WHERE id='"+id+"'");
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        clear();
        uploadChauffeurInfo();
        emptyinput();
    }
    void emptyinput(){
        this.name.clear();
        this.id.clear();
        this.quantite.clear();

    }

    void uploadChauffeurInfo(){
        try{
            stmt=con.prepareStatement("SELECT * FROM `commande`");
            rs=stmt.executeQuery();

            while (rs.next()){
                Commande commande = new Commande(rs.getString("ClientName"), rs.getString("DateCommande"), rs.getString("ProduitCommander"), rs.getInt("Quantité"),rs.getInt("Id") );
                tablecommandes.getItems().add(commande);
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }*/
    void getItem(){
        tablecommandes.setOnMouseClicked(event ->{

            name.setText(tablecommandes.getSelectionModel().getSelectedItems().getFirst().getName());

            date.setValue(LocalDate.parse((tablecommandes.getSelectionModel().getSelectedItems().getFirst().getDate())) );
            produit.setText(tablecommandes.getSelectionModel().getSelectedItems().getFirst().getProduit());
            quantite.setText(String.valueOf(tablecommandes.getSelectionModel().getSelectedItems().getFirst().getQuantite()));
            id.setText(String.valueOf(tablecommandes.getSelectionModel().getSelectedItems().getFirst().getId()));
        } );
    }
    @FXML
    void clear(){
        name.clear();
        date.getEditor().clear();
        produit.clear();
        quantite.clear();
        id.clear();

    }


}