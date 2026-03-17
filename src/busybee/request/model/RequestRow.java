package busybee.request.model;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequestRow {

    private final IntegerProperty requestId;
    private final IntegerProperty customerId;
    private final StringProperty customerName;
    private final IntegerProperty employeeId;
    private final StringProperty employeeName;
    private final ObjectProperty<LocalDateTime> requestDate;
    private final ObjectProperty<LocalDateTime> scheduledStart;
    private final StringProperty status;
    private final StringProperty notes;

    public RequestRow(int requestId, int customerId, String customerName,
                      int employeeId, String employeeName,
                      LocalDateTime requestDate, LocalDateTime scheduledStart,
                      String status, String notes) {

        this.requestId = new SimpleIntegerProperty(requestId);
        this.customerId = new SimpleIntegerProperty(customerId);
        this.customerName = new SimpleStringProperty(customerName);
        this.employeeId = new SimpleIntegerProperty(employeeId);
        this.employeeName = new SimpleStringProperty(employeeName);
        this.requestDate = new SimpleObjectProperty<>(requestDate);
        this.scheduledStart = new SimpleObjectProperty<>(scheduledStart);
        this.status = new SimpleStringProperty(status);
        this.notes = new SimpleStringProperty(notes);
    }

    // PROPERTIES
    public IntegerProperty requestIdProperty() { return requestId; }
    public IntegerProperty customerIdProperty() { return customerId; }
    public StringProperty customerNameProperty() { return customerName; }
    public IntegerProperty employeeIdProperty() { return employeeId; }
    public StringProperty employeeNameProperty() { return employeeName; }
    public ObjectProperty<LocalDateTime> requestDateProperty() { return requestDate; }
    public ObjectProperty<LocalDateTime> scheduledStartProperty() { return scheduledStart; }
    public StringProperty statusProperty() { return status; }
    public StringProperty notesProperty() { return notes; }

    // GETTERS
    public int getRequestId() { return requestId.get(); }
    public int getCustomerId() { return customerId.get(); }
    public String getCustomerName() { return customerName.get(); }
    public int getEmployeeId() { return employeeId.get(); }
    public String getEmployeeName() { return employeeName.get(); }
    public LocalDateTime getRequestDate() { return requestDate.get(); }
    public LocalDateTime getScheduledStart() { return scheduledStart.get(); }
    public String getStatus() { return status.get(); }
    public String getNotes() { return notes.get(); }

    // SETTERS
    public void setRequestId(int value) { requestId.set(value); }
    public void setCustomerId(int value) { customerId.set(value); }
    public void setCustomerName(String value) { customerName.set(value); }
    public void setEmployeeId(int value) { employeeId.set(value); }
    public void setEmployeeName(String value) { employeeName.set(value); }
    public void setRequestDate(LocalDateTime value) { requestDate.set(value); }
    public void setScheduledStart(LocalDateTime value) { scheduledStart.set(value); }
    public void setStatus(String value) { status.set(value); }
    public void setNotes(String value) { notes.set(value); }

    // FORMATTED DATES FOR DISPLAY
    public String getRequestDateFormatted() {
        return requestDate.get() != null ? requestDate.get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "";
    }

    public String getScheduledStartFormatted() {
        return scheduledStart.get() != null ? scheduledStart.get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "";
    }

}