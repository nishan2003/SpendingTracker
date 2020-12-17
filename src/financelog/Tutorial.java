package financelog;

import javafx.scene.image.Image;
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
import java.util.ArrayList;
import java.util.Scanner;

public class Tutorial {

    Scene scene, secndpg, thirdpg;
    public void popUp(String filename) {

        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle("Welcome");
        primaryStage.setMinWidth(230);
        Label label = new Label();
        label.setText("Welcome to Spending Tracker!");
        label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,20));
        Button next_page = new Button("Next");
        Button back1 = new Button("Back");
        Button back2 = new Button("Back");
        Button done = new Button("Done");

        String tutorial_string = "This app is designed to help keep track of your purchases \n and control your spending. Start off by setting \n a monthly budget in the 'Budgets' tab.";
        String tutorial2_string = "You can add purchases in the 'Transactions' tab \n and view them in the 'View Purchases' tab. \n Lastly, you can set some saving goals in the 'Goals' tab. ";

        Label tutorial_label = new Label(tutorial_string);
        tutorial_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,16));

        Label tutorial_label2 = new Label(tutorial2_string);
        tutorial_label2.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,16));

        next_page.setOnAction(e -> {
            primaryStage.setScene(secndpg);
        });

        Button next_page2 = new Button("Next");
        next_page2.setOnAction(e -> {
            primaryStage.setScene(thirdpg);
        });

        back1.setOnAction(e -> {
            primaryStage.setScene(scene);
        });

        back2.setOnAction(e -> {
            primaryStage.setScene(secndpg);
        });

        done.setOnAction(e -> {
            primaryStage.close();
        });


        VBox secondv = new VBox(10);
        HBox buttons1 = new HBox(5);
        buttons1.getChildren().addAll(back1, next_page2);
        secondv.getChildren().addAll(tutorial_label, buttons1);
        secondv.setAlignment(Pos.CENTER);
        buttons1.setAlignment(Pos.CENTER);

        secndpg = new Scene(secondv);

        VBox thirdv = new VBox(10);
        HBox buttons2 = new HBox(5);
        buttons2.getChildren().addAll(back2, done);
        thirdv.getChildren().addAll(tutorial_label2, buttons2);
        thirdv.setAlignment(Pos.CENTER);
        buttons2.setAlignment(Pos.CENTER);

        thirdpg = new Scene(thirdv);


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, next_page);
        layout.setAlignment(Pos.CENTER);

        scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("/resources/tutorial.css").toExternalForm());
        secndpg.getStylesheets().add(getClass().getResource("/resources/tutorial.css").toExternalForm());
        thirdpg.getStylesheets().add(getClass().getResource("/resources/tutorial.css").toExternalForm());
        ClassLoader load = Thread.currentThread().getContextClassLoader();

        primaryStage.getIcons().add(new Image(load.getResourceAsStream("app_icon.png")));
        primaryStage.setResizable(false);
        primaryStage.setWidth(400);
        primaryStage.setHeight(200);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}

