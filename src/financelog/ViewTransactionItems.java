package financelog;

public class ViewTransactionItems {
    private String item_name;
    private double item_price;

    public ViewTransactionItems() {
        this.item_name = "";
        this.item_price = 0;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public double getItem_price() {
        return item_price;
    }

    public void setItem_price(double item_price) {
        this.item_price = item_price;
    }

    public ViewTransactionItems(String item_name, double item_price) {
        this.item_name = item_name;
        this.item_price = item_price;

    }
}
