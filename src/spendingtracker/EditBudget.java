package spendingtracker;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.util.ArrayList;

public class EditBudget {
    double new_budget;
    public void popUp(ObservableList<BudgetTableItems> SelectedRow, ArrayList<BudgetData> categories) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setHeight(200);
        window.setWidth(400);
        window.setTitle("Edit Budget");
        Button done = new Button("Done");
        Label subtitle = new Label("Add or reduce money from: " + SelectedRow.get(0).getBudget_name() + ".");
        subtitle.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,17));
        String budget_name = SelectedRow.get(0).getBudget_name();

        Spinner<Integer> num_selector = new Spinner<Integer>(-10000000, 10000000, 1 );
        num_selector.setEditable(true);

        done.setOnAction(e-> {
            //new_budget = Double.parseDouble(change_budget.getText());
            for(int i = 0; i < categories.size(); i++) {
                if(categories.get(i).name.equals(budget_name)) {
                    if(categories.get(i).money_left + Double.valueOf(num_selector.getValue()) >= 0) {
                        categories.get(i).budget += Double.valueOf(num_selector.getValue());
                        categories.get(i).money_left += Double.valueOf(num_selector.getValue());
                    }
                    else {
                        OkAlert.popUp("Error", "Your changes exceed the money left in your account.", Color.RED, Color.WHITE);
                    }

                }
            }
            window.close();
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e-> {
            window.close();
        });

        HBox buttons = new HBox(5);
        buttons.getChildren().addAll(done, cancel );
        buttons.setAlignment(Pos.CENTER);
        VBox v = new VBox(10);
        v.getChildren().addAll(subtitle, num_selector, buttons);
        v.setAlignment(Pos.CENTER);

        Scene s = new Scene(v);
        s.getStylesheets().add(getClass().getResource("/resources/tutorial.css").toExternalForm());
        window.setScene(s);
        window.setResizable(false);
        ClassLoader load = Thread.currentThread().getContextClassLoader();

        window.getIcons().add(new Image(load.getResourceAsStream("app_icon.png")));
        window.showAndWait();

    }
}
