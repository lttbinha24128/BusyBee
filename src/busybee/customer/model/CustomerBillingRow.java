package busybee.customer.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CustomerBillingRow {

    private final StringProperty invoice;
    private final StringProperty date;
    private final StringProperty amount;
    private final StringProperty status;

    public CustomerBillingRow(String invoice,
                              String date,
                              String amount,
                              String status) {

        this.invoice = new SimpleStringProperty(invoice);
        this.date = new SimpleStringProperty(date);
        this.amount = new SimpleStringProperty(amount);
        this.status = new SimpleStringProperty(status);
    }

    public StringProperty invoiceProperty() { return invoice; }
    public StringProperty dateProperty() { return date; }
    public StringProperty amountProperty() { return amount; }
    public StringProperty statusProperty() { return status; }

}