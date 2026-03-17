package busybee.billing.controller;

import busybee.billing.model.InvoiceRow;
import busybee.billing.model.PaymentRow;
import busybee.util.DialogUtils;
import busybee.util.StageManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.HBox;

public class BillingListController implements Initializable {

    @FXML private TextField txtSearch;
    @FXML private ComboBox<String> cbStatus;
    @FXML private Button btnCreatePayment, btnCreateInvoice;
    @FXML private TabPane billingTabPane;
    @FXML private TableView<PaymentRow> paymentsTable;
    @FXML private TableColumn<PaymentRow, String> colPaymentId, colRequestId, colAmount, colMethod, colPaidAt, colPaymentStatus, colReceivedBy;
    @FXML private TableColumn<PaymentRow, Void> colPaymentAction;
    @FXML private TableView<InvoiceRow> invoicesTable;
    @FXML private TableColumn<InvoiceRow, String> colInvoiceId, colInvoiceRequestId, colInvoiceNo, colIssuedAt, colTotalAmount, colInvoiceStatus, colGeneratedBy;
    @FXML private TableColumn<InvoiceRow, Void> colInvoiceAction;

    private ObservableList<PaymentRow> payments = FXCollections.observableArrayList();
    private ObservableList<InvoiceRow> invoices = FXCollections.observableArrayList();
    private FilteredList<PaymentRow> filteredPayments;
    private FilteredList<InvoiceRow> filteredInvoices;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTables();
        setupFilters();
        setupButtons();
        loadData();
    }

    private void setupTables() {
        // Payments Table
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colRequestId.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("formattedAmount"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("method"));
        colPaidAt.setCellValueFactory(new PropertyValueFactory<>("formattedPaidAt"));
        colPaymentStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colReceivedBy.setCellValueFactory(new PropertyValueFactory<>("receivedBy"));

        // Status column with badges
        colPaymentStatus.setCellFactory(column -> new TableCell<PaymentRow, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);
                    setStyle("-fx-alignment: CENTER;");
                    getStyleClass().add("status-badge");
                    if ("PAID".equals(status)) {
                        getStyleClass().add("status-paid");
                    } else if ("PENDING".equals(status)) {
                        getStyleClass().add("status-pending");
                    } else if ("CANCELLED".equals(status)) {
                        getStyleClass().add("status-cancelled");
                    }
                }
            }
        });

        // Action column for payments
        colPaymentAction.setCellFactory(column -> new TableCell<PaymentRow, Void>() {
            private final Button btnView = new Button("👁 View");
            private final Button btnEdit = new Button("✏ Edit");

            {
                btnView.getStyleClass().add("btn-action");
                btnEdit.getStyleClass().add("btn-action");

                btnView.setOnAction(event -> {
                    PaymentRow payment = getTableView().getItems().get(getIndex());
                    openPaymentDetail(payment);
                });

                btnEdit.setOnAction(event -> {
                    PaymentRow payment = getTableView().getItems().get(getIndex());
                    openPaymentCreate(payment);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(5, btnView, btnEdit);
                    hbox.setAlignment(javafx.geometry.Pos.CENTER);
                    setGraphic(hbox);
                }
            }
        });

        // Double-click to view payment
        paymentsTable.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                PaymentRow selected = paymentsTable.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    openPaymentDetail(selected);
                }
            }
        });

        // Invoices Table
        colInvoiceId.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        colInvoiceRequestId.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        colInvoiceNo.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));
        colIssuedAt.setCellValueFactory(new PropertyValueFactory<>("formattedIssuedAt"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("formattedTotalAmount"));
        colInvoiceStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colGeneratedBy.setCellValueFactory(new PropertyValueFactory<>("generatedBy"));

        // Status column with badges for invoices
        colInvoiceStatus.setCellFactory(column -> new TableCell<InvoiceRow, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);
                    setStyle("-fx-alignment: CENTER;");
                    getStyleClass().add("status-badge");
                    if ("ISSUED".equals(status)) {
                        getStyleClass().add("status-issued");
                    } else if ("PAID".equals(status)) {
                        getStyleClass().add("status-paid");
                    } else if ("CANCELLED".equals(status)) {
                        getStyleClass().add("status-cancelled");
                    }
                }
            }
        });

        // Action column for invoices
        colInvoiceAction.setCellFactory(column -> new TableCell<InvoiceRow, Void>() {
            private final Button btnView = new Button("👁 View");
            private final Button btnEdit = new Button("✏ Edit");

            {
                btnView.getStyleClass().add("btn-action");
                btnEdit.getStyleClass().add("btn-action");

                btnView.setOnAction(event -> {
                    InvoiceRow invoice = getTableView().getItems().get(getIndex());
                    openInvoiceDetail(invoice);
                });

                btnEdit.setOnAction(event -> {
                    InvoiceRow invoice = getTableView().getItems().get(getIndex());
                    openInvoiceCreate(invoice);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(5, btnView, btnEdit);
                    hbox.setAlignment(javafx.geometry.Pos.CENTER);
                    setGraphic(hbox);
                }
            }
        });

        // Double-click to view invoice
        invoicesTable.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                InvoiceRow selected = invoicesTable.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    openInvoiceDetail(selected);
                }
            }
        });
    }

    private void setupFilters() {
        // Status filter options
        cbStatus.setItems(FXCollections.observableArrayList("ALL", "PAID", "PENDING", "ISSUED", "CANCELLED"));

        // Search filter
        txtSearch.textProperty().addListener((obs, oldText, newText) -> {
            applyFilters();
        });

        // Status filter
        cbStatus.valueProperty().addListener((obs, oldValue, newValue) -> {
            applyFilters();
        });
    }

    private void setupButtons() {
        btnCreatePayment.setOnAction(event -> openPaymentCreate(null));
        btnCreateInvoice.setOnAction(event -> openInvoiceCreate(null));
    }

    private void loadData() {
        // Mock data for payments
        payments.addAll(
            new PaymentRow("P001", "R001", 150.00, "CASH", "2024-01-15 10:30", "PAID", "John Doe"),
            new PaymentRow("P002", "R002", 200.00, "CARD", "2024-01-16 14:20", "PAID", "Jane Smith"),
            new PaymentRow("P003", "R003", 75.00, "BANK", "2024-01-17 09:15", "PENDING", "Bob Johnson")
        );

        // Mock data for invoices
        invoices.addAll(
            new InvoiceRow("I001", "R001", "INV-2024-001", "2024-01-15 10:30", 150.00, "ISSUED", "Alice Brown"),
            new InvoiceRow("I002", "R002", "INV-2024-002", "2024-01-16 14:20", 200.00, "PAID", "Charlie Wilson"),
            new InvoiceRow("I003", "R003", "INV-2024-003", "2024-01-17 09:15", 75.00, "ISSUED", "Diana Davis")
        );

        filteredPayments = new FilteredList<>(payments, p -> true);
        filteredInvoices = new FilteredList<>(invoices, p -> true);

        paymentsTable.setItems(filteredPayments);
        invoicesTable.setItems(filteredInvoices);
    }

    private void applyFilters() {
        String searchText = txtSearch.getText().toLowerCase();
        String statusFilter = cbStatus.getValue();

        filteredPayments.setPredicate(payment -> {
            boolean matchesSearch = searchText.isEmpty() ||
                payment.getPaymentId().toLowerCase().contains(searchText) ||
                payment.getRequestId().toLowerCase().contains(searchText) ||
                payment.getMethod().toLowerCase().contains(searchText) ||
                payment.getReceivedBy().toLowerCase().contains(searchText);

            boolean matchesStatus = statusFilter == null || "ALL".equals(statusFilter) ||
                statusFilter.equals(payment.getStatus());

            return matchesSearch && matchesStatus;
        });

        filteredInvoices.setPredicate(invoice -> {
            boolean matchesSearch = searchText.isEmpty() ||
                invoice.getInvoiceId().toLowerCase().contains(searchText) ||
                invoice.getRequestId().toLowerCase().contains(searchText) ||
                invoice.getInvoiceNo().toLowerCase().contains(searchText) ||
                invoice.getGeneratedBy().toLowerCase().contains(searchText);

            boolean matchesStatus = statusFilter == null || "ALL".equals(statusFilter) ||
                statusFilter.equals(invoice.getStatus());

            return matchesSearch && matchesStatus;
        });
    }

    private void openPaymentDetail(PaymentRow payment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("billing_detail.xml"));
            Parent root = loader.load();

            BillingDetailController controller = loader.getController();
            controller.setPayment(payment);

            Stage stage = new Stage();
            stage.setTitle("Payment Details - " + payment.getPaymentId());
            stage.setScene(new Scene(root, 600, 500));
            stage.initModality(Modality.NONE);
            stage.setResizable(false);
            stage.show();

            StageManager.getInstance().addStage(stage);

        } catch (IOException e) {
            DialogUtils.showError("Error", "Failed to open payment details: " + e.getMessage());
        }
    }

    private void openInvoiceDetail(InvoiceRow invoice) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("billing_detail.xml"));
            Parent root = loader.load();

            BillingDetailController controller = loader.getController();
            controller.setInvoice(invoice);

            Stage stage = new Stage();
            stage.setTitle("Invoice Details - " + invoice.getInvoiceId());
            stage.setScene(new Scene(root, 600, 500));
            stage.initModality(Modality.NONE);
            stage.setResizable(false);
            stage.show();

            StageManager.getInstance().addStage(stage);

        } catch (IOException e) {
            DialogUtils.showError("Error", "Failed to open invoice details: " + e.getMessage());
        }
    }

    private void openPaymentCreate(PaymentRow payment) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("billing_create.xml"));
            Parent root = loader.load();

            BillingCreateController controller = loader.getController();
            controller.setPayment(payment);

            Stage stage = new Stage();
            stage.setTitle(payment == null ? "Create Payment" : "Edit Payment - " + payment.getPaymentId());
            stage.setScene(new Scene(root, 700, 600));
            stage.initModality(Modality.NONE);
            stage.setResizable(false);

            if (payment == null) {
                stage.setOnCloseRequest(event -> {
                    if (controller.hasUnsavedChanges()) {
                        boolean confirm = DialogUtils.showConfirmation(
                            "Unsaved Changes",
                            "You have unsaved changes. Do you want to save them before closing?"
                        );
                        if (confirm) {
                            event.consume();
                            controller.savePayment();
                        }
                    }
                });
            }

            stage.show();
            StageManager.getInstance().addStage(stage);

        } catch (IOException e) {
            DialogUtils.showError("Error", "Failed to open payment form: " + e.getMessage());
        }
    }

    private void openInvoiceCreate(InvoiceRow invoice) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("billing_create.xml"));
            Parent root = loader.load();

            BillingCreateController controller = loader.getController();
            controller.setInvoice(invoice);

            Stage stage = new Stage();
            stage.setTitle(invoice == null ? "Create Invoice" : "Edit Invoice - " + invoice.getInvoiceId());
            stage.setScene(new Scene(root, 700, 600));
            stage.initModality(Modality.NONE);
            stage.setResizable(false);

            if (invoice == null) {
                stage.setOnCloseRequest(event -> {
                    if (controller.hasUnsavedChanges()) {
                        boolean confirm = DialogUtils.showConfirmation(
                            "Unsaved Changes",
                            "You have unsaved changes. Do you want to save them before closing?"
                        );
                        if (confirm) {
                            event.consume();
                            controller.saveInvoice();
                        }
                    }
                });
            }

            stage.show();
            StageManager.getInstance().addStage(stage);

        } catch (IOException e) {
            DialogUtils.showError("Error", "Failed to open invoice form: " + e.getMessage());
        }
    }
}