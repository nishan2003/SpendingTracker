package spendingtracker;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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
            primaryStage.close();
            ArrayList<String> data = new ArrayList<String>();
            Scanner add_data_arraylist = new Scanner(new File(InputUserLogin.getText()));
            while (add_data_arraylist.hasNext()) {
                data.add(add_data_arraylist.nextLine());
            }
            Menu m = new Menu();
            BudgetData no_category = null;
            ArrayList<BudgetData> no_budget_categories = new ArrayList<>();
            try {
                String month = data.get(1);
            } catch (IndexOutOfBoundsException e) {
                Calendar c = Calendar.getInstance();
                data.add(Integer.toString(c.get(Calendar.MONTH)) + " " + Integer.toString(c.get(Calendar.YEAR)));

                no_category = new BudgetData("No Category", Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, new ArrayList<>());
                no_budget_categories.add(no_category);
                /*try {
                    DataWrite dataWrite = new DataWrite(InputUserLogin.getText(), true);
                    dataWrite.writeUser(no_category.name);
                    dataWrite.writeUser(Double.toString(no_category.budget));
                    dataWrite.writeUser(Double.toString(no_category.money_left));
                    dataWrite.writeUser("");
                    dataWrite.writeUser(">>>>>>>>");
                } catch (IOException f) {

                } */

                Tutorial window = new Tutorial();
                window.popUp(InputUserLogin.getText());
            } finally {
                try {
                    m.openMainMenu(data, no_budget_categories);
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }



        }
        else {
            OkAlert.popUp("Error", "Username or Password is incorrect.", Color.RED, Color.WHITE);
        }
        InputUserLogin.clear();
        InputPasswordLogin.clear();
    }
}
