package financelog;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.*;

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
        DataWrite finance = new DataWrite(AccFile, true); //Writer that writes the account to the file Accounts.txt

        ClassLoader load = Thread.currentThread().getContextClassLoader();

        primaryStage.getIcons().add(new Image(load.getResourceAsStream("app_icon.png")));

        //Main Screen Buttons, Labels, etc.
        Label MainScreenLabel = new Label("Spending Tracker");
        MainScreenLabel.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,35));


        GoToAcc = new Hyperlink("New User? Sign up here");
        GoToAcc.setStyle("-fx-border-color: transparent; -fx-text-fill: white;");
        GoToAcc.setOnAction(e -> {
            primaryStage.setScene(CreateAccScene);
        });

        HBox MainScreenItems = new HBox();
        MainScreenItems.getChildren().addAll(GoToAcc);
        MainScreenItems.setAlignment(Pos.CENTER);



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
        InputUsernameSignUp.setMaxWidth(475);
        InputPasswordSignUp.setMaxWidth(475);
        InputPasswordSignUp.setPromptText("Create a Password");
        ConfirmPassword.setPromptText("Confirm Password");
        ConfirmPassword.setMaxWidth(475);
        CreateAcc = new Button("Create");
        CreateAcc.setOnAction(e -> {
            AccountCreate.createAcc(InputPasswordSignUp, InputUsernameSignUp, ConfirmPassword, primaryStage, MainScreen, finance);
        });

        ExitCreateAcc = new Button("Cancel");
        ExitCreateAcc.setOnAction(e -> {
            InputUsernameSignUp.clear();
            InputPasswordSignUp.clear();
            ConfirmPassword.clear();
            primaryStage.setScene(MainScreen);
        });

        VBox CreateAccSceneItems = new VBox();
        CreateAccSceneItems.setSpacing(5);
        CreateAccSceneItems.getChildren().addAll(SignUp, InputUsernameSignUp, InputPasswordSignUp, ConfirmPassword, CreateAcc, ExitCreateAcc);
        CreateAccSceneItems.setAlignment(Pos.CENTER);
        CreateAccScene = new Scene(CreateAccSceneItems);

        //Login Screen
        Label LoginLabel = new Label("Sign In");
        LoginLabel.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 20));
        InputUserLogin = new TextField();
        InputPasswordLogin = new PasswordField();
        InputUserLogin.setPromptText("Username");
        InputUserLogin.setMaxWidth(450);
        InputPasswordLogin.setMaxWidth(450);

        InputPasswordLogin.setPromptText("Password");
        Login = new Button("→");
        Login.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 13));
        Login.setOnAction(e -> {
            try {
                CheckLogin.login(InputUserLogin, InputPasswordLogin, primaryStage, MainMenuScene);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });

        VBox LoginScreenItems = new VBox();
        LoginScreenItems.setSpacing(15);
        LoginScreenItems.getChildren().addAll(MainScreenLabel, LoginLabel, InputUserLogin, InputPasswordLogin, Login, GoToAcc);
        LoginScreenItems.setAlignment(Pos.CENTER);

        BorderPane MainScreenPane = new BorderPane();
        MainScreenPane.setCenter(LoginScreenItems);
        MainScreen = new Scene(MainScreenPane);


        MainScreen.getStylesheets().add(getClass().getResource("/resources/loginscreen.css").toExternalForm());
        CreateAccScene.getStylesheets().add(getClass().getResource("/resources/loginscreen.css").toExternalForm());


        primaryStage.setHeight(600);
        primaryStage.setWidth(880);
        primaryStage.setResizable(false);
        primaryStage.setScene(MainScreen);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
