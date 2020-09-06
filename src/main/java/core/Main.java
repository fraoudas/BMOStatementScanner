package core;

import helpers.DatabaseHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage window;
    public static DatabaseHelper db;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        db = new DatabaseHelper();
        window = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/MainMenuScene.fxml"));
        Parent root = loader.load();
        window.setTitle("BMO Statement Scanner");
        window.setScene(new Scene(root, 960, 540));
        window.show();
    }

    public static Stage getWindow() {
        return window;
    }

    public static DatabaseHelper getDatabase() {
        return db;
    }

}
