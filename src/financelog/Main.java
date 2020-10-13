package financelog;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


import java.io.*;

import java.util.Scanner;

public class Main extends Application {
    Scene MainScreen, CreateAccScene, LoginScene, MainMenuScene;
    Button CreateAcc, ExitCreateAcc, Login, ExitLogin;
    Hyperlink GoToAcc;
    TextField InputUsernameSignUp, InputUserLogin;
    PasswordField InputPasswordSignUp, ConfirmPassword, InputPasswordLogin;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Login");

        String AccFile = "Accounts.txt";
        FinanceWrite finance = new FinanceWrite(AccFile, true); //Writer that writes the account to the file Accounts.txt

        //Main Screen Buttons, Labels, etc.
        Label MainScreenLabel = new Label("Spending Tracker");
        MainScreenLabel.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,20));


        GoToAcc = new Hyperlink("New User? Sign up here");
        GoToAcc.setStyle("-fx-border-color: transparent;");
        GoToAcc.setOnAction(e -> {
            primaryStage.setScene(CreateAccScene);
        });
        VBox LabelBox = new VBox();
        LabelBox.getChildren().addAll(MainScreenLabel);
        LabelBox.setAlignment(Pos.CENTER);

        HBox MainScreenItems = new HBox();
        MainScreenItems.getChildren().addAll(GoToAcc);
        MainScreenItems.setAlignment(Pos.CENTER);

        Image MainScreenImg = new Image(Main.class.getResourceAsStream("/resources/MainScreenIMG.jpg"));
        ImageView MainScreenImgV = new ImageView(MainScreenImg);

        VBox MainScreenImages = new VBox();
        MainScreenImages.getChildren().addAll(MainScreenImgV);
        MainScreenImages.setAlignment(Pos.CENTER);
        MainScreenImages.setMinSize(200, 200);

        BorderPane RightSideMainPane = new BorderPane();
        RightSideMainPane.setTop(LabelBox);
        RightSideMainPane.setBottom(MainScreenItems);
        RightSideMainPane.setMaxHeight(75);
        RightSideMainPane.setMaxWidth(150);


        BorderPane MainScreenPane = new BorderPane();
        MainScreenPane.setLeft(MainScreenImages);
        MainScreenPane.setCenter(RightSideMainPane);
        MainScreenPane.setMaxHeight(100);
        MainScreenPane.setMaxWidth(300);
        MainScreenPane.setStyle("-fx-background-color: #FFFFFF");
        MainScreen = new Scene(MainScreenPane);

        //Sign up Scene Buttons, Labels, etc.
        Label SignUp = new Label("Sign Up");
        SignUp.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 20));
        InputUsernameSignUp = new TextField();
        InputUsernameSignUp.setMaxWidth(800);
        InputPasswordSignUp = new PasswordField();
        InputPasswordSignUp.setMaxWidth(800);
        ConfirmPassword = new PasswordField();
        ConfirmPassword.setMaxWidth(800);
        InputUsernameSignUp.setPromptText("Create a Username");
        InputPasswordSignUp.setPromptText("Create a Password");
        ConfirmPassword.setPromptText("Confirm Password");
        CreateAcc = new Button("Create");
        CreateAcc.setStyle("-fx-background-color: #FFFFFF");
        CreateAcc.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 15));
        CreateAcc.setOnAction(e -> {
            AccountCreate.createAcc(InputPasswordSignUp, InputUsernameSignUp, ConfirmPassword, primaryStage, MainScreen, finance);
        });

        ExitCreateAcc = new Button("Cancel");
        ExitCreateAcc.setStyle("-fx-background-color: #FFFFFF");
        ExitCreateAcc.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 15));
        ExitCreateAcc.setOnAction(e -> {
            InputUsernameSignUp.clear();
            InputPasswordSignUp.clear();
            ConfirmPassword.clear();
            primaryStage.setScene(MainScreen);
        });

        VBox CreateAccSceneItems = new VBox();
        CreateAccSceneItems.setSpacing(5);
        CreateAccSceneItems.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(1), null)));
        CreateAccSceneItems.getChildren().addAll(SignUp, InputUsernameSignUp, InputPasswordSignUp, ConfirmPassword, CreateAcc, ExitCreateAcc);
        CreateAccSceneItems.setAlignment(Pos.CENTER);
        CreateAccScene = new Scene(CreateAccSceneItems);

        //Login Screen
        Label LoginLabel = new Label("Sign In");
        LoginLabel.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 13));
        InputUserLogin = new TextField();
        InputPasswordLogin = new PasswordField();
        InputUserLogin.setPromptText("Username");
        InputPasswordLogin.setPromptText("Password");
        Login = new Button("→");
        Login.setStyle("-fx-background-color: #FFFFFF");
        Login.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 15));
        Login.setOnAction(e -> {
            try {
                CheckLogin.login(InputUserLogin, InputPasswordLogin, primaryStage, MainMenuScene);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });

        VBox LoginScreenItems = new VBox();
        LoginScreenItems.setSpacing(10);
        LoginScreenItems.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(1), null)));
        LoginScreenItems.getChildren().addAll(LoginLabel, InputUserLogin, InputPasswordLogin, Login);
        LoginScreenItems.setAlignment(Pos.CENTER);
        RightSideMainPane.setCenter(LoginScreenItems);


        //The Main Menu Screen when a user successfully logs in.
        BorderPane MainMenuPane = new BorderPane();
        ProgressBar pb = new ProgressBar();
        pb.setProgress(0.5);
        pb.setStyle("-fx-text-box-border: ##FFFFFF");
        pb.setStyle("-fx-control-inner-background: ##FFFFFF");
        pb.setStyle("-fx-accent: #bc13fe");
        Label MainMenuLabel = new Label("Main Menu");
        MainMenuLabel.setFont(Font.font("Aviner", 50));
        MainMenuLabel.setTextFill(Color.WHITE);
        HBox MainMenuItems = new HBox();
        MainMenuPane.setStyle("-fx-background-color: #bc13fe");
        MainMenuPane.setCenter(pb);
        MainMenuScene = new Scene(MainMenuPane);


        primaryStage.setHeight(650);
        primaryStage.setWidth(900);
        primaryStage.setResizable(false);
        primaryStage.setScene(MainScreen);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
