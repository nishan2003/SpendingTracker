package sample;

import com.sun.org.apache.bcel.internal.generic.GotoInstruction;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    Scene MainScreen, CreateAccScene;
    Button GoToLogIn, GoToAcc, CreateAcc, ExitCreateAcc;
    TextField InputUsername;

    @Override
    public void start(Stage primaryStage) throws Exception{ ;
        primaryStage.setTitle("Financial Log");

        //Main Screen Buttons, Labels, etc.
        GoToLogIn = new Button("Log In");
        GoToLogIn.setOnAction(e -> {
            System.out.println("Nothing Here Yet");
        });

        GoToAcc = new Button("Sign Up");
        GoToAcc.setOnAction(e -> {
            primaryStage.setScene(CreateAccScene);
        });

        HBox MainScreenItems = new HBox();
        MainScreenItems.getChildren().addAll(GoToLogIn, GoToAcc);
        MainScreenItems.setAlignment(Pos.CENTER);
        MainScreen = new Scene(MainScreenItems);

        //Sign up Scene Buttons, Labels, etc.
        InputUsername = new TextField();
        CreateAcc = new Button("Create");
        CreateAcc.setOnAction(e -> {
            System.out.println("Nothing Here Yet");
        });

        ExitCreateAcc = new Button("Exit");
        ExitCreateAcc.setOnAction(e -> {
            primaryStage.setScene(MainScreen);
        });

        VBox CreateAccSceneItems = new VBox();
        CreateAccSceneItems.getChildren().addAll(InputUsername, CreateAcc, ExitCreateAcc);
        CreateAccSceneItems.setAlignment(Pos.CENTER);
        CreateAccScene = new Scene(CreateAccSceneItems);

        primaryStage.setHeight(200);
        primaryStage.setWidth(200);
        primaryStage.setScene(MainScreen);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
