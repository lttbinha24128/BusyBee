package busybee.customer.controller;

import busybee.child.ChildRow;
import busybee.customer.model.CustomerBillingRow;
import busybee.customer.model.CustomerRequestRow;
import busybee.customer.model.CustomerRow;
import busybee.customer.service.CustomerDetailService;
import busybee.util.DialogUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class CustomerDetailController {

    // =========================
    // SERVICE
    // =========================
    private final CustomerDetailService service = new CustomerDetailService();

    // =========================
    // CUSTOMER INFO
    // =========================

    @FXML private Label lblNameHeader;
    @FXML private Label lblName;
    @FXML private Label lblPhone;
    @FXML private Label lblEmail;
    @FXML private Label lblAddress;
    @FXML private Label lblStatus;

    private CustomerRow currentCustomer;

    private boolean hasUnsavedChanges = false;

    // =========================
    // ACTION BUTTON
    // =========================

    @FXML private Button btnActions;

    private ContextMenu actionsMenu;
    private MenuItem toggleActiveItem;

    // =========================
    // TABLES
    // =========================

    @FXML private TableView<CustomerRequestRow> requestTable;
    @FXML private TableColumn<CustomerRequestRow, String> colService;
    @FXML private TableColumn<CustomerRequestRow, String> colDate;
    @FXML private TableColumn<CustomerRequestRow, String> colRequestStatus;

    @FXML private TableView<CustomerBillingRow> billingTable;
    @FXML private TableColumn<CustomerBillingRow, String> colInvoice;
    @FXML private TableColumn<CustomerBillingRow, String> colBillingDate;
    @FXML private TableColumn<CustomerBillingRow, String> colAmount;
    @FXML private TableColumn<CustomerBillingRow, String> colBillingStatus;

    @FXML private TableView<ChildRow> childrenTable;
    @FXML private TableColumn<ChildRow, String> colChildName;
    @FXML private TableColumn<ChildRow, Integer> colChildAge;
    @FXML private TableColumn<ChildRow, String> colChildNote;
    @FXML private TableColumn<ChildRow, Void> colChildAction;

    private final ObservableList<CustomerRequestRow> requestData = FXCollections.observableArrayList();
    private final ObservableList<CustomerBillingRow> billingData = FXCollections.observableArrayList();
    private final ObservableList<ChildRow> childrenData = FXCollections.observableArrayList();

    // =========================
    // INIT
    // =========================

    @FXML
    private void initialize() {

        setupTables();
        setupActionsMenu();
        loadData();
    }

    // =========================
    // SET CUSTOMER
    // =========================

    public void setCustomer(CustomerRow customer) {

        currentCustomer = customer;

        lblNameHeader.setText(customer.getName());
        lblName.setText(customer.getName());
        lblPhone.setText(customer.getPhone());
        lblEmail.setText(customer.getEmail());
        lblAddress.setText(customer.getAddress());

        updateStatusUI(customer.getStatus());
    }

    // =========================
    // STATUS UI
    // =========================

    private void updateStatusUI(String status) {

        lblStatus.setText(status);

        lblStatus.getStyleClass().removeAll(
                "customer-status-active",
                "customer-status-inactive"
        );

        if (status.equalsIgnoreCase("Active")) {
            lblStatus.getStyleClass().add("customer-status-active");
        } else {
            lblStatus.getStyleClass().add("customer-status-inactive");
        }

        if (toggleActiveItem != null) {
            toggleActiveItem.setText(
                    status.equalsIgnoreCase("Active")
                            ? "Deactivate Account"
                            : "Activate Account"
            );
        }
    }

    // =========================
    // ACTION MENU
    // =========================

    private void setupActionsMenu() {

        MenuItem editItem = new MenuItem("Edit Customer");
        toggleActiveItem = new MenuItem("Deactivate Account");
        MenuItem deleteItem = new MenuItem("Delete Customer");

        actionsMenu = new ContextMenu(editItem, toggleActiveItem, deleteItem);

        btnActions.setOnAction(e ->
                actionsMenu.show(btnActions, Side.BOTTOM, 0, 0)
        );

        toggleActiveItem.setOnAction(e -> toggleCustomerStatus());
        deleteItem.setOnAction(e -> deleteCustomer());
    }

    // =========================
    // ACTIONS
    // =========================

    private void toggleCustomerStatus() {

        String newStatus = service.toggleStatus(lblStatus.getText());

        updateStatusUI(newStatus);

        hasUnsavedChanges = true;
    }

    private void deleteCustomer() {

        boolean confirmed = DialogUtils.showConfirmation(
                "Delete Customer",
                "Are you sure you want to delete this customer?"
        );

        if (confirmed) {
            System.out.println("Deleted");
        }
    }

    // =========================
    // TABLE SETUP
    // =========================

    private void setupTables() {

        colService.setCellValueFactory(new PropertyValueFactory<>("service"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRequestStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        requestTable.setItems(requestData);

        colInvoice.setCellValueFactory(new PropertyValueFactory<>("invoice"));
        colBillingDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colBillingStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        billingTable.setItems(billingData);

        colChildName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colChildAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colChildNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        childrenTable.setItems(childrenData);
    }

    // =========================
    // LOAD DATA
    // =========================

    private void loadData() {

        requestData.setAll(service.getRequestData());
        billingData.setAll(service.getBillingData());
        childrenData.setAll(service.getChildrenData());
    }
}