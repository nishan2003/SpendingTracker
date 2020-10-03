package sample;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class OkAlert {
    public static void popUp(String title, String alert) {

        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle(title);
        primaryStage.setMinWidth(230);

        Label label = new Label();
        label.setText(alert);
        Button Ok = new Button("Ok");
        Ok.setOnAction(e -> {
            primaryStage.close();
        });


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, Ok);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();

    }

}
