package financelog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Menu {

    public static void openMainMenu(FinanceRead financeRead) throws FileNotFoundException {

        LinkedListQueue q = financeRead.user_data;

        q.dequeue();
        q.dequeue();
        Object budget_object = q.peekFront();
        int budget_int = Integer.parseInt((String) budget_object);


        Stage primaryStage = new Stage();
        //TableView Code
        TableColumn<TableItems, String> itemColumn = new TableColumn<>("Item");
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("Item"));

        TableColumn<TableItems, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));

        TableView table = new TableView<>();

        table.setStyle("-fx-selection-bar: gold;");

        table.getColumns().addAll(itemColumn, priceColumn);
        table.setPrefWidth(150);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList Schedules = FXCollections.observableArrayList();

        //Textfields, buttons, and labels for the Main Menu

        TextField ItemInput = new TextField();
        TextField PriceInput = new TextField();

        Label LogLabel = new Label("Spending Log");
        Label budget_label = new Label("Your Weekly Budget: " + "$" + (String) budget_object);

        LogLabel.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,25));
        budget_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,35));

        //Inputs for the table view
        ItemInput.setPromptText("Item");
        PriceInput.setPromptText("Price");

        Button add = new Button(" Add ");
        add.setStyle("-fx-background-color: #FFFFFF");
        add.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 11));
        add.setOnAction(e -> {
            Schedules.add(new TableItems(ItemInput.getText(), Integer.parseInt(PriceInput.getText())));
            ItemInput.clear();
            PriceInput.clear();
            table.setItems(Schedules);
        });

        Button delete = new Button("Delete"); //Deletes a selected row in the tableview
        delete.setStyle("-fx-background-color: #FFFFFF");
        delete.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 11));
        delete.setOnAction(e -> {
            ObservableList<TableItems> SelectedRow, AllRows;
            AllRows = table.getItems();
            SelectedRow = table.getSelectionModel().getSelectedItems();
            SelectedRow.forEach(AllRows::remove);
        });

        VBox TableBox = new VBox();
        HBox buttons = new HBox();
        buttons.getChildren().addAll(add, delete);
        buttons.setAlignment(Pos.CENTER);
        TableBox.getChildren().addAll(LogLabel, table, ItemInput, PriceInput, buttons);
        BorderPane MainMenuPane = new BorderPane();
        BorderPane right_pane = new BorderPane();
        right_pane.setTop(budget_label);
        right_pane.setPadding(new Insets(0, 30, 0, 0));
        TableBox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(1), null)));
        MainMenuPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(1), null)));
        MainMenuPane.setLeft(TableBox);
        MainMenuPane.setRight(right_pane);
        MainMenuPane.setPadding(new Insets(20,20,20,20));
        Scene TableViewScene = new Scene(MainMenuPane);

        primaryStage.setScene(TableViewScene);
        primaryStage.setTitle("Menu");
        primaryStage.setResizable(false);
        primaryStage.setWidth(900);
        primaryStage.setHeight(650);
        primaryStage.show();

    }

}
