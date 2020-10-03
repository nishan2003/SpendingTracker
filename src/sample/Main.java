package sample;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.*;

import java.util.Scanner;

public class Main extends Application {
    Scene MainScreen, CreateAccScene, LoginScene;
    Button GoToLogIn, GoToAcc, CreateAcc, ExitCreateAcc, Login, ExitLogin;
    TextField InputUsernameSignUp, InputUserLogin;
    PasswordField InputPasswordSignUp, ConfirmPassword, InputPasswordLogin;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Financial Log");

        String AccFile = "Accounts.txt";
        FinanceWrite finance = new FinanceWrite(AccFile, true); //Writer that writes the account to the file Accounts.txt

        //Main Screen Buttons, Labels, etc.
        Label MainScreenLabel = new Label("Financial Log");

        GoToLogIn = new Button("Log In");
        GoToLogIn.setOnAction(e -> {
            primaryStage.setScene(LoginScene);
        });

        GoToAcc = new Button("Sign Up");
        GoToAcc.setOnAction(e -> {
            primaryStage.setScene(CreateAccScene);
        });
        VBox LabelBox = new VBox();
        LabelBox.getChildren().addAll(MainScreenLabel);
        LabelBox.setAlignment(Pos.CENTER);

        HBox MainScreenItems = new HBox();
        MainScreenItems.getChildren().addAll(GoToLogIn, GoToAcc);
        MainScreenItems.setAlignment(Pos.CENTER);
        BorderPane MainScreenPane = new BorderPane();
        MainScreenPane.setTop(LabelBox);
        MainScreenPane.setCenter(MainScreenItems);
        MainScreen = new Scene(MainScreenPane);

        //Sign up Scene Buttons, Labels, etc.
        Label SignUp = new Label("Sign Up");
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
                    FinanceWrite fw = new FinanceWrite(InputUsernameSignUp.getText(), false);
                    fw.writeUser(InputUsernameSignUp.getText() + " ---- " + InputPasswordSignUp.getText());
                    fw.close();
                    finance.writeUser(InputUsernameSignUp.getText() + " ---- " + InputPasswordSignUp.getText());
                    finance.flush();
                    OkAlert.popUp("Congratulations!", "Your Account has been created.");
                    primaryStage.setScene(MainScreen);

                }
                else {
                    OkAlert.popUp("Error", "Confirmed password does not match the original.");
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
            InputUsernameSignUp.clear();
            InputPasswordSignUp.clear();
            ConfirmPassword.clear();
            primaryStage.setScene(MainScreen);
        });

        VBox CreateAccSceneItems = new VBox();
        CreateAccSceneItems.getChildren().addAll(SignUp, InputUsernameSignUp, InputPasswordSignUp, ConfirmPassword, CreateAcc, ExitCreateAcc);
        CreateAccSceneItems.setAlignment(Pos.CENTER);
        CreateAccScene = new Scene(CreateAccSceneItems);

        //Login Screen
        Label LoginLabel = new Label("Login");
        InputUserLogin = new TextField();
        InputPasswordLogin = new PasswordField();
        InputUserLogin.setPromptText("Username");
        InputPasswordLogin.setPromptText("Password");
        Login = new Button("Login");
        Login.setOnAction(e -> {
            String[] Array = new String[15];
            String line = null;
            try {
                Scanner fileReader = new Scanner(new File(InputUserLogin.getText()));
                while (fileReader.hasNext()) {
                    line = fileReader.nextLine();
                }
                Array = line.split(" ---- ");
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }

            if(InputUserLogin.getText().equals(Array[0]) && InputPasswordLogin.getText().equals(Array[1])) {
                System.out.println("Nice");
            }
            else {
                OkAlert.popUp("Error", "Username or Password is incorrect.");
            }
            InputUserLogin.clear();
            InputPasswordLogin.clear();

        });
        ExitLogin = new Button("Exit");
        ExitLogin.setOnAction(e -> {
            InputUserLogin.clear();
            InputPasswordLogin.clear();
            primaryStage.setScene(MainScreen);
        });
        VBox LoginScreenItems = new VBox();
        LoginScreenItems.getChildren().addAll(LoginLabel, InputUserLogin, InputPasswordLogin, Login, ExitLogin);
        LoginScreenItems.setAlignment(Pos.CENTER);
        LoginScene = new Scene(LoginScreenItems);


        primaryStage.setHeight(200);
        primaryStage.setWidth(200);
        primaryStage.setScene(MainScreen);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
