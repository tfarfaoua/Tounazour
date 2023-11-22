module tounazour.tounazour {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;



    opens tounazour.tounazour  to javafx.fxml;
    exports tounazour.tounazour ;
}