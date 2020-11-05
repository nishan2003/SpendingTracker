package financelog;

public class BudgetItems {
    private String budget_name;
    private double budget;

    public BudgetItems() {
        this.budget_name = "";
        this.budget = 0;
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

    public BudgetItems(String budget_name, double budget) {
        this.budget_name = budget_name;
        this.budget = budget;
    }
}
