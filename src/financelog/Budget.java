package financelog;

import java.util.ArrayList;

public class Budget {
    String name;
    double budget;
    ArrayList<ViewTransactionItems> purchased_array;
    public Budget (String name, double budget, ArrayList<ViewTransactionItems> purchased_array) {
        this.budget = budget;
        this.name = name;
        this.purchased_array = purchased_array;
    }
}
