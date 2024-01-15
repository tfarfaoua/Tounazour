package tounazour.tounazour;

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

    String url = "jdbc:mysql://localhost/tounazour";
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
            ps=con.prepareStatement("SELECT COUNT(*) as nb FROM Employee");
            rs=ps.executeQuery();

            while (rs.next()){
                this.employee_id.setText(rs.getString("nb"));
            }
        }catch (Exception e){
            System.out.println(e);
        }

        try{
            ps=con.prepareStatement("SELECT COUNT(*) as nb FROM honey");
            rs=ps.executeQuery();

            while (rs.next()){
                this.honey_id.setText(rs.getString("nb"));
            }
        }catch (Exception e){
            System.out.println(e);
        }
        try{
            ps=con.prepareStatement("SELECT COUNT(*) as nb FROM bee_supplies");
            rs=ps.executeQuery();

            while (rs.next()){
                this.bee_ss_id.setText(rs.getString("nb"));
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

        this.navbarhomeinformation();

    }
}
