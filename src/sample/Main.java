package sample;


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
        MainScreenLabel.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 20));

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

        Image MainScreenImg = new Image(Main.class.getResourceAsStream("/resources/MainScreenIMG.jpg"));
        ImageView MainScreenImgV = new ImageView(MainScreenImg);

        VBox MainScreenImages = new VBox();
        MainScreenImages.getChildren().addAll(MainScreenImgV);
        MainScreenImages.setAlignment(Pos.CENTER);
        MainScreenImages.setMinSize(200, 200);
        //MainScreenImages.setMaxSize(200 ,200);

        BorderPane RightSideMainPane = new BorderPane();
        RightSideMainPane.setTop(LabelBox);
        RightSideMainPane.setCenter(MainScreenItems);
        RightSideMainPane.setMaxHeight(75);
        RightSideMainPane.setMaxWidth(150);


        BorderPane MainScreenPane = new BorderPane();
        MainScreenPane.setLeft(MainScreenImages);
        MainScreenPane.setCenter(RightSideMainPane);
        MainScreenPane.setMaxHeight(100);
        MainScreenPane.setMaxWidth(300);
        MainScreen = new Scene(MainScreenPane);

        //Sign up Scene Buttons, Labels, etc.
        Label SignUp = new Label("Sign Up");
        SignUp.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 20));
        InputUsernameSignUp = new TextField();
        InputPasswordSignUp = new PasswordField();
        ConfirmPassword = new PasswordField();
        InputUsernameSignUp.setPromptText("Create a Username");
        InputPasswordSignUp.setPromptText("Create a Password");
        ConfirmPassword.setPromptText("Confirm Password");
        CreateAcc = new Button("Create");
        CreateAcc.setOnAction(e -> {
            try {
                if(InputUsernameSignUp.getText().equals("") || InputPasswordSignUp.getText().equals("")) {
                    OkAlert.popUp("Error", "Please fill out all text boxes.", Color.RED);
                }
                if(ConfirmPassword.getText().equals(InputPasswordSignUp.getText())) {
                    Scanner scan = new Scanner(new File("Accounts.txt"));
                    String[] Temp = new String[1000];
                    Boolean contain = false;
                    int x = 0;
                    while(scan.hasNext()) {
                        Temp[x] = scan.nextLine();
                        x++;
                    }
                    for(int i = 0; i < Temp.length; i++) {
                        if(InputUsernameSignUp.getText().equals(Temp[i])) {
                            contain = true;
                        }
                    }
                    if (contain == false) {
                        FinanceWrite fw = new FinanceWrite(InputUsernameSignUp.getText(), false);
                        fw.writeUser(InputUsernameSignUp.getText() + " ---- " + InputPasswordSignUp.getText());
                        fw.close();
                        finance.writeUser(InputUsernameSignUp.getText());
                        finance.flush();
                        OkAlert.popUp("Congratulations!", "Your Account has been created.", Color.LIME);
                        primaryStage.setScene(MainScreen);
                    }
                    else {
                        OkAlert.popUp("Error","Account with this username has already been taken.", Color.RED);
                    }
                }
                else if (!ConfirmPassword.getText().equals(InputPasswordSignUp)){
                    OkAlert.popUp("Error", "Confirmed password does not match the original.", Color.RED);
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
        CreateAccSceneItems.setBackground(new Background(new BackgroundFill(Color.LAVENDER, new CornerRadii(1), null)));
        CreateAccSceneItems.getChildren().addAll(SignUp, InputUsernameSignUp, InputPasswordSignUp, ConfirmPassword, CreateAcc, ExitCreateAcc);
        CreateAccSceneItems.setAlignment(Pos.CENTER);
        CreateAccScene = new Scene(CreateAccSceneItems);

        //Login Screen
        Label LoginLabel = new Label("Login");
        LoginLabel.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 20));
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
                OkAlert.popUp("Error", "Username or Password is incorrect.", Color.RED);
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
        LoginScreenItems.setBackground(new Background(new BackgroundFill(Color.LAVENDER, new CornerRadii(1), null)));
        LoginScreenItems.getChildren().addAll(LoginLabel, InputUserLogin, InputPasswordLogin, Login, ExitLogin);
        LoginScreenItems.setAlignment(Pos.CENTER);
        LoginScene = new Scene(LoginScreenItems);


        primaryStage.setHeight(650);
        primaryStage.setWidth(900);
        primaryStage.setScene(MainScreen);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
