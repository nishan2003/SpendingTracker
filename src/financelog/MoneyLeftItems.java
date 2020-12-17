package financelog;

public class MoneyLeftItems {
    private String name;
    private double money_left;

    public MoneyLeftItems() {
        this.money_left = 0;
        this.name = "";
    }

    public double getMoney_left() {
        return money_left;
    }

    public void setMoney_left(double money_left) {
        this.money_left = money_left;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MoneyLeftItems(String name, double money_left) {
        this.name = name;
        this.money_left = money_left;
    }
}
