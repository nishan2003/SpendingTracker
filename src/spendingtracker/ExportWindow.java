package spendingtracker;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class ExportWindow {
    String name;
    Boolean cancelled;
    public String popUp() {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setHeight(200);
        window.setWidth(400);
        window.setTitle("Export to Excel");

        TextField exportname = new TextField();
        exportname.setMaxWidth(335);
        exportname.setPromptText("Enter File Name");

        Button export = new Button("Export");

        export.setOnAction(e -> {
            name = exportname.getText();
            window.close();
            cancelled = false;
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> {
            window.close();
            cancelled = true;
        });

        ClassLoader load = Thread.currentThread().getContextClassLoader();

        HBox buttons = new HBox();
        buttons.getChildren().addAll(export, cancel);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(5);

        VBox v = new VBox();
        v.getChildren().addAll(exportname, buttons);
        v.setAlignment(Pos.CENTER);
        v.setSpacing(5);

        Scene s = new Scene(v);
        s.getStylesheets().add(getClass().getResource("/resources/tutorial.css").toExternalForm());
        window.setScene(s);
        window.setResizable(false);

        window.getIcons().add(new Image(load.getResourceAsStream("app_icon.png")));
        window.showAndWait();

        return name;



    }
}

