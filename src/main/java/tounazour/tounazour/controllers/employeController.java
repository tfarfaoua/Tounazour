package tounazour.tounazour.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tounazour.tounazour.ConnexionMysql;
import tounazour.tounazour.models.employe;

public class employeController implements Initializable {

    public Connection cnx;

    public TextField searchCh;
    @FXML
    private TableColumn<employe, Integer> cln_id;


    @FXML
    private TableColumn<employe, String> cln_cin;


    @FXML
    private TableColumn<String, employe> cln_tel;
    @FXML
    private TableColumn<employe, String> cln_nom;
    @FXML
    private TableColumn<String, employe> cln_ferme;
    @FXML
    private Button suppCh;




    @FXML
    private Button btndcx;



    @FXML
    private Button ajouterCh;



    @FXML
    private TextField idCh;

    @FXML
    private TextField telCh;

    @FXML
    private TextField nomCh;

    @FXML
    private TextField cinCh;
    @FXML
    private TextField fermeCh;
    @FXML
    private Button modifierCh;

    @FXML
    private AnchorPane table;

    @FXML
    private TableView<employe> ChTable;


    public ObservableList<employe> data = FXCollections.observableArrayList();

    @FXML
    private Parent fxml;



    @FXML
    void Modifier(ActionEvent event) {
        cnx = ConnexionMysql.connexionDB();

        int id =  ChTable.getSelectionModel().getSelectedItem().getId();
        String nom = nomCh.getText();
        String cin = cinCh.getText();
        String tel = telCh.getText();
        String ferme = fermeCh.getText();
        String sql = "update employe set nom=?,cin=?,tel=?,ferme=? where id=?";
        if (!nom.isEmpty() && !cin.isEmpty() && !tel.isEmpty() && !ferme.isEmpty()) {
            try {
                st = cnx.prepareStatement(sql);
                st.setString(1, nom);
                st.setString(2, cin);
                st.setString(3, tel);
                st.setString(4, ferme);
                st.setInt(5, id);
                st.execute();

                nomCh.setText("");
                cinCh.setText("");
                telCh.setText("");
                fermeCh.setText("");

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "employe modifie avec succes", javafx.scene.control.ButtonType.OK);
                alert.showAndWait();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "veuillez remplir tous les champs !" , javafx.scene.control.ButtonType.OK);
            alert.showAndWait();

        }
    }

    @FXML
    void Supprimer(ActionEvent event) {

        int id = ChTable.getSelectionModel().getSelectedItem().getId();

        String sql="delete from employe where id=?";
        try{
            st=cnx.prepareStatement(sql);
            st.setInt(1, id);
            st.execute();
            nomCh.setText("");
            cinCh.setText("");
            telCh.setText("");
            fermeCh.setText("");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "employe " + searchCh.getText() + " supprime avec succes ", javafx.scene.control.ButtonType.OK);
            alert.showAndWait();

            getAllData();
            ChTable.refresh();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void Ajouter(ActionEvent event) {
        cnx = ConnexionMysql.connexionDB();

        String nom = nomCh.getText();
        String cin = cinCh.getText();
        String tel = telCh.getText();
        String ferme = fermeCh.getText();
        String sql = "insert into employe(nom,cin,tel,ferme) values (?,?,?,?)";
        if (!nom.equals("") && !cin.equals("") && !tel.equals("")&& !ferme.equals("")) {
            try {
                st = cnx.prepareStatement(sql);
                st.setString(1, nom);
                st.setString(2, cin);
                st.setString(3, tel);
                st.setString(4, ferme);

                st.execute();

                nomCh.setText("");
                cinCh.setText("");
                telCh.setText("");
                fermeCh.setText("");

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "employe"+ nomCh.getText() + "  ajouter avec succes ", javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
                getAllData();
                ChTable.refresh();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "veuillez remplir tous les champs !" , javafx.scene.control.ButtonType.OK);
            alert.showAndWait();

        }

    }

    public PreparedStatement st;
    public ResultSet result;
    public void search(ActionEvent actionEvent) {
        cnx = ConnexionMysql.connexionDB();

        String sql = "select nom,cin,tel,ferme from employe where nom='" + searchCh.getText() + "'";
        int m = 0;
        try {
            st = cnx.prepareStatement(sql);
            result = st.executeQuery();
            if (result.next()) {
                nomCh.setText(result.getString("nom"));
                cinCh.setText(result.getString("cin"));
                telCh.setText(result.getString("tel"));
                fermeCh.setText(result.getString("ferme"));

                m=1;

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (m==0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "aucun employe trouve avec nom =" + searchCh.getText() + "", javafx.scene.control.ButtonType.OK);
            alert.showAndWait();

        }
    }

    @FXML
    public void showemploye() {
        int id =  ChTable.getSelectionModel().getSelectedItem().getId();
        System.out.println(id);
    }

    public void getAllData(){

        ChTable.getItems().clear();
    String sql = "select * from employe";
        try {
        st = cnx.prepareStatement(sql);
        result = st.executeQuery();
        while (result.next()) {
            data.add(new employe(result.getInt("id"), result.getString("nom"), result.getString("cin"), result.getString("tel"), result.getString("ferme")));
        }

        cln_id.setCellValueFactory(new PropertyValueFactory<employe, Integer>("id"));
        cln_nom.setCellValueFactory(new PropertyValueFactory<employe, String>("nom"));
        cln_cin.setCellValueFactory(new PropertyValueFactory<employe, String>("cin"));
        cln_tel.setCellValueFactory(new PropertyValueFactory<String, employe>("tel"));
        cln_ferme.setCellValueFactory(new PropertyValueFactory<String, employe>("ferme"));
        ChTable.setItems(data);

    } catch (Exception e) {
        System.out.println(e.getMessage());

    }
    }

    public void initialize(URL arg0, ResourceBundle argl) {

       // cnx = ConnexionMysql.connexionDB();
        //getAllData();
            }

    public void searchemploye(MouseEvent mouseEvent) {

        String sql = "select nom,prenom,tel,ferme from employe where nom='" + searchCh.getText() + "'";
        int m = 0;
        try {
            st = cnx.prepareStatement(sql);
            result = st.executeQuery();
            if (result.next()) {
                nomCh.setText(result.getString("nom"));
                cinCh.setText(result.getString("cin"));
                telCh.setText(result.getString("tel"));
                fermeCh.setText(result.getString("ferme"));


                m=1;


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (m == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "aucun employe trouve " + searchCh.getText() + "", javafx.scene.control.ButtonType.OK);
            alert.showAndWait();

        }
    }



}






