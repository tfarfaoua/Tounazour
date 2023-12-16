package tounazour.tounazour ;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.stage.StageStyle;


import java.io.IOException;


public class  Main extends Application {
    private double  xOffset=0;
    private double  yOffset=0;


    @Override
    public void start(Stage stage) throws IOException {
      /*  FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 417);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
       // Image image=new Image(getClass().getResourceAsStream("img/app logo.png"));
       // stage.getIcons().add(image);
        stage.setTitle("Tounazour");
        stage.initStyle(StageStyle.UNDECORATED);

        scene.setOnMousePressed(mouseEvent -> {
            xOffset=mouseEvent.getScreenX();
            yOffset=mouseEvent.getScreenY();
        });
        scene.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getSceneX() - xOffset);
            stage.setY(mouseEvent.getSceneY() - yOffset);
        });

        stage.setScene(scene);
        stage.show();
    }*/
       Parent root= FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Honey");
        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}