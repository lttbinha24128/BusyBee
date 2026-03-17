package busybee.customer.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CustomerRequestRow {

    private final StringProperty service;
    private final StringProperty date;
    private final StringProperty status;

    public CustomerRequestRow(String service,
                              String date,
                              String status) {

        this.service = new SimpleStringProperty(service);
        this.date = new SimpleStringProperty(date);
        this.status = new SimpleStringProperty(status);

    }

    public StringProperty serviceProperty() { return service; }
    public StringProperty dateProperty() { return date; }
    public StringProperty statusProperty() { return status; }

}