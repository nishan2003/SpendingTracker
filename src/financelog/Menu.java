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
import java.io.IOException;
import java.util.Scanner;

public class Menu {
    int budget_int;
    int money_left;
    public void openMainMenu(FinanceRead financeRead) throws FileNotFoundException {

        LinkedListQueue q = financeRead.user_data;

        String[] user_name = ((String) q.dequeue()).split(" ---- ");
        q.dequeue();
        Object budget_object = q.peekFront();
        budget_int = Integer.parseInt((String) budget_object);
        try {
            Scanner s = new Scanner(new File(user_name[0] + "data"));
            money_left = s.nextInt();
        } catch (Exception e) {
            money_left = budget_int;
        }

        Stage primaryStage = new Stage();
        //TableView Code
        TableColumn<TableItems, String> itemColumn = new TableColumn<>("Item");
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("Item"));

        TableColumn<TableItems, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));

        TableView table = new TableView<>();

        table.setStyle("-fx-selection-bar: #bc13fe;");

        table.getColumns().addAll(itemColumn, priceColumn);
        table.setPrefWidth(150);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList Schedules = FXCollections.observableArrayList();

        //Textfields, buttons, and labels for the Main Menu

        TextField ItemInput = new TextField();
        TextField PriceInput = new TextField();

        Label LogLabel = new Label("Spending Log");
        Label budget_label = new Label("Your Weekly Budget: " + "$" + (String) budget_object);
        Label money_left_label = new Label("You have $" + Integer.toString(money_left) + " left.");

        LogLabel.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,25));
        budget_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,35));
        money_left_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,35));

        //Inputs for the table view
        ItemInput.setPromptText("Item");
        PriceInput.setPromptText("Price");

        Button add = new Button(" Add ");
        add.setStyle("-fx-background-color: #FFFFFF");
        add.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 11));
        add.setOnAction(e -> {
            try {
                Schedules.add(new TableItems(ItemInput.getText(), Integer.parseInt(PriceInput.getText())));
                table.setItems(Schedules);
                money_left = money_left - Integer.parseInt(PriceInput.getText());
                money_left_label.setText("You have $" + Integer.toString(money_left) + " left.");
                FinanceWrite f = new FinanceWrite(user_name[0] + "data", false);
                f.writeUser(Integer.toString(money_left));
                f.flush();
            }catch(NumberFormatException | IOException i) {
                OkAlert.popUp("Error", "Please add a number in the price column", Color.RED, Color.WHITE);
            } finally {
                ItemInput.clear();
                PriceInput.clear();
            }

        });

        Button delete = new Button("Delete"); //Deletes a selected row in the tableview
        delete.setStyle("-fx-background-color: #FFFFFF");
        delete.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 11));
        delete.setOnAction(e -> {
            TableItems t = (TableItems) table.getSelectionModel().getSelectedItem();
            money_left += t.getPrice();
            money_left_label.setText("You have $" + Integer.toString(money_left) + " left.");
            ObservableList<TableItems> SelectedRow, AllRows;
            AllRows = table.getItems();
            SelectedRow = table.getSelectionModel().getSelectedItems();
            SelectedRow.forEach(AllRows::remove);
            try {
                FinanceWrite f = new FinanceWrite(user_name[0] + "data",false);
                f.writeUser(Integer.toString(money_left));
                f.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(add, delete);
        buttons.setAlignment(Pos.CENTER);

        VBox budget_labels_box = new VBox();
        budget_labels_box.getChildren().addAll(budget_label, money_left_label);

        VBox TableBox = new VBox();
        TableBox.getChildren().addAll(LogLabel, table, ItemInput, PriceInput, buttons);
        TableBox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(1), null)));

        BorderPane right_pane = new BorderPane();
        right_pane.setTop(budget_labels_box);
        right_pane.setPadding(new Insets(0, 30, 0, 0));

        BorderPane MainMenuPane = new BorderPane();
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
