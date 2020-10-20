package financelog;

import com.sun.xml.internal.ws.assembler.jaxws.TerminalTubeFactory;
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

import java.util.Scanner;

public class Menu {

    public static void openMainMenu() {
        Stage primaryStage = new Stage();

        TableColumn<TableItems, String> itemColumn = new TableColumn<>("Item");
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("Item"));

        TableColumn<TableItems, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));


        TableView table = new TableView<>();

        table.getColumns().addAll(itemColumn, priceColumn);
        table.setPrefWidth(150);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList Schedules = FXCollections.observableArrayList();

        TextField ItemInput = new TextField();
        TextField PriceInput = new TextField();

        Label LogLabel = new Label("Spending Log ");
        LogLabel.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,25));

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

        Button delete = new Button("Delete");
        delete.setStyle("-fx-background-color: #FFFFFF");
        delete.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 11));


        int budget = BudgetSetWindow.budget;
        Label budgetLabel = new Label(Integer.toString(budget));


        VBox TableBox = new VBox();
        HBox buttons = new HBox();
        buttons.getChildren().addAll(add, delete);
        buttons.setAlignment(Pos.CENTER);
        TableBox.getChildren().addAll(LogLabel, table, ItemInput, PriceInput, buttons);
        BorderPane MainMenuPane = new BorderPane();
        TableBox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(1), null)));
        MainMenuPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(1), null)));
        MainMenuPane.setLeft(TableBox);
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
