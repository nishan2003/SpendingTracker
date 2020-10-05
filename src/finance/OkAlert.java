package finance;

import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class OkAlert {
    public static void popUp(String title, String alert, Color c) {

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
        BackgroundFill b = new BackgroundFill(c, new CornerRadii(1), null);
        layout.setBackground(new Background(b));

        Scene scene = new Scene(layout);
        primaryStage.setWidth(300);
        primaryStage.setHeight(150);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();

    }

}
