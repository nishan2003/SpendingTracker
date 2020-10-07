package financelog;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CheckLogin {

    public static void login(TextField InputUserLogin, TextField InputPasswordLogin, Stage primaryStage, Scene MainMenuScene) {
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
            primaryStage.setScene(MainMenuScene);
        }
        else {
            OkAlert.popUp("Error", "Username or Password is incorrect.", Color.RED);
        }
        InputUserLogin.clear();
        InputPasswordLogin.clear();
    }
}
