package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import core.Main;

import java.io.IOException;

import static javafx.application.ConditionalFeature.FXML;

public class MainMenuController {

    public Stage window = Main.getWindow();

    public void startClick() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/StatementScannerScene.fxml"));
        Parent statementScannerParent = loader.load();
        Scene statementScannerScene = new Scene(statementScannerParent);
        window.setScene(statementScannerScene);
        window.show();
    }

    public void updateKeywordsClick() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/UpdateKeywordsScene.fxml"));
        Parent updateKeywordsParent = loader.load();
        Scene updateKeywordsScene = new Scene(updateKeywordsParent);
        window.setScene(updateKeywordsScene);
        window.show();
    }

    public void exitClick() {
        Main.getWindow().close();
    }



}
