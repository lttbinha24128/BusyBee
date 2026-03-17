package busybee.billing.controller;

import busybee.billing.model.InvoiceRow;
import busybee.billing.model.PaymentRow;
import busybee.billing.controller.BillingCreateController;
import busybee.util.DialogUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BillingDetailController implements Initializable {

    @FXML private Label lblTitle;
    @FXML private Label lblPaymentId, lblRequestId, lblAmount, lblMethod, lblPaidAt, lblPaymentStatus, lblReceivedBy;
    @FXML private Label lblInvoiceId, lblInvoiceRequestId, lblInvoiceNo, lblIssuedAt, lblTotalAmount, lblInvoiceStatus, lblGeneratedBy;
    @FXML private Button btnEdit, btnClose;

    private PaymentRow payment;
    private InvoiceRow invoice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupButtons();
    }

    public void setPayment(PaymentRow payment) {
        this.payment = payment;
        this.invoice = null;

        lblTitle.setText("Payment Details - " + payment.getPaymentId());

        // Show payment details, hide invoice details
        lblPaymentId.setText(payment.getPaymentId());
        lblRequestId.setText(payment.getRequestId());
        lblAmount.setText(payment.getFormattedAmount());
        lblMethod.setText(payment.getMethod());
        lblPaidAt.setText(payment.getFormattedPaidAt());
        lblPaymentStatus.setText(payment.getStatus());
        lblReceivedBy.setText(payment.getReceivedBy());

        // Apply status styling
        lblPaymentStatus.getStyleClass().removeAll("status-paid", "status-pending", "status-cancelled");
        if ("PAID".equals(payment.getStatus())) {
            lblPaymentStatus.getStyleClass().add("status-paid");
        } else if ("PENDING".equals(payment.getStatus())) {
            lblPaymentStatus.getStyleClass().add("status-pending");
        } else if ("CANCELLED".equals(payment.getStatus())) {
            lblPaymentStatus.getStyleClass().add("status-cancelled");
        }
    }

    public void setInvoice(InvoiceRow invoice) {
        this.invoice = invoice;
        this.payment = null;

        lblTitle.setText("Invoice Details - " + invoice.getInvoiceId());

        // Show invoice details, hide payment details
        lblInvoiceId.setText(invoice.getInvoiceId());
        lblInvoiceRequestId.setText(invoice.getRequestId());
        lblInvoiceNo.setText(invoice.getInvoiceNo());
        lblIssuedAt.setText(invoice.getFormattedIssuedAt());
        lblTotalAmount.setText(invoice.getFormattedTotalAmount());
        lblInvoiceStatus.setText(invoice.getStatus());
        lblGeneratedBy.setText(invoice.getGeneratedBy());

        // Apply status styling
        lblInvoiceStatus.getStyleClass().removeAll("status-issued", "status-paid", "status-cancelled");
        if ("ISSUED".equals(invoice.getStatus())) {
            lblInvoiceStatus.getStyleClass().add("status-issued");
        } else if ("PAID".equals(invoice.getStatus())) {
            lblInvoiceStatus.getStyleClass().add("status-paid");
        } else if ("CANCELLED".equals(invoice.getStatus())) {
            lblInvoiceStatus.getStyleClass().add("status-cancelled");
        }
    }

    private void setupButtons() {
        btnEdit.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("billing_create.xml"));
                Parent root = loader.load();

                BillingCreateController controller = loader.getController();
                if (payment != null) {
                    controller.setPayment(payment);
                } else if (invoice != null) {
                    controller.setInvoice(invoice);
                }

                Stage stage = new Stage();
                stage.setTitle(payment != null ? "Edit Payment - " + payment.getPaymentId() :
                              "Edit Invoice - " + invoice.getInvoiceId());
                stage.setScene(new Scene(root, 700, 600));
                stage.initModality(Modality.NONE);
                stage.setResizable(false);
                stage.show();

                // Close current detail window
                Stage currentStage = (Stage) btnClose.getScene().getWindow();
                currentStage.close();

            } catch (IOException e) {
                DialogUtils.showError("Error", "Failed to open edit form: " + e.getMessage());
            }
        });

        btnClose.setOnAction(event -> {
            Stage stage = (Stage) btnClose.getScene().getWindow();
            stage.close();
        });
    }
}