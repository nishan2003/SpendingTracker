package financelog;

import com.sun.xml.internal.ws.assembler.jaxws.TerminalTubeFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class MainMenu {
    public static void openMainMenu() {
        Stage primaryStage = new Stage();

        TableColumn<TableItems, String> itemColumn = new TableColumn<>("Purchased Item");
        itemColumn.setMinWidth(320);
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("Item"));

        TableColumn<TableItems, Integer> priceColumn = new TableColumn<>("Price of Item");
        priceColumn.setMinWidth(320);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));

        ObservableList Schedules = FXCollections.observableArrayList();

        TableView table = new TableView<>();
        table.getColumns().addAll(itemColumn, priceColumn);

        VBox m = new VBox();
        m.getChildren().addAll(table);

        Scene s = new Scene(m);

        primaryStage.setScene(s);


        primaryStage.setTitle("Menu");
        primaryStage.setResizable(false);
        primaryStage.setWidth(900);
        primaryStage.setHeight(650);
        primaryStage.show();

    }

}
