package financelog;

public class TableItems {
    private String Item;
    private int Price;

    public TableItems() {
        this.Item = "";
        this.Price = 0;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public TableItems(String items, int price) {
        this.Item = items;
        this.Price = price;
    }
}
