package financelog;

import java.util.ArrayList;

public class OldPurchaseData {
    String date;
    ArrayList<ViewTransactionItems> items;

    public OldPurchaseData(String date, ArrayList<ViewTransactionItems> items) {
        this.items = items;
        this.date = date;
    }
}
