package financelog;

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
        Button Yes = new Button("Yes");
        Yes.setOnAction(e -> {
            answer = true;
            primaryStage.close();
        });
        Yes.setStyle("-fx-background-color: limegreen; -fx-text-fill: white;");

        Button No = new Button("No");
        No.setOnAction(e -> {
            answer = false;
            primaryStage.close();
        });
        No.setStyle("-fx-background-color: crimson; -fx-text-fill: white;");

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, Yes, No);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();

        return answer;
    }

}
