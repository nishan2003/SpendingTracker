package financelog;

import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class YesNoAlert {
    static Boolean answer;
    public static boolean popUp(String title, String alert) {

        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle(title);
        primaryStage.setMinWidth(230);

        Label label = new Label();
        label.setText(alert);
        label.setTextFill(Color.WHITE);
        Button Yes = new Button("Proceed");
        Yes.setOnAction(e -> {
            answer = true;
            primaryStage.close();
        });
        Yes.setStyle("-fx-background-color: white; -fx-text-fill: black;");

        Button No = new Button("Cancel");
        No.setOnAction(e -> {
            answer = false;
            primaryStage.close();
        });
        No.setStyle("-fx-background-color: white; -fx-text-fill: black;");

        HBox h = new HBox();
        h.getChildren().addAll(Yes, No);
        h.setAlignment(Pos.CENTER);
        h.setSpacing(10);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, h);
        layout.setAlignment(Pos.CENTER);
        BackgroundFill b = new BackgroundFill(Color.RED, new CornerRadii(1), null);
        layout.setBackground(new Background(b));
        Scene scene = new Scene(layout);
        primaryStage.setHeight(150);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();

        return answer;
    }

}
