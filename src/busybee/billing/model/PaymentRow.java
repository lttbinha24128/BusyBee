package busybee.billing.model;

import busybee.util.DateUtils;
import busybee.util.NumberUtils;
import javafx.beans.property.*;

public class PaymentRow {

    private final StringProperty paymentId;
    private final StringProperty requestId;
    private final DoubleProperty amount;
    private final StringProperty method;
    private final StringProperty paidAt;
    private final StringProperty status;
    private final StringProperty receivedBy;

    public PaymentRow(String paymentId, String requestId, double amount,
                      String method, String paidAt, String status, String receivedBy) {

        this.paymentId = new SimpleStringProperty(paymentId);
        this.requestId = new SimpleStringProperty(requestId);
        this.amount = new SimpleDoubleProperty(amount);
        this.method = new SimpleStringProperty(method);
        this.paidAt = new SimpleStringProperty(paidAt);
        this.status = new SimpleStringProperty(status);
        this.receivedBy = new SimpleStringProperty(receivedBy);
    }

    public PaymentRow() {
        this("", "", 0.0, "", "", "", "");
    }

    // PROPERTIES
    public StringProperty paymentIdProperty() { return paymentId; }
    public StringProperty requestIdProperty() { return requestId; }
    public DoubleProperty amountProperty() { return amount; }
    public StringProperty methodProperty() { return method; }
    public StringProperty paidAtProperty() { return paidAt; }
    public StringProperty statusProperty() { return status; }
    public StringProperty receivedByProperty() { return receivedBy; }

    // GETTERS
    public String getPaymentId() { return paymentId.get(); }
    public String getRequestId() { return requestId.get(); }
    public double getAmount() { return amount.get(); }
    public String getMethod() { return method.get(); }
    public String getPaidAt() { return paidAt.get(); }
    public String getStatus() { return status.get(); }
    public String getReceivedBy() { return receivedBy.get(); }

    // SETTERS
    public void setPaymentId(String value) { paymentId.set(value); }
    public void setRequestId(String value) { requestId.set(value); }
    public void setAmount(double value) { amount.set(value); }
    public void setMethod(String value) { method.set(value); }
    public void setPaidAt(String value) { paidAt.set(value); }
    public void setStatus(String value) { status.set(value); }
    public void setReceivedBy(String value) { receivedBy.set(value); }

    // FORMATTED METHODS
    public String getFormattedAmount() {
        return NumberUtils.formatCurrency(getAmount());
    }

    public String getFormattedPaidAt() {

        String value = getPaidAt();

        if (value == null || value.isEmpty()) {
            return "";
        }

        return DateUtils.isValidDateTime(value)
                ? DateUtils.formatDateTimeForDisplay(DateUtils.parseDateTime(value))
                : value;
    }

    @Override
    public String toString() {
        return "PaymentRow{" +
                "paymentId='" + getPaymentId() + '\'' +
                ", requestId='" + getRequestId() + '\'' +
                ", amount=" + getAmount() +
                ", method='" + getMethod() + '\'' +
                ", paidAt='" + getPaidAt() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", receivedBy='" + getReceivedBy() + '\'' +
                '}';
    }
}