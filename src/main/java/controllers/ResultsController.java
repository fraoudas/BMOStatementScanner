package controllers;

import core.StatementParser;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Results;

import core.StatementParser;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ResultsController implements Initializable {
    public ListView<String> categoriesListView;
    public TableView resultsTableView;
    public TableColumn<Results, String> janTableColumn;
    public TableColumn<Results, String> febTableColumn;
    public TableColumn<Results, String> marTableColumn;
    public TableColumn<Results, String> aprTableColumn;
    public TableColumn<Results, String> mayTableColumn;
    public TableColumn<Results, String> junTableColumn;
    public TableColumn<Results, String> julTableColumn;
    public TableColumn<Results, String> augTableColumn;
    public TableColumn<Results, String> sepTableColumn;
    public TableColumn<Results, String> octTableColumn;
    public TableColumn<Results, String> novTableColumn;
    public TableColumn<Results, String> decTableColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        janTableColumn.setCellValueFactory(new PropertyValueFactory<>("janResults"));
        febTableColumn.setCellValueFactory(new PropertyValueFactory<>("febResults"));
        marTableColumn.setCellValueFactory(new PropertyValueFactory<>("marResults"));
        aprTableColumn.setCellValueFactory(new PropertyValueFactory<>("aprResults"));
        mayTableColumn.setCellValueFactory(new PropertyValueFactory<>("mayResults"));
        junTableColumn.setCellValueFactory(new PropertyValueFactory<>("junResults"));
        julTableColumn.setCellValueFactory(new PropertyValueFactory<>("julResults"));
        augTableColumn.setCellValueFactory(new PropertyValueFactory<>("augResults"));
        sepTableColumn.setCellValueFactory(new PropertyValueFactory<>("sepResults"));
        octTableColumn.setCellValueFactory(new PropertyValueFactory<>("octResults"));
        novTableColumn.setCellValueFactory(new PropertyValueFactory<>("novResults"));
        decTableColumn.setCellValueFactory(new PropertyValueFactory<>("decResults"));

        HashMap<String, HashMap<String, Float>> rawResults = StatementParser.transactionsRecorded;
        HashMap<String, Results> formattedResults = new HashMap<>();

        for (String category : rawResults.keySet()) {
            categoriesListView.getItems().add(category);
            formattedResults.put(category, new Results(rawResults.get(category)));
        }

        categoriesListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String category = categoriesListView.getSelectionModel().getSelectedItem();
                Results selectedResults = formattedResults.get(category);
                resultsTableView.getItems().clear();
                resultsTableView.getItems().add(selectedResults);
            }
        });

    }
}
