package financelog;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CheckLogin {

    public static void Login(TextField InputUserLogin, TextField InputPasswordLogin) {
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
    }
}
