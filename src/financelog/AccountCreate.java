package financelog;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AccountCreate {
    public static void createAcc(TextField InputPasswordSignUp, TextField InputUsernameSignUp, TextField ConfirmPassword, Stage primaryStage, Scene MainScreen, DataWrite finance) {
        try {
            if(InputUsernameSignUp.getText().equals("") || InputPasswordSignUp.getText().equals("")) {
                OkAlert.popUp("Error", "Please fill out all text boxes.", Color.RED, Color.WHITE);
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
                    DataWrite fw = new DataWrite(InputUsernameSignUp.getText(), false);
                    fw.writeUser(InputUsernameSignUp.getText() + " ---- " + InputPasswordSignUp.getText());
                    fw.close();
                    finance.writeUser(InputUsernameSignUp.getText());
                    finance.flush();
                    OkAlert.popUp("Congratulations!", "Your Account has been created.", Color.LIMEGREEN, Color.WHITE);
                    primaryStage.setScene(MainScreen);
                }
                else {
                    OkAlert.popUp("Error","Account with this username has already been taken.", Color.RED, Color.WHITE);
                }
            }
            else if (!ConfirmPassword.getText().equals(InputPasswordSignUp)){
                OkAlert.popUp("Error", "Confirmed password does not match the original.", Color.RED, Color.WHITE);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            InputUsernameSignUp.clear();
            InputPasswordSignUp.clear();
            ConfirmPassword.clear();
        }
    }
}
