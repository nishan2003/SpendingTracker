package financelog;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.io.IOException;

public class BudgetSetWindow {
    public static int budget;
    boolean b;
    public void popUp(String filename) {

        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle("Welcome");
        primaryStage.setMinWidth(230);
        Label label = new Label();
        label.setText("Welcome to Spending Tracker! Please set a weekly budget.");
        label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,15));
        Button Ok = new Button("Ok");
        Ok.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,15));
        Ok.setStyle("-fx-background-color: #FFFFFF");
        TextField text = new TextField();
        text.setMaxWidth(250);
        text.setPromptText("Enter a Budget");
        Ok.setOnAction(e -> {

            try {
                b = true;
                budget = Integer.parseInt(text.getText());
                try {
                    FinanceWrite fw = new FinanceWrite(filename, true);
                    fw.writeUser(Boolean.toString(b));
                    fw.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                try {
                    FinanceWrite fw = new FinanceWrite(filename, true);
                    fw.writeUser(Integer.toString(budget));
                    fw.close();
                    primaryStage.close();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } catch(NumberFormatException ex) {
                OkAlert.popUp("Error", "Please enter a number.", Color.RED, Color.WHITE);
            }

        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, text, Ok);
        layout.setAlignment(Pos.CENTER);
        BackgroundFill b = new BackgroundFill(Color.WHITE, new CornerRadii(1), null);
        layout.setBackground(new Background(b));

        Scene scene = new Scene(layout);
        primaryStage.setResizable(false);
        primaryStage.setWidth(400);
        primaryStage.setHeight(200);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();

    }

}

