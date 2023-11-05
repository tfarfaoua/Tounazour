package tounazour.tounazour;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private BorderPane body;

    @FXML
    private AnchorPane app;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void dashboard(MouseEvent mouseEvent) {
        loadPage("dashboard");
    }


    public void supplies(MouseEvent mouseEvent) {
        loadPage("bee_supplies");
    }

    public void home(MouseEvent mouseEvent) {
        loadPage("Home");
    }

    public void command(MouseEvent mouseEvent) {
        loadPage("Commend");
    }
    public void exportation(MouseEvent mouseEvent) {
        loadPage("exportation");
    }
    public void importation(MouseEvent mouseEvent) {
        loadPage("importation");
    }

    private void loadPage(String page) {
        Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        body.setCenter(root);
    }
}