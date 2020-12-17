package spendingtracker;

public class BudgetTableItems {
    private String budget_name;
    private double budget;
    private double moneyleft;

    public BudgetTableItems() {
        this.budget_name = "";
        this.budget = 0;
        this.moneyleft = 0;
    }

    public double getMoneyleft() {
        return moneyleft;
    }

    public void setMoneyleft(double moneyleft) {
        this.moneyleft = moneyleft;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getBudget_name() {
        return budget_name;
    }

    public void setBudget_name(String budget_name) {
        this.budget_name = budget_name;
    }

    public BudgetTableItems(String budget_name, double budget, double moneyleft) {
        this.budget_name = budget_name;
        this.budget = budget;
        this.moneyleft = moneyleft;
    }
}
