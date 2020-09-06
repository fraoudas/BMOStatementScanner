package controllers;

import core.StatementParser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.AlertBox;
import core.Main;
import models.Transaction;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StatementScannerController {

    public Stage window = Main.getWindow();
    List<File> allStatements;
    public ListView<String> statementListView;

    public TableView<Transaction> transactionsNotRecordedTableView;
    public TableColumn<Transaction, String> dateTableColumn;
    public TableColumn<Transaction, String> descriptionTableColumn;
    public TableColumn<Transaction, String> amountTableColumn;
    public TableColumn<Transaction, String> typeTableColumn;

    public void browseClick() throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Statement PDFs");
        allStatements = chooser.showOpenMultipleDialog(window);

        for (int i = 0; i < allStatements.size(); i++) {
            if (allStatements.get(i).getName().endsWith(".pdf")) {
                statementListView.getItems().add(allStatements.get(i).getName());
            } else {
                AlertBox.display("ERROR", "Some of these files are invalid.");
            }
        }
    }

    public void scanClick() throws IOException {
        ArrayList<Transaction> allTransactions = new ArrayList<>();
        for (File f : allStatements) {
            ArrayList<Transaction> transactions = StatementParser.parse(f);
            allTransactions.addAll(transactions);
        }

        StatementParser.read(allTransactions);
        ArrayList<Transaction> transactionsNotRecorded = StatementParser.getNotRecorded();

        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("desc"));
        amountTableColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        for (Transaction t : transactionsNotRecorded) {
            transactionsNotRecordedTableView.getItems().add(t);
        }


        AlertBox.display("Done", "Scanning has finished.");
    }

    public void backClick() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/MainMenuScene.fxml"));
        Parent statementScannerParent = loader.load();
        Scene mainMenuScene = new Scene(statementScannerParent);
        window.setScene(mainMenuScene);
        window.show();
    }

    public void viewResultsClick() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/ResultsScene.fxml"));
        Parent statementScannerParent = loader.load();
        Scene viewResultsScene = new Scene(statementScannerParent);
        window.setScene(viewResultsScene);
        window.show();
    }


}
