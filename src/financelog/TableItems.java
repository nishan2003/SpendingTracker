package financelog;

public class TableItems {
    private String Item;
    private double Price;

    public TableItems() {
        this.Item = "";
        this.Price = 0;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public TableItems(String items, double price) {
        this.Item = items;
        this.Price = price;
    }
}
