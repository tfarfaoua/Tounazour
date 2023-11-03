module tounazour.tounazour {
    requires javafx.controls;
    requires javafx.fxml;


    opens tounazour.tounazour to javafx.fxml;
    exports tounazour.tounazour;
}