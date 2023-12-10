package tounazour.tounazour.views;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {

    String url = "jdbc:mysql://localhost/**************";
    String username="root";
    String password="";
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @FXML
    private Label employee_id;
    @FXML
    private Label honey_id;
    @FXML
    private Label bee_ss_id;
    @FXML
    private Label production_id;
    @FXML
    private Label date_id;


    public void navbarhomeinformation(){
        try{
            ps=con.prepareStatement("SELECT COUNT(*) as nb FROM reservation");
            rs=ps.executeQuery();

            while (rs.next()){
                this.employee_id.setText(rs.getString("nb"));
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            con = DriverManager.getConnection(this.url,username,password);
            System.out.println("Good");
        }catch (Exception e){
            System.out.println("error connection with sql !!!");
        }

        this.date_id.setText(java.time.LocalDate.now().toString());

    }
}
