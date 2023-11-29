module tounazour.tounazour {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;



    opens tounazour.tounazour  to javafx.fxml;
    exports tounazour.tounazour ;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    exports tounazour.tounazour.controllers;
    opens tounazour.tounazour.controllers to javafx.fxml;
    exports tounazour.tounazour.models;
    opens tounazour.tounazour.models to javafx.fxml;
    exports tounazour.tounazour.views;
    opens tounazour.tounazour.views to javafx.fxml;
}