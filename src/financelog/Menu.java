package financelog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Menu {
    double budget_double;
    double money_left;

    public void openMainMenu(ArrayList<String> user_data) throws FileNotFoundException {

        TabPane main_screen = new TabPane();

        //BUDGET TAB
        ArrayList<Budget> categories = new ArrayList<Budget>();
        ComboBox select_category = new ComboBox();
        ComboBox select_view_transactions = new ComboBox();
        select_category.setMinWidth(150);
        select_category.setMaxWidth(150);
        select_view_transactions.setMinWidth(150);
        select_view_transactions.setMaxWidth(150);

        Label budget_items_label = new Label("Set some budgets to manage your spending.");
        budget_items_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,25));
        budget_items_label.setStyle("-fx-text-fill: #bc13fe;");

        Tab budgets_tab = new Tab();
        budgets_tab.setStyle("-fx-background-color: #bc13fe;");
        budgets_tab.setGraphic(new Label("Budgets"));
        budgets_tab.getGraphic().setStyle("-fx-text-fill: white;");

        TableView budget_table = new TableView<>();
        budget_table.setMaxHeight(300);
        budget_table.setMaxWidth(325);

        budget_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<BudgetItems, String> budget_name_column = new TableColumn<>("Category");
        budget_name_column.setCellValueFactory(new PropertyValueFactory<>("budget_name"));

        TableColumn<BudgetItems, Double> budget_column = new TableColumn<>("Budget");
        budget_column.setCellValueFactory(new PropertyValueFactory<>("budget"));

        budget_name_column.setStyle( "-fx-alignment: CENTER;");
        budget_column.setStyle( "-fx-alignment: CENTER;");

        budget_table.getColumns().addAll(budget_name_column, budget_column);

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        budget_column.setCellFactory(tc -> new TableCell<BudgetItems, Double>() {

            //Formats the prices to two decimal places and makes the font colour purple
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setTextFill(Color.rgb(188,19,254));
                    setText(currencyFormat.format(price));
                }
            }
        });
        budget_name_column.setCellFactory(tc -> new TableCell<BudgetItems, String>() {
            //Changes the font colour of the items column to purple
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) {
                    setText(null);
                }
                else {
                    setText(item);
                    setTextFill(Color.rgb(188,19,254));
                }
            }
        });

        TextField budget_name_input = new TextField();
        TextField budget_input = new TextField();

        budget_name_input.setPromptText("Enter the budget category.");
        budget_input.setPromptText("Enter the budget.");

        Button add_budget = new Button("Enter");
        add_budget.setOnAction(e -> {
            try {
                budget_table.getItems().add(new BudgetItems(budget_name_input.getText(), Double.parseDouble(budget_input.getText())));
                categories.add(new Budget(budget_name_input.getText(), Double.parseDouble(budget_input.getText()), new ArrayList<ViewTransactionItems>()));
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

        });

        //GOALS TAB
        Tab goals_tab = new Tab();
        goals_tab.setStyle("-fx-background-color: #bc13fe;");
        goals_tab.setGraphic(new Label("Goals"));
        goals_tab.getGraphic().setStyle("-fx-text-fill: white;");

        //SUBSCRIPTIONS TAB
        Tab subscriptions_tab = new Tab();
        subscriptions_tab.setStyle("-fx-background-color: #bc13fe;");
        subscriptions_tab.setGraphic(new Label("Subscriptions"));
        subscriptions_tab.getGraphic().setStyle("-fx-text-fill: white;");


        //GRAPHS TAB
        Tab graphs_tab = new Tab();
        graphs_tab.setStyle("-fx-background-color: #bc13fe;");
        graphs_tab.setGraphic(new Label("Graphs"));
        graphs_tab.getGraphic().setStyle("-fx-text-fill: white;");

        //TRANSACTIONS TAB
        Tab transactions_tab = new Tab();
        transactions_tab.setStyle("-fx-background-color: #bc13fe;");
        transactions_tab.setGraphic(new Label("Transactions"));
        transactions_tab.getGraphic().setStyle("-fx-text-fill: white;");
        Label transactions_items_label = new Label("Transactions");
        transactions_items_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,25));
        transactions_items_label.setStyle("-fx-text-fill: #bc13fe;");
        TabPane transactions_tabpane  = new TabPane();

        //new_transaction
        Label new_trans_label = new Label("Enter in a new Transaction.");
        new_trans_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,25));
        new_trans_label.setStyle("-fx-text-fill: #bc13fe;");

        Label what_budget = new Label("Select the category of your purchase.");
        what_budget.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,23.5));
        what_budget.setStyle("-fx-text-fill: #bc13fe;");

        TextField insert_name_field = new TextField();
        insert_name_field.setPromptText("Insert the name of the item you purchased.");
        TextField insert_price_field = new TextField();
        insert_price_field.setPromptText("Insert the price of the item you purchased.");

        Tab new_transaction = new Tab();
        new_transaction.setStyle("-fx-background-color: #bc13fe;");
        new_transaction.setGraphic(new Label("New Transaction"));
        new_transaction.getGraphic().setStyle("-fx-text-fill: white;");
        Label new_transactions_items_label = new Label("New Transaction");
        new_transactions_items_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,25));
        new_transactions_items_label.setStyle("-fx-text-fill: #bc13fe;");

        Button add_item = new Button("Add Transaction");
        add_item.setOnAction(e -> {
            String category_name = (String) select_category.getValue();
            for(int f = 0; f < categories.size(); f++) {
                if(categories.get(f).name.equals(category_name)) {
                    if(categories.get(f).budget - Double.parseDouble(insert_price_field.getText()) < 0) {
                        boolean answer = YesNoAlert.popUp("Warning!", "Your purchase exceeds your budget. If you proceed, money will be deducted from your next months; budget.");
                        if(answer == false) {

                        }
                        else{
                            categories.get(f).budget = categories.get(f).budget - Double.parseDouble(insert_price_field.getText());
                            categories.get(f).purchased_array.add(new ViewTransactionItems(insert_name_field.getText(), Double.parseDouble(insert_price_field.getText())));
                        }
                    }
                    else {
                        categories.get(f).budget = categories.get(f).budget - Double.parseDouble(insert_price_field.getText());
                        categories.get(f).purchased_array.add(new ViewTransactionItems(insert_name_field.getText(), Double.parseDouble(insert_price_field.getText())));
                    }

                }
            }
            insert_name_field.clear();
            insert_price_field.clear();

        });


        //view_transactions
        Label view_trans_label = new Label("View your purchases.");
        view_trans_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,22.5));
        view_trans_label.setStyle("-fx-text-fill: #bc13fe;");

        Label showing_category = new Label("Showing Purchases from:");
        showing_category.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,13));
        showing_category.setStyle("-fx-text-fill: #bc13fe;");

        Tab view_transactions = new Tab();
        view_transactions.setStyle(" -fx-background-color: #bc13fe;");
        view_transactions.setGraphic(new Label("View Transactions"));
        view_transactions.getGraphic().setStyle("-fx-text-fill: white;");
        Label view_transactions_items_label = new Label("View Transactions");
        view_transactions_items_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,25));
        view_transactions_items_label.setStyle("-fx-text-fill: #bc13fe;");

        TableView view_transaction_table = new TableView();
        view_transaction_table.setMaxHeight(300);
        view_transaction_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<ViewTransactionItems, String> insert_itemname_column = new TableColumn<>("Item Name");
        insert_itemname_column.setCellValueFactory(new PropertyValueFactory<>("item_name"));

        TableColumn<ViewTransactionItems, Double> insert_itemprice_column = new TableColumn<>("Price");
        insert_itemprice_column.setCellValueFactory(new PropertyValueFactory<>("item_price"));

        insert_itemname_column.setStyle( "-fx-alignment: CENTER;");
        insert_itemprice_column.setStyle( "-fx-alignment: CENTER;");

        view_transaction_table.getColumns().addAll(insert_itemname_column, insert_itemprice_column);

        insert_itemprice_column.setCellFactory(tc -> new TableCell<ViewTransactionItems, Double>() {

            //Formats the prices to two decimal places and makes the font colour purple
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setTextFill(Color.rgb(188,19,254));
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
                    setTextFill(Color.rgb(188,19,254));
                }
            }
        });

        select_view_transactions.getSelectionModel().selectedItemProperty().addListener((options, oldval, newval) -> {
            String s = (String) select_view_transactions.getValue();
            for(int i = 0; i<categories.size(); i++) {
                if(categories.get(i).name.equals(s)) {
                    ObservableList view_items = FXCollections.observableArrayList(categories.get(i).purchased_array);
                    view_transaction_table.getItems().setAll(view_items);
                    showing_category.setText("Showing Purchases from: " + s + ".");
                }
            }
            ObservableList table_viewer = FXCollections.observableArrayList();
        });


        transactions_tabpane.getTabs().addAll(new_transaction,view_transactions);
        transactions_tabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        transactions_tabpane.tabMinWidthProperty().bind(transactions_tabpane.widthProperty().divide(transactions_tabpane.getTabs().size()).subtract(22));


        //Instantiates variables from the Queue that stores data such usernames and budget

        String date_now = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String[] user_name =  user_data.get(0).split(" ---- ");

        budget_double = Double.parseDouble(user_data.get(2));
        try {
            money_left = Double.parseDouble(user_data.get(3));
        } catch (IndexOutOfBoundsException e) {
            money_left = budget_double;
        }
        DecimalFormat decimal_places = new DecimalFormat("#.00");
        decimal_places.format(money_left);

        Stage primaryStage = new Stage();



        //Adds previously saved data to the table view
        /*try {
            Scanner s = new Scanner(new File(user_name[0]+"table"));
            String[] lines;
            while (s.hasNext()) {
                String line = "";
                line = line + s.nextLine();
                lines = line.split("----");
                table.getItems().addAll(new TableItems(new String(lines[0]), Double.parseDouble(new String(lines[1]))));
            }


        } catch (Exception e) {

        } */
        /*LinkedListQueue q = new LinkedListQueue();
        String[] lines = new String[1000];
        boolean contains = false;
        for(int i = 0; i< user_data.size(); i++) {
            if(user_data.get(i).equals(date_now)) {
                contains = true;
                String line = user_data.get(i + 1);
                lines = line.split("!!!");
                for(String s : lines) {
                    q.enqueue(s);
                }
                while (!(q.isEmpty())) {
                    String line2 = (String) q.dequeue();
                    String[] lines2 = line2.split("----");
                    table.getItems().addAll(new TableItems(new String(lines2[0]), Double.parseDouble(new String(lines2[1]))));
                }

                }
            }
        if(contains == false) {
            user_data.add(date_now);
        } */


        //Textfields, buttons, and labels for the Main Menu



        Label LogLabel = new Label("Your Spending");
        Label budget_label = new Label("Your Weekly financelog.Budget: " + "$" + decimal_places.format(budget_double));
        Label money_left_label = new Label("You have $" + decimal_places.format(money_left) + " left.");

        LogLabel.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,25));
        LogLabel.setStyle("-fx-text-fill: #bc13fe;");
        budget_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,35));
        budget_label.setStyle("-fx-text-fill: #bc13fe;");
        money_left_label.setFont(Font.font("Segoe UI Light", FontWeight.BOLD,35));
        money_left_label.setStyle("-fx-text-fill: #bc13fe;");

        //Inputs for the table view


        /*Button add = new Button(" Add ");
        add.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: green");
        add.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 11));
        add.setOnAction(e -> {
            try {
                table.getItems().add(new BudgetItems(ItemInput.getText(), Double.parseDouble(PriceInput.getText())));
                money_left = money_left - Double.parseDouble(PriceInput.getText());
                money_left_label.setText("You have $" + decimal_places.format(money_left) + " left.");
                user_data.set(3, Double.toString(money_left));
            }catch(NumberFormatException numberFormatException) {
                OkAlert.popUp("Error", "Please add a number in the price column", Color.RED, Color.WHITE);
            } finally {
                ItemInput.clear();
                PriceInput.clear();
            }

        }); */

        /*Button delete = new Button("Delete"); //Deletes a selected row in the tableview
        delete.setStyle("-fx-background-color: #FFFFFF");
        delete.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: red");
        delete.setFont(Font.font("Segoe UI Light", FontWeight.BOLD, 11));
        delete.setOnAction(e -> {
            BudgetItems t = (BudgetItems) table.getSelectionModel().getSelectedItem();
            money_left += t.getPrice();
            money_left_label.setText("You have $" + decimal_places.format(money_left) + " left.");
            ObservableList<BudgetItems> SelectedRow, AllRows;
            AllRows = table.getItems();
            SelectedRow = table.getSelectionModel().getSelectedItems();
            SelectedRow.forEach(AllRows::remove);
            user_data.set(3, Double.toString(money_left));
        }); */

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    //ObservableList<TableItems> AllRows;
                    //AllRows = table.getItems();
                    /*for(TableItems items : AllRows) {
                        String row_data = items.getItem() + "----" + items.getPrice();
                        f.writeUser(row_data);
                        f.flush();
                    } */

                    FinanceWrite f = new FinanceWrite(user_name[0], false);
                    for(String s : user_data) {
                        f.writeUser(s);
                    }
                    f.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });




        /*HBox buttons = new HBox();
        buttons.getChildren().addAll(add, delete);
        buttons.setAlignment(Pos.CENTER);

        VBox budget_labels_box = new VBox();
        budget_labels_box.getChildren().addAll(budget_label, money_left_label);

        VBox TableBox = new VBox();
        TableBox.getChildren().addAll(LogLabel, table, ItemInput, PriceInput, buttons);
        TableBox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(1), null)));
        TableBox.setPadding(new Insets(0, 30, 0, 0));

        BorderPane left_pane = new BorderPane();
        left_pane.setCenter(TableBox);


        BorderPane right_pane = new BorderPane();
        right_pane.setTop(budget_labels_box);
        right_pane.setPadding(new Insets(0, 30, 0, 30)); */


        //Budget Tab
        VBox budget_items = new VBox();
        budget_items.setSpacing(10);
        budget_items.getChildren().addAll(budget_items_label, budget_table, budget_name_input, budget_input, add_budget);
        budget_items.setAlignment(Pos.CENTER);
        BorderPane budget_pane = new BorderPane();
        budget_pane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(1), null)));
        budget_pane.setTop(budget_items);
        budget_pane.setPadding(new Insets(20,100,20,100));
        budgets_tab.setContent(budget_pane);

        //New Transaction Tab
        VBox new_trans_items = new VBox();
        new_trans_items.getChildren().addAll(new_trans_label,insert_name_field,insert_price_field, what_budget, select_category, add_item);
        new_trans_items.setAlignment(Pos.CENTER);
        new_trans_items.setPadding(new Insets(0,30,0,30));
        new_trans_items.setSpacing(15);
        BorderPane new_transaction_pane = new BorderPane();
        new_transaction_pane.setCenter(new_trans_items);
        new_transaction_pane.setPadding(new Insets(0,20,20,20));
        new_transaction.setContent(new_transaction_pane);

        //View Transaction Tab
        VBox view_trans_items = new VBox();
        HBox combo_label = new HBox();
        combo_label.getChildren().addAll(select_view_transactions, showing_category);
        combo_label.setAlignment(Pos.CENTER);
        combo_label.setSpacing(5);
        view_trans_items.getChildren().addAll(view_trans_label, combo_label, view_transaction_table);
        view_trans_items.setPadding(new Insets(20,20,20,20));
        view_trans_items.setSpacing(10);
        view_trans_items.setAlignment(Pos.CENTER);
        view_transactions.setContent(view_trans_items);

        //Transactions Tab
        BorderPane transactions_pane = new BorderPane();
        VBox transaction_items = new VBox();
        transaction_items.getChildren().addAll(transactions_items_label,transactions_tabpane);
        transaction_items.setAlignment(Pos.CENTER);
        transactions_pane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(1), null)));
        transactions_pane.setTop(transaction_items);
        transactions_tab.setContent(transactions_pane);

        main_screen.getTabs().addAll(transactions_tab, budgets_tab, subscriptions_tab, goals_tab, graphs_tab);
        main_screen.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Scene main_scene = new Scene(main_screen);

        main_scene.getStylesheets().add(getClass().getResource("/resources/tablestyle.css").toExternalForm());



        primaryStage.setScene(main_scene);
        primaryStage.setTitle("Menu");
        primaryStage.setResizable(false);
        primaryStage.setWidth(880);
        primaryStage.setHeight(570);
        primaryStage.show();

    }

}
