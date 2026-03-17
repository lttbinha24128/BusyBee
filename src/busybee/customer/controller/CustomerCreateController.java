package busybee.customer.controller;

import busybee.customer.model.CustomerRow;
import busybee.customer.service.CustomerCreateService;
import busybee.util.DialogUtils;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class CustomerCreateController {

    @FXML private TextField txtName;
    @FXML private TextField txtPhone;
    @FXML private TextField txtEmail;
    @FXML private TextField txtAddress;

    @FXML private ComboBox<String> cbStatus;

    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    private final CustomerCreateService service = new CustomerCreateService();

    private Consumer<CustomerRow> onCustomerCreated;

    private CustomerRow editingCustomer;

    private boolean hasUnsavedChanges = false;
    private boolean isSettingData = false;

    // =========================
    // INIT
    // =========================

    @FXML
    private void initialize() {

        cbStatus.getItems().addAll("Active", "Inactive");
        cbStatus.setValue("Active");

        trackChanges();

        btnSave.setOnAction(e -> saveCustomer());
        btnCancel.setOnAction(e -> handleCancel());
    }

    // =========================
    // TRACK CHANGES
    // =========================

    private void trackChanges() {

        txtName.textProperty().addListener((obs, o, n) -> {
            if (!isSettingData) hasUnsavedChanges = true;
        });

        txtPhone.textProperty().addListener((obs, o, n) -> {
            if (!isSettingData) hasUnsavedChanges = true;
        });

        txtEmail.textProperty().addListener((obs, o, n) -> {
            if (!isSettingData) hasUnsavedChanges = true;
        });

        txtAddress.textProperty().addListener((obs, o, n) -> {
            if (!isSettingData) hasUnsavedChanges = true;
        });

        cbStatus.valueProperty().addListener((obs, o, n) -> {
            if (!isSettingData) hasUnsavedChanges = true;
        });
    }

    // =========================
    // EDIT MODE
    // =========================

    public void setCustomerForEdit(CustomerRow customer) {

        editingCustomer = customer;

        isSettingData = true;

        txtName.setText(customer.getName());
        txtPhone.setText(customer.getPhone());
        txtEmail.setText(customer.getEmail());
        txtAddress.setText(customer.getAddress());

        cbStatus.setValue(customer.getStatus());

        btnSave.setText("Update Customer");

        isSettingData = false;
    }

    // =========================
    // SAVE
    // =========================

    private void saveCustomer() {

        if (performSave()) {
            closeWindow();
        }
    }

    public boolean performSave() {

        String error = service.validateCustomer(
                txtName.getText(),
                txtPhone.getText(),
                txtEmail.getText()
        );

        if (error != null) {
            DialogUtils.showError("Validation Error", error);
            return false;
        }

        String status = cbStatus.getValue();

        // EDIT
        if (editingCustomer != null) {

            service.updateCustomer(
                    editingCustomer,
                    txtName.getText(),
                    txtPhone.getText(),
                    txtEmail.getText(),
                    txtAddress.getText(),
                    status
            );

            DialogUtils.showInfo("Success", "Customer updated successfully!");
        }

        // CREATE
        else {

            CustomerRow newCustomer = service.createCustomer(
                    txtName.getText(),
                    txtPhone.getText(),
                    txtEmail.getText(),
                    txtAddress.getText(),
                    status
            );

            if (onCustomerCreated != null) {
                onCustomerCreated.accept(newCustomer);
            }

            DialogUtils.showInfo("Success", "Customer created successfully!");
        }

        hasUnsavedChanges = false;

        return true;
    }

    // =========================
    // CANCEL
    // =========================

    private void handleCancel() {

        if (!hasUnsavedChanges) {
            closeWindow();
            return;
        }

        int choice = DialogUtils.showConfirmationWithThreeOptions(
                "Unsaved Changes",
                "You have unsaved changes. What do you want to do?",
                "Save and Close",
                "Don't Save",
                "Cancel"
        );

        if (choice == 1) {

            if (performSave()) {
                closeWindow();
            }

        } else if (choice == 2) {

            closeWindow();
        }
    }

    // =========================
    // SETTER CALLBACK
    // =========================

    public void setOnCustomerCreated(Consumer<CustomerRow> callback) {
        this.onCustomerCreated = callback;
    }

    // =========================
    // HELPER
    // =========================

    private void closeWindow() {

        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}