package financelog;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CheckLogin {

    public static void login(TextField InputUserLogin, TextField InputPasswordLogin, Stage primaryStage, Scene MainMenuScene) throws FileNotFoundException {
        String[] Array = new String[15];
        String line = null;
        try {
            Scanner fileReader = new Scanner(new File(InputUserLogin.getText()));
            line = fileReader.nextLine();
            Array = line.split(" ---- ");

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }


        if(InputUserLogin.getText().equals(Array[0]) && InputPasswordLogin.getText().equals(Array[1])) {
            String[] Elements = new String[1000];
            int maxIndex = -1;
            primaryStage.setScene(MainMenuScene);
            Scanner BooleanReader = new Scanner(new File(InputUserLogin.getText()));
            primaryStage.close();
            MainMenu.openMainMenu();
            try {
                BooleanReader.nextLine();
                String bool = BooleanReader.nextLine();
            } catch (NoSuchElementException ex) {
                BudgetSetWindow window = new BudgetSetWindow();
                window.popUp(InputUserLogin.getText());

            }
        }
        else {
            OkAlert.popUp("Error", "Username or Password is incorrect.", Color.RED, Color.WHITE);
        }
        InputUserLogin.clear();
        InputPasswordLogin.clear();
    }
}
