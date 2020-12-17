package financelog;

import java.util.ArrayList;

public class BudgetData {
    String name;
    double budget;
    double money_left;
    ArrayList<ViewTransactionItems> purchased_array;
    public BudgetData(String name, double budget, double money_left, ArrayList<ViewTransactionItems> purchased_array) {
        this.budget = budget;
        this.name = name;
        this.purchased_array = purchased_array;
        this.money_left = money_left;
    }

    public void resetBudget() {
        if(money_left < 0) {
            money_left = budget + money_left;
        }
        else {
            money_left = budget;
        }

        purchased_array.clear();
    }
}
