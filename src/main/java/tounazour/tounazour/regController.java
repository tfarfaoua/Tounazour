package tounazour.tounazour;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tounazour.tounazour.ConnexionMysql;

public class regController implements Initializable {

    public TextField RegUser;
    public PasswordField RegPass;
    public TextField RegConfrm;
    public TextField RegPrenom;
    public TextField RegNom;
    public Button btnConnecterClicked;
    public Button btnInscrireClicked;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Parent fxml;

    public Connection cnx=null;
    public static Connection connexionDB(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection cnx= DriverManager.getConnection("jdbc:mysql://localhost:3306/tounazour","root","");

            return cnx;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @FXML
    private Parent root;

    @FXML
    void btnInscrireClicked(ActionEvent event) throws IOException {
        cnx = ConnexionMysql.connexionDB();

        String nom = RegNom.getText();
        String prenom = RegPrenom.getText();
        String username=RegUser.getText();
        String password = RegPass.getText();
        String sql = "insert into register(nom,prenom,username,password) values (?,?,?,?)";
        if (!nom.equals("") && !prenom.equals("") && !username.equals("") && !password.equals("")) {
            try {
                st = cnx.prepareStatement(sql);
                st.setString(1, nom);
                st.setString(2, prenom);
                st.setString(3, username);
                st.setString(4, password);

                st.execute();

                RegNom.setText("");
                RegPrenom.setText("");
                RegUser.setText("");
                RegPass.setText("");


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "utilisateur inscrit avec succes " , javafx.scene.control.ButtonType.OK);
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
    void btnConnecterClicked(ActionEvent event) throws IOException {
        btnConnecterClicked.getScene().getWindow().hide();
        Stage Log=new Stage();
        Parent root= FXMLLoader.load(getClass().getResource("hello-veiw.fxml"));
        Scene scene=new Scene(root);
        Log.setScene(scene);
        Log.show();

    }


    @Override
    public void initialize(URL arg0, ResourceBundle argl) {
        cnx = ConnexionMysql.connexionDB();


    }
    public PreparedStatement st;
    public ResultSet result;
    public void inscrire(MouseEvent mouseEvent) {
        cnx = ConnexionMysql.connexionDB();

        String nom = RegNom.getText();
        String prenom = RegPrenom.getText();
        String username=RegUser.getText();
        String password = RegPass.getText();
        String sql = "insert into register(nom,prenom,username,password) values (?,?,?,?)";
        if (!nom.equals("") && !prenom.equals("") && !username.equals("") && !password.equals("")) {
            try {
                st = cnx.prepareStatement(sql);
                st.setString(1, nom);
                st.setString(2, prenom);
                st.setString(3, username);
                st.setString(3, password);

                st.execute();

                RegNom.setText("");
                RegPrenom.setText("");
                RegUser.setText("");
                RegPass.setText("");


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "utilisateur inscrit avec succes " , javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "veuillez remplir tous les champs !" , javafx.scene.control.ButtonType.OK);
            alert.showAndWait();

        }
    }
}

