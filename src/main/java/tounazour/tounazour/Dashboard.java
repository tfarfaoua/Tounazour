package tounazour.tounazour;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {

    String url = "jdbc:mysql://localhost/tounazour";
    String username="root";
    String password="";
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    XYChart.Series set=new XYChart.Series<>();
    XYChart.Series set2=new XYChart.Series<>();



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

    //-------------------- barchart

    HashMap<String,Integer> barchatinfo=new HashMap<>();

    @FXML
    private CategoryAxis X;

    @FXML
    private NumberAxis Y;

    @FXML
    private BarChart<?, ?> barcharttona;


    //-------------------- linechart
    @FXML
    private CategoryAxis XL;
    @FXML
    private NumberAxis YL;

    @FXML
    private LineChart<?, ?> LineCharttona;



    HashMap<String,Integer> linechatinfo=new HashMap<>();




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


    //

    void insertintobarchart(){

        try{
            ps=con.prepareStatement("SELECT htype,sum(quantity) FROM honey group by htype");
            rs=ps.executeQuery();

            while (rs.next()){
                String ns=String.valueOf(rs.getString("htype"));
                barchatinfo.put(ns,rs.getInt("sum(quantity)"));
               // System.out.print(rs.getString("htype"));
              //  System.out.print(rs.getInt("sum(quantity)"));
            }
        }catch (Exception e){

        }

    }

    void barchartinformation(){

        this.barchatinfo.forEach((k,v) -> {
            this.set.getData().add(new XYChart.Data(k,v));
        });

        this.barcharttona.getData().addAll(this.set);
    }

    //


    void insertintolinechart(){

        try{
            ps=con.prepareStatement("SELECT sum(Quantité),DateCommande FROM `commande` group by DateCommande");
            rs=ps.executeQuery();

            while (rs.next()){
                linechatinfo.put(rs.getString("DateCommande"),rs.getInt("sum(Quantité)"));
            }
        }catch (Exception e){

        }


    }

    void linechartinformation(){

        this.linechatinfo.forEach((k,v) -> {
            this.set2.getData().add(new XYChart.Data(k,v));
        });

        this.LineCharttona.getData().addAll(this.set2);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            con = DriverManager.getConnection(this.url,username,password);
            System.out.println("Good");
        }catch (Exception e){
            System.out.println("error connection with sql !!!");
        }

        insertintobarchart();
        barchartinformation();

        insertintolinechart();
        linechartinformation();

        this.date_id.setText(java.time.LocalDate.now().toString());

        this.navbarhomeinformation();

    }
}
