package sample;

import com.sun.org.apache.bcel.internal.generic.GotoInstruction;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jdk.internal.util.xml.impl.Input;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main extends Application {
    Scene MainScreen, CreateAccScene;
    Button GoToLogIn, GoToAcc, CreateAcc, ExitCreateAcc;
    TextField InputUsernameSignUp;
    PasswordField InputPasswordSignUp, ConfirmPassword;

    @Override
    public void start(Stage primaryStage) throws Exception{ ;
        primaryStage.setTitle("Financial Log");

        String AccFile = "Accounts.txt";
        FinanceWrite finance = new FinanceWrite(AccFile, true); //Writer that writes the account to the file Accounts.txt

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
        InputUsernameSignUp = new TextField();
        InputPasswordSignUp = new PasswordField();
        ConfirmPassword = new PasswordField();
        InputUsernameSignUp.setPromptText("Create a Username");
        InputPasswordSignUp.setPromptText("Create a Password");
        ConfirmPassword.setPromptText("Confirm Password");
        CreateAcc = new Button("Create");
        CreateAcc.setOnAction(e -> {
            try {
                if(ConfirmPassword.getText().equals(InputPasswordSignUp.getText())) {
                    PrintWriter pw = new PrintWriter(new FileWriter(InputUsernameSignUp.getText()));
                    pw.println(InputUsernameSignUp.getText() + " ---- " + InputPasswordSignUp.getText());
                    pw.close();
                    finance.writeUser(InputUsernameSignUp.getText() + " ---- " + InputPasswordSignUp.getText());
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } finally {
                InputUsernameSignUp.clear();
                InputPasswordSignUp.clear();
                ConfirmPassword.clear();
            }

        });


        ExitCreateAcc = new Button("Exit");
        ExitCreateAcc.setOnAction(e -> {
            primaryStage.setScene(MainScreen);
        });

        VBox CreateAccSceneItems = new VBox();
        CreateAccSceneItems.getChildren().addAll(InputUsernameSignUp, InputPasswordSignUp, ConfirmPassword, CreateAcc, ExitCreateAcc);
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
