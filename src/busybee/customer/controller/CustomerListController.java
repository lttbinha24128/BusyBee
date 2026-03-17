package busybee.customer.controller;

import busybee.auth.Role;
import busybee.auth.Session;
import busybee.customer.model.CustomerRow;
import busybee.customer.service.CustomerListService;
import busybee.request.controller.RequestCreateController;
import busybee.util.DialogUtils;
import busybee.util.StageManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.HBox;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class CustomerListController {

    // =========================
    // SERVICE
    // =========================
    private final CustomerListService service = new CustomerListService();

    // =========================
    // UI
    // =========================
    @FXML private TableView<CustomerRow> customerTable;

    @FXML private TableColumn<CustomerRow,String> colName;
    @FXML private TableColumn<CustomerRow,String> colPhone;
    @FXML private TableColumn<CustomerRow,String> colEmail;
    @FXML private TableColumn<CustomerRow,String> colAddress;
    @FXML private TableColumn<CustomerRow,String> colStatus;
    @FXML private TableColumn<CustomerRow,Void> colAction;

    @FXML private TextField txtSearch;
    @FXML private ComboBox<String> cbStatus;

    @FXML private Button btnCreate;

    private final ObservableList<CustomerRow> masterData = FXCollections.observableArrayList();
    private FilteredList<CustomerRow> filteredData;

    private final Map<CustomerRow, Stage> openStages = new HashMap<>();

    // =========================
    // INIT
    // =========================
    @FXML
    private void initialize() {

        setupTable();
        setupFilters();
        setupRowDoubleClick();
        loadData();

        setupRoleUI();

        btnCreate.setOnAction(e -> openCreateCustomerWindow());
    }

    // =========================
    // ROLE UI
    // =========================
    private void setupRoleUI() {

        if (Session.hasRole(Role.RECEPTIONIST)) {
            colStatus.setVisible(false);

            cbStatus.setVisible(false);
            cbStatus.setManaged(false);
        }
    }

    // =========================
    // TABLE
    // =========================
    private void setupTable() {

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        setupActionColumn();

        filteredData = new FilteredList<>(masterData, p -> true);
        customerTable.setItems(filteredData);
    }

    // =========================
    // ACTION COLUMN
    // =========================
    private void setupActionColumn() {

        if (Session.hasRole(Role.RECEPTIONIST)) {
            setupReceptionAction();
        } else {
            setupDirectorAction();
        }
    }

    // 👉 LỄ TÂN
    private void setupReceptionAction() {

        colAction.setCellFactory(param -> new TableCell<>() {

            private final Button btnCreateRequest = new Button("Create Request");
            private final Button btnView = new Button("View");
            private final HBox pane = new HBox(8, btnCreateRequest, btnView);

            private CustomerRow getCurrent() {
                return getTableRow().getItem();
            }

            {
                btnCreateRequest.setOnAction(e -> {
                    CustomerRow c = getCurrent();
                    if (c != null) {
                        openCreateRequest(c);
                    }
                });

                btnView.setOnAction(e -> {
                    CustomerRow c = getCurrent();
                    if (c != null) {
                        openCustomerDetail(c);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {

                super.updateItem(item, empty);

                if (empty || getCurrent() == null) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });
    }

    // 👉 GIÁM ĐỐC
    private void setupDirectorAction() {

        colAction.setCellFactory(param -> new TableCell<>() {

            private final Button btnEdit = new Button("Edit");
            private final Button btnToggle = new Button();
            private final HBox pane = new HBox(8, btnEdit, btnToggle);

            private CustomerRow getCurrent() {
                return getTableRow().getItem();
            }

            {
                btnEdit.setOnAction(e -> openEditCustomerWindow(getCurrent()));

                btnToggle.setOnAction(e -> {
                    CustomerRow c = getCurrent();
                    if (c != null && confirmToggle()) {
                        service.toggleStatus(c);
                        customerTable.refresh();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {

                super.updateItem(item, empty);

                CustomerRow c = getCurrent();

                if (empty || c == null) {
                    setGraphic(null);
                    return;
                }

                btnToggle.setText(
                        "Active".equals(c.getStatus())
                                ? "Deactivate"
                                : "Activate"
                );

                setGraphic(pane);
            }
        });
    }

    // =========================
    // FILTER
    // =========================
    private void setupFilters() {

        cbStatus.getItems().addAll("All","Active","Inactive");
        cbStatus.setValue("All");

        txtSearch.textProperty().addListener((obs,o,n)->applyFilter());
        cbStatus.valueProperty().addListener((obs,o,n)->applyFilter());
    }

    private void applyFilter() {

        String keyword = txtSearch.getText() == null ? "" : txtSearch.getText();
        String status = cbStatus.getValue();

        filteredData.setPredicate(c ->
                service.matchFilter(c, keyword, status)
        );
    }

    // =========================
    // CREATE / EDIT
    // =========================
    private void openCreateCustomerWindow() {
        openCustomerForm(null, "Create Customer", customer -> masterData.add(customer));
    }

    private void openEditCustomerWindow(CustomerRow customer) {
        openCustomerForm(customer, "Edit Customer", null);
        customerTable.refresh();
    }

    private void openCustomerForm(CustomerRow customer, String title,
                                  java.util.function.Consumer<CustomerRow> callback) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/busybee/customer/view/customer_create.xml")
            );

            Parent root = loader.load();

            CustomerCreateController controller = loader.getController();

            if (customer != null) {
                controller.setCustomerForEdit(customer);
            }

            if (callback != null) {
                controller.setOnCustomerCreated(callback);
            }

            Stage stage = new Stage();

            Scene scene = new Scene(root);
            scene.getStylesheets().addAll(customerTable.getScene().getStylesheets());

            stage.setScene(scene);
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // CREATE REQUEST
    // =========================
    private void openCreateRequest(CustomerRow customer) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/busybee/request/view/request_create.xml")
            );

            Parent root = loader.load();

            RequestCreateController controller = loader.getController();
            controller.setCustomer(customer);

            Stage stage = new Stage();

            Scene scene = new Scene(root);
            scene.getStylesheets().addAll(customerTable.getScene().getStylesheets());

            stage.setScene(scene);
            stage.setTitle("Create Request");
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // DOUBLE CLICK
    // =========================
    private void setupRowDoubleClick() {

        customerTable.setRowFactory(tv -> {

            TableRow<CustomerRow> row = new TableRow<>();

            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && !row.isEmpty()) {
                    openCustomerDetail(row.getItem());
                }
            });

            return row;
        });
    }

    // =========================
    // DETAIL
    // =========================
    private void openCustomerDetail(CustomerRow customer) {

        if (openStages.containsKey(customer)) {
            openStages.get(customer).toFront();
            return;
        }

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/busybee/customer/view/customer_detail.xml")
            );

            Parent root = loader.load();

            CustomerDetailController controller = loader.getController();
            controller.setCustomer(customer);

            Stage stage = new Stage();

            Scene scene = new Scene(root);
            scene.getStylesheets().addAll(customerTable.getScene().getStylesheets());

            stage.setScene(scene);
            stage.setTitle("Customer Detail");

            stage.setOnHidden(e -> openStages.remove(customer));

            StageManager.getInstance().addStage(stage);

            stage.show();

            openStages.put(customer, stage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // DATA
    // =========================
    private void loadData() {
        masterData.setAll(service.getDummyData());
    }

    // =========================
    // HELPER
    // =========================
    private boolean confirmToggle() {
        return DialogUtils.showConfirmation(
                "Change Status",
                "Change status for this customer?"
        );
    }
}