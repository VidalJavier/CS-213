package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.SongLibController;

public class SongLib extends Application {

    public static void main(String[] args){
	    launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/SongLib.fxml"));
            GridPane root = loader.load();

            SongLibController listController = loader.getController();
            listController.start(primaryStage);

            Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
