package financelog;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
            primaryStage.setScene(MainMenuScene);
            primaryStage.close();
            ArrayList<String> data = new ArrayList<String>();
            Scanner add_data_arraylist = new Scanner(new File(InputUserLogin.getText()));
            while (add_data_arraylist.hasNext()) {
                data.add(add_data_arraylist.nextLine());
            }
            try {
                Menu m = new Menu();
                m.openMainMenu(data);
                String bool = data.get(1);
            } catch (IndexOutOfBoundsException e) {
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
