package busybee.promotion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class PromotionListController {

    @FXML private TableView<PromotionRow> promotionTable;

    @FXML private TableColumn<PromotionRow, String> colName;
    @FXML private TableColumn<PromotionRow, String> colService;
    @FXML private TableColumn<PromotionRow, String> colDiscountType;
    @FXML private TableColumn<PromotionRow, String> colDiscountValue;
    @FXML private TableColumn<PromotionRow, String> colStartDate;
    @FXML private TableColumn<PromotionRow, String> colEndDate;
    @FXML private TableColumn<PromotionRow, String> colStatus;
    @FXML private TableColumn<PromotionRow, Void> colAction;

    @FXML private TextField txtSearch;
    @FXML private ComboBox<String> cbStatus;
    @FXML private ComboBox<String> cbService;
    @FXML private Button btnCreate;

    private final ObservableList<PromotionRow> masterData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        // =========================
        // COLUMN BINDING
        // =========================

        colName.setCellValueFactory(c -> c.getValue().nameProperty());
        colService.setCellValueFactory(c -> c.getValue().serviceProperty());
        colDiscountType.setCellValueFactory(c -> c.getValue().discountTypeProperty());
        colDiscountValue.setCellValueFactory(c -> c.getValue().discountValueProperty());
        colStartDate.setCellValueFactory(c -> c.getValue().startDateProperty());
        colEndDate.setCellValueFactory(c -> c.getValue().endDateProperty());
        colStatus.setCellValueFactory(c -> c.getValue().statusProperty());

        setupStatusColor();
        setupActionColumn();
        setupStatusFilter();
        setupServiceFilter();

        loadMockData();

        // =========================
        // FILTER SYSTEM
        // =========================

        FilteredList<PromotionRow> filtered = new FilteredList<>(masterData, p -> true);

        Runnable applyFilter = () -> {

            String keyword = txtSearch.getText();
            String status = cbStatus.getValue();
            String service = cbService.getValue();

            filtered.setPredicate(promo -> {

                boolean matchSearch = true;
                boolean matchStatus = true;
                boolean matchService = true;

                // SEARCH
                if (keyword != null && !keyword.isEmpty()) {

                    String lower = keyword.toLowerCase();

                    matchSearch =
                            promo.getName().toLowerCase().contains(lower)
                            || promo.getService().toLowerCase().contains(lower);
                }

                // STATUS
                if (status != null && !status.equals("All")) {
                    matchStatus = promo.getStatus().equals(status);
                }

                // SERVICE
                if (service != null && !service.equals("All")) {
                    matchService = promo.getService().equals(service);
                }

                return matchSearch && matchStatus && matchService;

            });

        };

        txtSearch.textProperty().addListener((obs,o,n)->applyFilter.run());
        cbStatus.valueProperty().addListener((obs,o,n)->applyFilter.run());
        cbService.valueProperty().addListener((obs,o,n)->applyFilter.run());

        promotionTable.setItems(filtered);

        // =========================
        // CREATE BUTTON
        // =========================

        btnCreate.setOnAction(e -> {

    busybee.layout.MainLayoutController
            .getInstance()
            .loadContent("/busybee/promotion/promotion_create.xml");

});

    }

    // =========================
    // STATUS COLOR
    // =========================

    private void setupStatusColor() {

        colStatus.setCellFactory(column -> new TableCell<>() {

            @Override
            protected void updateItem(String status, boolean empty) {

                super.updateItem(status, empty);

                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                    return;
                }

                setText(status);

                switch (status) {

                    case "Active" ->
                        setStyle("-fx-background-color:#d4edda; -fx-text-fill:#155724;");

                    case "Expired" ->
                        setStyle("-fx-background-color:#f8d7da; -fx-text-fill:#721c24;");

                    case "Inactive" ->
                        setStyle("-fx-background-color:#e2e3e5; -fx-text-fill:#383d41;");
                }
            }
        });

    }

    // =========================
    // ACTION BUTTON
    // =========================

    private void setupActionColumn() {

        colAction.setCellFactory(col -> new TableCell<>() {

            private final Button btnEdit = new Button("Edit");
            private final Button btnDisable = new Button("Disable");

            private final HBox box = new HBox(8, btnEdit, btnDisable);

            {

                btnEdit.setOnAction(e -> {

                    PromotionRow row = getTableView().getItems().get(getIndex());
                    System.out.println("Edit: " + row.getName());

                });

                btnDisable.setOnAction(e -> {

                    PromotionRow row = getTableView().getItems().get(getIndex());
                    row.setStatus("Inactive");

                    promotionTable.refresh();

                });

            }

            @Override
            protected void updateItem(Void item, boolean empty) {

                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(box);
                }

            }

        });

    }

    // =========================
    // STATUS FILTER
    // =========================

    private void setupStatusFilter() {

        cbStatus.getItems().addAll(
                "All",
                "Active",
                "Inactive",
                "Expired"
        );

        cbStatus.setValue("All");

    }

    // =========================
    // SERVICE FILTER
    // =========================

    private void setupServiceFilter() {

        cbService.getItems().addAll(
                "All",
                "Home Cleaning",
                "Office Cleaning",
                "Babysitting"
        );

        cbService.setValue("All");

    }

    // =========================
    // MOCK DATA
    // =========================

    private void loadMockData() {

        masterData.addAll(

                new PromotionRow(
                        "Summer Sale",
                        "Home Cleaning",
                        "Percent",
                        "20%",
                        "01-06-2026",
                        "30-06-2026",
                        "Active"
                ),

                new PromotionRow(
                        "Babysit Promo",
                        "Babysitting",
                        "Fixed",
                        "50000",
                        "01-05-2026",
                        "31-05-2026",
                        "Expired"
                ),

                new PromotionRow(
                        "Office Discount",
                        "Office Cleaning",
                        "Percent",
                        "15%",
                        "10-06-2026",
                        "30-07-2026",
                        "Active"
                )

        );

    }

}