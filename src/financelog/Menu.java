package financelog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Menu {
    double budget_double;
    double money_left;

    public void openMainMenu(ArrayList<String> user_data, ArrayList<BudgetData> no_budget_categories) throws IOException, ParseException {

        Stage primaryStage = new Stage();

        String[] user_name =  user_data.get(0).split(" ---- ");

        TabPane main_screen = new TabPane();

        ComboBox select_category = new ComboBox();
        ComboBox select_view_transactions = new ComboBox();
        select_view_transactions.getItems().add("All Purchases");
        
        Calendar cal = Calendar.getInstance();
        String date_now = Integer.toString(cal.get(Calendar.MONTH) + 1) + " " + Integer.toString(cal.get(Calendar.YEAR));
        String users_date =  user_data.get(1);

        boolean month_change = false;

        if(!users_date.equals(date_now)) {
            users_date = date_now;
            user_data.set(1, users_date);
            month_change = true;
        }

        ArrayList<BudgetData> categories = new ArrayList<BudgetData>();

        ArrayList<OldPurchaseData> old_purchases_data = new ArrayList<>();



        if(!(no_budget_categories == null)) {
            for(int i = 0; i < no_budget_categories.size(); i++) {
                categories.add(no_budget_categories.get(i));
                select_category.getItems().addAll(no_budget_categories.get(i).name);
                select_view_transactions.getItems().addAll(no_budget_categories.get(i).name);
            }


        }


        TableView budget_table = new TableView<>();

        TableView budget_left_table = new TableView<>();



        //START LOADING DATA CODE HERE
        Scanner file_reader = new Scanner(new File(user_name[0]));
        file_reader.nextLine();
        try {
            file_reader.nextLine();
        }catch (NoSuchElementException e) {

        }

        Scanner old_data_reader = new Scanner(new File(user_name[0]));

        Boolean old_data_reading = false;

        while (old_data_reader.hasNext()) {
            if(old_data_reader.nextLine().equals(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::")) {
                try {
                    while(!(old_data_reading)) {
                        String date = old_data_reader.nextLine();
                        String old_purchase_contents = old_data_reader.nextLine();
                        String[] table_array = old_purchase_contents.split("!:!:!:!:!");
                        ArrayList<ViewTransactionItems> old_purchases_list = new ArrayList<>();
                        for(int i = 0; i<table_array.length; i++) {
                            String[] item = table_array[i].split(" _!_!_! ");
                            try {
                                old_purchases_list.add(new ViewTransactionItems(item[0], Double.parseDouble(item[1]), item[2]));
                            }catch (ArrayIndexOutOfBoundsException n) {

                            }

                        }
                        old_purchases_data.add(new OldPurchaseData(date, old_purchases_list));

                        if(old_data_reader.nextLine().equals("___________________________________")) {
                            continue;
                        }
                        else {
                            old_data_reading = true;
                        }
                    }


                } catch (NoSuchElementException k) {
                    old_data_reading = true;
                }
            }
        }


        boolean month_in_data = false;

        for(int i = 0; i < old_purchases_data.size(); i++) {
            if(old_purchases_data.get(i).date.equals(date_now)) {
                month_in_data = true;
            }
        }

        if(month_in_data == false) {
            old_purchases_data.add(new OldPurchaseData(date_now, new ArrayList<>()));
        }


        Boolean f_reading = false;
        Double budget_before = 0.00;
        Double budget = 0.00;
        try {
            while(!f_reading) {
                String name = file_reader.nextLine();
                try {
                    budget_before = Double.parseDouble(file_reader.nextLine());
                    budget = Double.parseDouble(file_reader.nextLine());
                } catch (Exception ex) {
                    break;
                }

                String table_contents = file_reader.nextLine();
                String[] s = table_contents.split("!:!:!:!:!");
                ArrayList<ViewTransactionItems> v = new ArrayList<>();
                select_category.getItems().addAll(name);
                select_view_transactions.getItems().addAll(name);

                for(int i = 0; i<s.length; i++) {
                    String[] item = s[i].split(" _!_!_! ");
                    try {
                        v.add(new ViewTransactionItems(item[0], Double.parseDouble(item[1]), item[2]));
                    } catch (ArrayIndexOutOfBoundsException exception) {

                    }

                }
                categories.add(new BudgetData(name, budget_before, budget, v ));
                if(file_reader.nextLine().equals(">>>>>>>>")) {
                    continue;
                }
                else {
                    f_reading = true;
                }
            }
        }catch (NoSuchElementException n) {
            f_reading = true;

        }
        if(month_change) {
            for(int i = 0; i < categories.size(); i++) {
                categories.get(i).resetBudget();
            }
        }

        for (int i = 0; i < categories.size(); i++) {
            if(!(categories.get(i).name.equals("No Category"))) {
                budget_table.getItems().addAll(new BudgetTableItems(categories.get(i).name, categories.get(i).budget, categories.get(i).money_left));

            }
        }


        //BUDGET TAB
        select_category.setMinWidth(150);
        select_category.setMaxWidth(150);
        select_view_transactions.setMinWidth(150);
        select_view_transactions.setMaxWidth(150);

        Label budget_items_label = new Label("Set some monthly budgets to manage your spending.");
        budget_items_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,25));

        Tab budgets_tab = new Tab();
        budgets_tab.setStyle("-fx-background-color: #d85e5b;");
        budgets_tab.setGraphic(new Label("Budgets"));
        budgets_tab.getGraphic().setStyle("-fx-text-fill: white;");


        budget_table.setMaxHeight(150);
        budget_table.setMaxWidth(325);

        budget_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<BudgetTableItems, String> budget_name_column = new TableColumn<>("Name");
        budget_name_column.setCellValueFactory(new PropertyValueFactory<>("budget_name"));

        TableColumn<BudgetTableItems, Double> budget_column = new TableColumn<>("Budget");
        budget_column.setCellValueFactory(new PropertyValueFactory<>("budget"));

        TableColumn<BudgetTableItems, Double> moneyleft_column = new TableColumn<>("Money Left");
        moneyleft_column.setCellValueFactory(new PropertyValueFactory<>("moneyleft"));

        budget_name_column.setStyle( "-fx-alignment: CENTER;");
        budget_column.setStyle( "-fx-alignment: CENTER;");
        moneyleft_column.setStyle("-fx-alignment: CENTER;");

        budget_table.getColumns().addAll(budget_name_column, budget_column, moneyleft_column);

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        budget_column.setCellFactory(tc -> new TableCell<BudgetTableItems, Double>() {

            //Formats the prices to two decimal places and makes the font colour purple
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setTextFill(Color.WHITE);
                    setText(currencyFormat.format(price));
                }
            }
        });
        moneyleft_column.setCellFactory(tc -> new TableCell<BudgetTableItems, Double>() {

            //Formats the prices to two decimal places and makes the font colour purple
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setTextFill(Color.WHITE);
                    setText(currencyFormat.format(price));
                }
            }
        });
        budget_name_column.setCellFactory(tc -> new TableCell<BudgetTableItems, String>() {
            //Changes the font colour of the items column to purple
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) {
                    setText(null);
                }
                else {
                    setText(item);
                    setTextFill(Color.WHITE);
                }
            }
        });

        TextField budget_name_input = new TextField();
        budget_name_input.setMaxWidth(375);
        TextField budget_input = new TextField();
        budget_input.setMaxWidth(375);

        budget_name_input.setPromptText("Enter the budget category.");
        budget_input.setPromptText("Enter the budget.");

        Button add_budget = new Button("Enter");
        add_budget.setOnAction(e -> {
            try {
                budget_table.getItems().add(new BudgetTableItems(budget_name_input.getText(), Double.parseDouble(budget_input.getText()), Double.parseDouble(budget_input.getText())));
                budget_left_table.getItems().add(new MoneyLeftItems(budget_name_input.getText(), Double.parseDouble(budget_input.getText())));
                categories.add(new BudgetData(budget_name_input.getText(), Double.parseDouble(budget_input.getText()), Double.parseDouble(budget_input.getText()), new ArrayList<ViewTransactionItems>()));
                select_category.getItems().add(budget_name_input.getText());
                select_view_transactions.getItems().add(budget_name_input.getText());
            }catch(NumberFormatException numberFormatException) {
                OkAlert.popUp("Error", "Please add a number in the budget column", Color.RED, Color.WHITE);
            } finally {
                budget_input.clear();
                budget_name_input.clear();
            }

        });

        Button remove_budget = new Button("Remove");
        remove_budget.setOnAction(e -> {
            ObservableList<BudgetTableItems> SelectedRow, AllRows;
            AllRows = budget_table.getItems();
            SelectedRow = budget_table.getSelectionModel().getSelectedItems();
            String name = SelectedRow.get(0).getBudget_name();
            SelectedRow.forEach(AllRows::remove);
            select_view_transactions.getItems().remove(name);
            select_category.getItems().remove(name);
            for(int i = 0; i <categories.size(); i++) {
                if(categories.get(i).name.equals(name)) {
                    categories.remove(i);
                }
            }

        });

        Button edit_budget = new Button("Edit");
        edit_budget.setOnAction(e -> {
            try {
                ObservableList<BudgetTableItems> SelectedRow;
                SelectedRow = budget_table.getSelectionModel().getSelectedItems();
                EditBudget editBudget = new EditBudget();
                editBudget.popUp(SelectedRow, categories);

                budget_table.getItems().clear();
                for (int i = 0; i < categories.size(); i++) {
                    if(!(categories.get(i).name.equals("No Category"))) {
                        budget_table.getItems().addAll(new BudgetTableItems(categories.get(i).name, categories.get(i).budget, categories.get(i).money_left));
                    }
                }
            } catch (NullPointerException ex) {
                OkAlert.popUp("Error", "Please select a budget in the table.", Color.RED, Color.WHITE);
            }

        });


        //GOALS TAB
        Tab goals_tab = new Tab();
        goals_tab.setStyle("-fx-background-color: #a64a57;");
        goals_tab.setGraphic(new Label("Goals"));
        goals_tab.getGraphic().setStyle("-fx-text-fill: white;");

        Label goals_tab_title = new Label("Set some saving goals.");


        //Stats TAB
        Label insights_label = new Label("Insights");
        insights_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,30));
        Tab stats_tab = new Tab();
        stats_tab.setStyle("-fx-background-color: #883e4b;");
        stats_tab.setGraphic(new Label("Insights"));
        stats_tab.getGraphic().setStyle("-fx-text-fill: white;");

        TabPane insights_tabpane = new TabPane();
        Tab pie_tab = new Tab();
        Tab scatter_tab = new Tab();
        insights_tabpane.getTabs().addAll(pie_tab, scatter_tab);


        ArrayList chart_arraylist = new ArrayList<>();
        PieChart spending_piechart = new PieChart();
        spending_piechart.setTitle("Spending per Budget");
        spending_piechart.setLegendVisible(false);
        refreshPieChart(categories, spending_piechart);

        pie_tab.setContent(spending_piechart);
        pie_tab.setStyle("-fx-background-color: #c04f53;");
        pie_tab.setGraphic(new Label("Spending per Budget"));
        pie_tab.getGraphic().setStyle("-fx-text-fill: white;");

        scatter_tab.setStyle("-fx-background-color: #d8b36c;");
        scatter_tab.setGraphic(new Label("Purchases per Day"));
        scatter_tab.getGraphic().setStyle("-fx-text-fill: white;");

        //Scatter Plot
        NumberAxis dayaxis = new NumberAxis();
        dayaxis.setLabel("Day of Month");

        NumberAxis spendingaxis = new NumberAxis();
        spendingaxis.setLabel("Price of Item ($)");

        ScatterChart spending_perday_chart = new ScatterChart(dayaxis, spendingaxis);
        spending_perday_chart.setLegendVisible(false);
        refreshScatterChart(categories, spending_perday_chart);
        scatter_tab.setContent(spending_perday_chart);


        //MAIN TAB
        Tab main_tab = new Tab();
        main_tab.setStyle("-fx-background-color: #c04f53;");
        main_tab.setGraphic(new Label("Home"));
        main_tab.getGraphic().setStyle("-fx-text-fill: white;");

        //Label main_tab_title = new Label("Quick View");
        //main_tab_title.setFont(Font.font("Roboto", FontWeight.SEMI_BOLD,25));


        Label date_label = new Label();
        Calendar c = Calendar.getInstance();
        //Label welcome = new Label("Welcome Back!");
        //welcome.setFont(Font.font("Segoe UI Light", FontWeight.THIN, 30));
        date_label.setText(c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()) + ", " + c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + String.valueOf(c.get(Calendar.DAY_OF_MONTH)));
        date_label.setFont(Font.font("Segoe UI Light", FontWeight.LIGHT,35));
        //System.out.println(javafx.scene.text.Font.getFamilies());

        //OLD PURCHASES TAB
        Tab old_purchases = new Tab();
        old_purchases.setStyle("-fx-background-color: #d85e5b;");
        old_purchases.setGraphic(new Label("Old Purchases"));
        old_purchases.getGraphic().setStyle("-fx-text-fill: white;");

        Label old_purchase_subtitle = new Label("View old purchases from a certain month.");
        old_purchase_subtitle.setFont(Font.font("Segoe UI Light", FontWeight.LIGHT, 24.5));
        Label date_combo_label = new Label("Showing purchases from (MM YYYY): ");
        date_combo_label.setFont(Font.font("Segoe UI Light", FontWeight.LIGHT, 13));

        ComboBox old_date_selector = new ComboBox();
        for(int i = 0; i <old_purchases_data.size(); i++) {
            old_date_selector.getItems().add(old_purchases_data.get(i).date);
        }

        TableView old_table = new TableView();

        TableColumn<ViewTransactionItems, String> old_purchase_namecolumn= new TableColumn<>("Item Name");
        old_purchase_namecolumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));

        TableColumn<ViewTransactionItems, Double> old_purchase_pricecolumn  = new TableColumn<>("Price");
        old_purchase_pricecolumn.setCellValueFactory(new PropertyValueFactory<>("item_price"));

        TableColumn<ViewTransactionItems, String> old_purchase_datecolumn  = new TableColumn<>("Date of Purchase");
        old_purchase_datecolumn.setCellValueFactory(new PropertyValueFactory<>("item_date"));

        old_purchase_namecolumn.setStyle("-fx-alignment: CENTER;");
        old_purchase_pricecolumn.setStyle("-fx-alignment: CENTER;");
        old_purchase_datecolumn.setStyle("-fx-alignment: CENTER;");

        old_table.setMaxHeight(275);
        old_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        old_purchase_pricecolumn.setCellFactory(tc -> new TableCell<ViewTransactionItems, Double>() {

            //Formats the prices to two decimal places and makes the font colour purple
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setTextFill(Color.WHITE);
                    setText(currencyFormat.format(price));
                }
            }
        });
        old_purchase_namecolumn.setCellFactory(tc -> new TableCell<ViewTransactionItems, String>() {
            //Changes the font colour of the items column to purple
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) {
                    setText(null);
                }
                else {
                    setText(item);
                    setTextFill(Color.WHITE);
                }
            }
        });
        old_purchase_datecolumn.setCellFactory(tc -> new TableCell<ViewTransactionItems, String>() {
            //Changes the font colour of the items column to purple
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) {
                    setText(null);
                }
                else {
                    setText(item);
                    setTextFill(Color.WHITE);
                }
            }
        });

        old_table.getColumns().addAll(old_purchase_namecolumn, old_purchase_pricecolumn, old_purchase_datecolumn);

        old_date_selector.getSelectionModel().selectedItemProperty().addListener((options, oldval, newval) -> {
            String date_selected = (String) old_date_selector.getValue();
            try {
                for(int i = 0; i < old_purchases_data.size(); i++) {
                    if(old_purchases_data.get(i).date.equals(date_selected)) {
                        ObservableList view_items = FXCollections.observableArrayList(old_purchases_data.get(i).items);
                        old_table.getItems().setAll(view_items);
                    }
                }
            } catch (Exception e) {
                ObservableList view_items = FXCollections.observableArrayList();
                old_table.getItems().setAll(view_items);
            }

        });

        Hyperlink export_old = new Hyperlink("Export to Excel");
        export_old.setStyle("-fx-border-color: transparent; -fx-text-fill: white;");
        export_old.setOnAction(e -> {
            ExportWindow exportWindow = new ExportWindow();
            String filename = exportWindow.popUp();
            if(exportWindow.cancelled == false) {
                try {
                    DataWrite exportWrite = new DataWrite(filename+".csv", false);
                    exportWrite.writeUser(old_purchase_namecolumn.getText() + ","+ old_purchase_pricecolumn.getText()+"," + old_purchase_datecolumn.getText());
                    for(int i = 0; i < old_table.getItems().size(); i++) {
                        exportWrite.writeUser(old_purchase_namecolumn.getCellData(i)+"," +old_purchase_pricecolumn.getCellData(i)+"," +old_purchase_datecolumn.getCellData(i));
                    }
                    exportWrite.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }


        });

        //TRANSACTIONS TAB
        Tab transactions_tab = new Tab();
        transactions_tab.setStyle("-fx-background-color: #d8b36c;");
        transactions_tab.setGraphic(new Label("Expenses"));
        transactions_tab.getGraphic().setStyle("-fx-text-fill: white;");
        Label transactions_items_label = new Label("Purchases");
        transactions_items_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,25));
        TabPane transactions_tabpane  = new TabPane();

        //new_transaction
        Label new_trans_label = new Label("New Transaction.");
        new_trans_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,25));

        TextField insert_name_field = new TextField();
        insert_name_field.setPromptText("Insert the name of the item you purchased.");
        insert_name_field.setMaxWidth(400);
        TextField insert_price_field = new TextField();
        insert_price_field.setPromptText("Insert the price of the item you purchased.");
        insert_price_field.setMaxWidth(400);


        Tab new_transaction = new Tab();
        new_transaction.setStyle("-fx-background-color: #d85e5b;");
        new_transaction.setGraphic(new Label("New Purchase"));
        new_transaction.getGraphic().setStyle("-fx-text-fill: white;");
        Label new_transactions_items_label = new Label("New Purchase");
        Label budget_label = new Label("Select a budget.");
        budget_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,23));
        Label selectdate_label = new Label("Select the date of your purchase.");
        selectdate_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,23));
        new_transactions_items_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,25));
        Button add_item = new Button("Add Purchase");

        DatePicker pick_date = new DatePicker();
        LocalDate this_month = LocalDate.now();
        pick_date.setDayCellFactory(d ->
                new DateCell() {
                    @Override public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(this_month.withDayOfMonth(this_month.lengthOfMonth())) || item.isBefore(this_month.withDayOfMonth(1)));
                    }});
        add_item.setOnAction(e -> {
            select_view_transactions.getSelectionModel().clearSelection();
            old_date_selector.getSelectionModel().clearSelection();
            old_table.getItems().clear();
            try {
                String category_name = (String) select_category.getValue();
                String date_picked = pick_date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                for(int f = 0; f < categories.size(); f++) {
                    if(categories.get(f).name.equals(category_name)) {
                        if(categories.get(f).money_left - Double.parseDouble(insert_price_field.getText()) < 0) {
                            boolean answer = YesNoAlert.popUp("Warning!", "Your purchase exceeds the money left in your budget. If you proceed, money will be deducted from your next months budget.");
                            if(answer == false) {

                            }
                            else{
                                categories.get(f).money_left = categories.get(f).money_left - Double.parseDouble(insert_price_field.getText());
                                categories.get(f).purchased_array.add(new ViewTransactionItems(insert_name_field.getText(), Double.parseDouble(insert_price_field.getText()), date_picked));
                                for(int i = 0; i < old_purchases_data.size(); i++) {
                                    if(old_purchases_data.get(i).date.equals(date_now)) {
                                        old_purchases_data.get(i).items.add(new ViewTransactionItems(insert_name_field.getText(), Double.parseDouble(insert_price_field.getText()), date_picked));
                                    }
                                }
                            }
                        }
                        else {
                            categories.get(f).money_left = categories.get(f).money_left - Double.parseDouble(insert_price_field.getText());
                            categories.get(f).purchased_array.add(new ViewTransactionItems(insert_name_field.getText(), Double.parseDouble(insert_price_field.getText()), date_picked));
                            for(int i = 0; i < old_purchases_data.size(); i++) {
                                if(old_purchases_data.get(i).date.equals(date_now)) {
                                    old_purchases_data.get(i).items.add(new ViewTransactionItems(insert_name_field.getText(), Double.parseDouble(insert_price_field.getText()), date_picked));
                                }
                            }
                        }
                    }
                }
                budget_table.getItems().clear();
                for (int i = 0; i < categories.size(); i++) {
                    if(!(categories.get(i).name.equals("No Category"))) {
                        budget_table.getItems().addAll(new BudgetTableItems(categories.get(i).name, categories.get(i).budget, categories.get(i).money_left));

                    }
                }
                refreshPieChart(categories, spending_piechart);
                refreshScatterChart(categories, spending_perday_chart);
            }catch (NumberFormatException | ParseException exception) {
                OkAlert.popUp("Error", "Please enter a number in the price field", Color.RED, Color.WHITE);
            } finally {
                insert_name_field.clear();
                insert_price_field.clear();
            }

        });


        //view_transactions
        Label view_trans_label = new Label("View your purchases for this month.");
        view_trans_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,24.5));


        Label showing_category = new Label("Showing Purchases from:");
        showing_category.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,13));


        Tab view_transactions = new Tab();
        view_transactions.setStyle(" -fx-background-color: #a64a57;");
        view_transactions.setGraphic(new Label("Recent Purchases"));
        view_transactions.getGraphic().setStyle("-fx-text-fill: white;");

        TableView view_transaction_table = new TableView();
        view_transaction_table.setMaxHeight(275);
        view_transaction_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<ViewTransactionItems, String> insert_itemname_column = new TableColumn<>("Item Name");
        insert_itemname_column.setCellValueFactory(new PropertyValueFactory<>("item_name"));

        TableColumn<ViewTransactionItems, Double> insert_itemprice_column = new TableColumn<>("Price");
        insert_itemprice_column.setCellValueFactory(new PropertyValueFactory<>("item_price"));

        TableColumn<ViewTransactionItems, String> insert_datecolumn = new TableColumn<>("Date of Purchase");
        insert_datecolumn.setCellValueFactory(new PropertyValueFactory<>("item_date"));

        insert_itemname_column.setStyle( "-fx-alignment: CENTER;");
        insert_itemprice_column.setStyle( "-fx-alignment: CENTER;");
        insert_datecolumn.setStyle( "-fx-alignment: CENTER;");

        //System.out.println(categories.get(0).purchased_array.get(0).getItem_price());

        view_transaction_table.getColumns().addAll(insert_itemname_column, insert_itemprice_column, insert_datecolumn);

        insert_itemprice_column.setCellFactory(tc -> new TableCell<ViewTransactionItems, Double>() {

            //Formats the prices to two decimal places and makes the font colour purple
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setTextFill(Color.WHITE);
                    setText(currencyFormat.format(price));
                }
            }
        });
        insert_itemname_column.setCellFactory(tc -> new TableCell<ViewTransactionItems, String>() {
            //Changes the font colour of the items column to purple
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) {
                    setText(null);
                }
                else {
                    setText(item);
                    setTextFill(Color.WHITE);
                }
            }
        });
        insert_datecolumn.setCellFactory(tc -> new TableCell<ViewTransactionItems, String>() {
            //Changes the font colour of the items column to purple
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) {
                    setText(null);
                }
                else {
                    setText(item);
                    setTextFill(Color.WHITE);
                }
            }
        });


        select_view_transactions.getSelectionModel().selectedItemProperty().addListener((options, oldval, newval) -> {
            String s = (String) select_view_transactions.getValue();
            try {
                if(!(s.equals("All Purchases"))) {
                    for(int i = 0; i<categories.size(); i++) {
                        try {
                            if(categories.get(i).name.equals(s)) {
                                ObservableList view_items = FXCollections.observableArrayList(categories.get(i).purchased_array);
                                view_transaction_table.getItems().setAll(view_items);
                                showing_category.setText("Showing Purchases from: " + s + ".");
                            }
                        } catch (NullPointerException ex) {
                            ObservableList view_items = FXCollections.observableArrayList();
                            view_transaction_table.getItems().setAll(view_items);
                            showing_category.setText("Showing Purchases from: " + ".");
                        }

                    }
                } else if(s.equals("All Purchases")) {
                    ObservableList all_items = FXCollections.observableArrayList();
                    for(int n = 0; n< categories.size(); n++) {
                        all_items.addAll(categories.get(n).purchased_array);
                    }
                    view_transaction_table.getItems().setAll(all_items);
                    showing_category.setText("Showing All Purchases.");

                }
            } catch (NullPointerException ex) {
                ObservableList view_items = FXCollections.observableArrayList();
                view_transaction_table.getItems().setAll(view_items);
                showing_category.setText("Showing Purchases from: " + ".");
            }

        });

        Hyperlink export_recent = new Hyperlink("Export to Excel");
        export_recent.setStyle("-fx-border-color: transparent; -fx-text-fill: white;");
        export_recent.setOnAction(e -> {
            ExportWindow exportWindow = new ExportWindow();
            String filename = exportWindow.popUp();
            if(exportWindow.cancelled == false) {
                try {
                    DataWrite exportWrite = new DataWrite(filename+".csv", false);
                    exportWrite.writeUser(insert_itemname_column.getText() + ","+ insert_itemprice_column.getText()+"," + insert_datecolumn.getText());
                    for(int i = 0; i < view_transaction_table.getItems().size(); i++) {
                        exportWrite.writeUser(insert_itemname_column.getCellData(i)+"," +insert_itemprice_column.getCellData(i)+"," +insert_datecolumn.getCellData(i));
                    }
                    exportWrite.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }


        });




        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    DataWrite fw = new DataWrite(user_name[0], false); //erases all data and replaces with just username
                    DataWrite fw1 = new DataWrite(user_name[0], true); //appends new data
                    fw.writeUser(user_data.get(0));
                    fw.writeUser(user_data.get(1));
                    fw.close();

                    for(int i = 0; i<categories.size(); i++) {
                        String s = "";
                        fw1.writeUser(categories.get(i).name);
                        fw1.writeUser(Double.toString(categories.get(i).budget));
                        fw1.writeUser(Double.toString(categories.get(i).money_left));
                        for(int f = 0; f < categories.get(i).purchased_array.size(); f++) {
                            s = s +  categories.get(i).purchased_array.get(f).getItem_name() + " _!_!_! " + categories.get(i).purchased_array.get(f).getItem_price() + " _!_!_! "  + categories.get(i).purchased_array.get(f).getItem_date() + "!:!:!:!:!";
                        }
                        fw1.writeUser(s);
                        fw1.writeUser(">>>>>>>>");
                    }
                    fw1.writeUser(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");

                    for(int i = 0; i<old_purchases_data.size(); i++) {
                        String s = "";
                        fw1.writeUser(old_purchases_data.get(i).date);
                        for(int f = 0; f < old_purchases_data.get(i).items.size(); f++) {
                            s = s +  old_purchases_data.get(i).items.get(f).getItem_name() + " _!_!_! " + old_purchases_data.get(i).items.get(f).getItem_price() +" _!_!_! "+ old_purchases_data.get(i).items.get(f).getItem_date() + "!:!:!:!:!";
                        }
                        fw1.writeUser(s);
                        fw1.writeUser("___________________________________");
                    }
                    fw1.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



        transactions_tabpane.getTabs().addAll(new_transaction,view_transactions,old_purchases);
        transactions_tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        insights_tabpane.setTabMinWidth(418);

        main_screen.setTabMinWidth(160);
        transactions_tabpane.setTabMinWidth(274.4);

        //Quick View
        BorderPane quickview_pane = new BorderPane();
        VBox quick_view_box = new VBox();
        quick_view_box.getChildren().addAll(date_label);
        quick_view_box.setAlignment(Pos.CENTER);
        quickview_pane.setTop(quick_view_box);
        main_tab.setContent(quickview_pane);


        //Budget Tab
        VBox budget_items = new VBox();
        budget_items.setSpacing(5);
        HBox budget_buttons= new HBox();
        budget_buttons.getChildren().addAll(add_budget, remove_budget, edit_budget);
        budget_buttons.setSpacing(5);
        budget_buttons.setAlignment(Pos.CENTER);
        budget_items.getChildren().addAll(budget_items_label, budget_table, budget_name_input, budget_input, budget_buttons);
        budget_items.setAlignment(Pos.CENTER);
        BorderPane budget_pane = new BorderPane();
        budget_pane.setCenter(budget_items);
        budget_pane.setPadding(new Insets(10,100,10,100));
        budgets_tab.setContent(budget_pane);

        //New Transaction Tab
        VBox new_trans_items = new VBox();
        new_trans_items.setAlignment(Pos.CENTER);
        new_trans_items.setPadding(new Insets(15,30,0,30));
        new_trans_items.setSpacing(15);
        new_trans_items.getChildren().addAll(new_trans_label,insert_name_field,insert_price_field, budget_label, select_category, selectdate_label, pick_date, add_item);
        BorderPane new_transaction_pane = new BorderPane();
        new_transaction_pane.setCenter(new_trans_items);
        new_transaction_pane.setPadding(new Insets(0,22,20,22));
        new_transaction.setContent(new_transaction_pane);

        //View Transaction Tab
        VBox view_trans_items = new VBox();
        HBox combo_label = new HBox();
        combo_label.getChildren().addAll(select_view_transactions, showing_category);
        combo_label.setAlignment(Pos.CENTER);
        combo_label.setSpacing(5);
        view_trans_items.getChildren().addAll(view_trans_label, combo_label, view_transaction_table,export_recent);
        view_trans_items.setPadding(new Insets(12,20,20,22));
        view_trans_items.setSpacing(10);
        view_trans_items.setAlignment(Pos.CENTER);
        view_transactions.setContent(view_trans_items);

        //Transactions Tab
        BorderPane transactions_pane = new BorderPane();
        VBox transaction_items = new VBox(10);
        transaction_items.setAlignment(Pos.CENTER);
        transaction_items.getChildren().addAll(transactions_items_label,transactions_tabpane);
        transaction_items.setAlignment(Pos.CENTER);
        transactions_pane.setCenter(transaction_items);
        transactions_tab.setContent(transactions_pane);

        //Stats
        BorderPane stats_pane = new BorderPane();
        VBox stats_pane_vbox = new VBox();
        stats_pane_vbox.setAlignment(Pos.CENTER);
        insights_tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        stats_pane_vbox.getChildren().addAll(insights_label, insights_tabpane);
        stats_pane.setTop(stats_pane_vbox);
        stats_tab.setContent(stats_pane_vbox);

        //Old Purchases
        BorderPane old_purchases_pane = new BorderPane();
        VBox old_purchase_items = new VBox(10);
        HBox date_combo_box = new HBox(5);
        date_combo_box.setAlignment(Pos.CENTER);
        date_combo_box.getChildren().addAll(date_combo_label, old_date_selector);
        old_purchase_items.getChildren().addAll(old_purchase_subtitle, date_combo_box, old_table, export_old);
        old_purchases_pane.setTop(old_purchase_items);
        old_purchase_items.setAlignment(Pos.CENTER);
        old_purchase_items.setPadding(new Insets(15,20,20,22));
        old_purchases.setContent(old_purchases_pane);


        main_screen.getTabs().addAll(main_tab, budgets_tab, transactions_tab, goals_tab, stats_tab);
        main_screen.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Scene main_scene = new Scene(main_screen);

        main_scene.getStylesheets().add(getClass().getResource("/resources/menustyle.css").toExternalForm());

        ClassLoader load = Thread.currentThread().getContextClassLoader();

        primaryStage.getIcons().add(new Image(load.getResourceAsStream("app_icon.png")));

        primaryStage.setScene(main_scene);
        primaryStage.setTitle("Menu");
        primaryStage.setResizable(false);
        primaryStage.setWidth(885);
        primaryStage.setHeight(570);
        primaryStage.show();

    }

    public static void refreshPieChart(ArrayList<BudgetData> categories, PieChart spending_chart) {
        ArrayList chart_arraylist = new ArrayList();
        for (int i = 0; i < categories.size(); i++) {
            if(!(categories.get(i).name.equals("No Category"))) {
                chart_arraylist.add(new PieChart.Data(categories.get(i).name, categories.get(i).budget - categories.get(i).money_left));
            }
        }
        double no_category_spending = 0;
        for(int i = 0; i < categories.size(); i++) {
            if(categories.get(i).name.equals("No Category")) {
                for(int f = 0; f<categories.get(i).purchased_array.size(); f++) {
                    no_category_spending += categories.get(i).purchased_array.get(f).getItem_price();
                }

            }
        }
        chart_arraylist.add(new PieChart.Data("No Category", no_category_spending));
        ObservableList<PieChart.Data> chart_data = FXCollections.observableArrayList(chart_arraylist);
        spending_chart.setData(chart_data);
    }

    public static void refreshScatterChart(ArrayList<BudgetData> categories, ScatterChart spending_perday_chart) throws ParseException {
        XYChart.Series scatter_series = new XYChart.Series();
        spending_perday_chart.getData().clear();
        for(int i = 0; i< categories.size(); i++) {
            for(int f = 0; f < categories.get(i).purchased_array.size(); f++) {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(categories.get(i).purchased_array.get(f).getItem_date());
                Calendar daycal = Calendar.getInstance();
                daycal.setTime(date);
                int dateint = daycal.get(Calendar.DAY_OF_MONTH);
                scatter_series.getData().add(new XYChart.Data<>(dateint, categories.get(i).purchased_array.get(f).getItem_price()));
            }
        }

        spending_perday_chart.getData().addAll(scatter_series);
    }


}
