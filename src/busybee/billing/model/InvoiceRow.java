package busybee.billing.model;

import busybee.util.DateUtils;
import busybee.util.NumberUtils;
import javafx.beans.property.*;

public class InvoiceRow {

    private final StringProperty invoiceId;
    private final StringProperty requestId;
    private final StringProperty invoiceNo;
    private final StringProperty issuedAt;
    private final DoubleProperty totalAmount;
    private final StringProperty status;
    private final StringProperty generatedBy;

    public InvoiceRow(String invoiceId, String requestId, String invoiceNo,
                      String issuedAt, double totalAmount, String status, String generatedBy) {

        this.invoiceId = new SimpleStringProperty(invoiceId);
        this.requestId = new SimpleStringProperty(requestId);
        this.invoiceNo = new SimpleStringProperty(invoiceNo);
        this.issuedAt = new SimpleStringProperty(issuedAt);
        this.totalAmount = new SimpleDoubleProperty(totalAmount);
        this.status = new SimpleStringProperty(status);
        this.generatedBy = new SimpleStringProperty(generatedBy);
    }

    public InvoiceRow() {
        this("", "", "", "", 0.0, "", "");
    }

    // PROPERTIES
    public StringProperty invoiceIdProperty() { return invoiceId; }
    public StringProperty requestIdProperty() { return requestId; }
    public StringProperty invoiceNoProperty() { return invoiceNo; }
    public StringProperty issuedAtProperty() { return issuedAt; }
    public DoubleProperty totalAmountProperty() { return totalAmount; }
    public StringProperty statusProperty() { return status; }
    public StringProperty generatedByProperty() { return generatedBy; }

    // GETTERS
    public String getInvoiceId() { return invoiceId.get(); }
    public String getRequestId() { return requestId.get(); }
    public String getInvoiceNo() { return invoiceNo.get(); }
    public String getIssuedAt() { return issuedAt.get(); }
    public double getTotalAmount() { return totalAmount.get(); }
    public String getStatus() { return status.get(); }
    public String getGeneratedBy() { return generatedBy.get(); }

    // SETTERS
    public void setInvoiceId(String value) { invoiceId.set(value); }
    public void setRequestId(String value) { requestId.set(value); }
    public void setInvoiceNo(String value) { invoiceNo.set(value); }
    public void setIssuedAt(String value) { issuedAt.set(value); }
    public void setTotalAmount(double value) { totalAmount.set(value); }
    public void setStatus(String value) { status.set(value); }
    public void setGeneratedBy(String value) { generatedBy.set(value); }

    // FORMATTED METHODS
    public String getFormattedTotalAmount() {
        return NumberUtils.formatCurrency(getTotalAmount());
    }

    public String getFormattedIssuedAt() {
        return DateUtils.isValidDateTime(getIssuedAt()) ?
               DateUtils.formatDateTimeForDisplay(DateUtils.parseDateTime(getIssuedAt())) : getIssuedAt();
    }

}