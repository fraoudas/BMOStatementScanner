package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.AlertBox;
import helpers.DatabaseHelper;
import core.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateKeywordsController implements Initializable {

    public Stage window = Main.getWindow();

    public TextField categoryTextField;
    public ComboBox<String> categoryComboBox;
    public TextField keywordTextField;

    public ListView<String> categoriesListView;
    public ListView<String> keywordsListView;

    public void refresh() {
        for (String s : Main.getDatabase().getCategories()) {
            categoriesListView.getItems().add(s);
            categoryComboBox.getItems().add(s);
        }
    }

    public void backClick() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/MainMenuScene.fxml"));
        Parent updateKeywordsParent = loader.load();
        Scene mainMenuScene = new Scene(updateKeywordsParent);
        window.setScene(mainMenuScene);
        window.show();
    }

    public void addClick() throws IOException {
        String category = "";
        String keyword = "";

        DatabaseHelper db = Main.getDatabase();

        if (!categoryTextField.getText().isEmpty()) {
            category = categoryTextField.getText();
        } else {
            if (!categoryComboBox.getSelectionModel().isEmpty()) {
                category = categoryComboBox.getSelectionModel().getSelectedItem();
            }
        }

        if (!keywordTextField.getText().isEmpty()) {
            keyword = keywordTextField.getText();
        }

        boolean res = false;
        if (!category.isEmpty() && !keyword.isEmpty()) {
            res = db.addKeyword(category, keyword);
        }

        if (!res) {
            AlertBox.display("ERROR", "There was an error adding your keyword. Please check values and make sure your keywords are not duplicated.");
        } else {
            AlertBox.display("Successful", "The category and/or keyword were successfully added.");
        }
        refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoriesListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!(categoriesListView.getSelectionModel().getSelectedItem() == null)) {
                    String category = categoriesListView.getSelectionModel().getSelectedItem();
                    keywordsListView.getItems().clear();
                    for (String s : Main.getDatabase().getKeywords(category)) {
                        keywordsListView.getItems().add(s);
                    }
                } else {
                    AlertBox.display("Error", "Error selecting category.");
                }
            }
        });
        refresh();

        categoryTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!categoryTextField.getText().isEmpty()) {
                    categoryComboBox.setDisable(true);
                    categoryComboBox.setOpacity(0.8);
                } else {
                    categoryComboBox.setDisable(false);
                    categoryComboBox.setOpacity(1);
                }
            }
        });
    }
}
