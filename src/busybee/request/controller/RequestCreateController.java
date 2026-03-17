package busybee.request.controller;

import busybee.customer.model.CustomerRow;
import busybee.request.model.RequestItemRow;
import busybee.request.model.RequestRow;
import busybee.request.service.RequestService;
import busybee.util.DialogUtils;
import busybee.util.ValidationUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Consumer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;

public class RequestCreateController {

    // =========================
    // UI
    // =========================
    @FXML private Label lblCustomerName;
    @FXML private Label lblPhone;

    @FXML private ComboBox<String> cbEmployee;
    @FXML private DatePicker dpRequestDate;
    @FXML private DatePicker dpScheduledStart;
    @FXML private ComboBox<String> cbStatus;
    @FXML private TextArea txtNotes;

    @FXML private ComboBox<String> cbService;
    @FXML private TextField txtQuantity;
    @FXML private Button btnAddItem;

    @FXML private TableView<RequestItemRow> itemsTable;
    @FXML private TableColumn<RequestItemRow, String> colServiceName;
    @FXML private TableColumn<RequestItemRow, Integer> colQuantity;

    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    // =========================
    // SERVICE
    // =========================
    private final RequestService requestService = new RequestService();

    // =========================
    // DATA
    // =========================
    private CustomerRow customer;

    private Consumer<RequestRow> onRequestCreated;

    private final ObservableList<RequestItemRow> itemsData =
            FXCollections.observableArrayList();

    // =========================
    // INIT
    // =========================
    @FXML
    private void initialize() {

        cbEmployee.getItems().addAll(
                "Alice Johnson",
                "Bob Wilson",
                "Charlie Brown"
        );

        cbService.getItems().addAll(
                "Home Cleaning",
                "Office Cleaning",
                "Deep Cleaning",
                "Post-Event Cleanup"
        );

        cbStatus.getItems().addAll(
                "Pending","Confirmed","In Progress","Completed","Cancelled"
        );

        cbStatus.setValue("Pending");

        dpRequestDate.setValue(LocalDate.now());
        dpScheduledStart.setValue(LocalDate.now().plusDays(1));

        colServiceName.setCellValueFactory(
                new PropertyValueFactory<>("serviceName")
        );

        colQuantity.setCellValueFactory(
                new PropertyValueFactory<>("quantity")
        );

        itemsTable.setItems(itemsData);

        btnAddItem.setOnAction(e -> addItem());
        btnSave.setOnAction(e -> saveRequest());
        btnCancel.setOnAction(e -> closeWindow());
    }

    // =========================
    // SET CUSTOMER
    // =========================
    public void setCustomer(CustomerRow customer) {
        this.customer = customer;

        if (customer != null) {
            lblCustomerName.setText(customer.getName());
            lblPhone.setText(customer.getPhone());
        }
    }

    // =========================
    // ADD ITEM
    // =========================
    private void addItem() {

        String service = cbService.getValue();
        String qtyText = txtQuantity.getText();

        if (service == null) {
            DialogUtils.showError("Validation Error","Select a service");
            return;
        }

        try {

            int quantity = Integer.parseInt(qtyText);

            RequestItemRow item =
                    requestService.createItem(service, quantity);

            itemsData.add(item);

            cbService.setValue(null);
            txtQuantity.clear();

        } catch (Exception ex) {

            DialogUtils.showError("Validation Error","Invalid quantity");

        }
    }

    // =========================
    // SAVE
    // =========================
    private void saveRequest() {

        if (!validateForm()) return;

        LocalDateTime requestDate =
                dpRequestDate.getValue().atStartOfDay();

        LocalDateTime scheduledStart =
                dpScheduledStart.getValue().atTime(LocalTime.of(9,0));

        RequestRow newRequest = requestService.createRequest(
                customer,
                cbEmployee.getValue(),
                requestDate,
                scheduledStart,
                cbStatus.getValue(),
                txtNotes.getText(),
                itemsData
        );

        if (onRequestCreated != null) {
            onRequestCreated.accept(newRequest);
        }

        DialogUtils.showInfo("Success","Request created");

        closeWindow();
    }

    // =========================
    // VALIDATE
    // =========================
    private boolean validateForm() {

        if (!requestService.isValid(customer, itemsData)) {
            DialogUtils.showError("Validation Error",
                    "Customer or service list is invalid");
            return false;
        }

        return true;
    }

    // =========================
    // CLOSE
    // =========================
    private void closeWindow() {
        ((Stage) btnSave.getScene().getWindow()).close();
    }

    // =========================
    // CALLBACK
    // =========================
    public void setOnRequestCreated(Consumer<RequestRow> callback) {
        this.onRequestCreated = callback;
    }
}