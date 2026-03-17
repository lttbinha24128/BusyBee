package busybee.customer.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CustomerRow {

    private int id;

    private final StringProperty name;
    private final StringProperty phone;
    private final StringProperty email;
    private final StringProperty address;
    private final StringProperty status;
    private final StringProperty createdAt;

    // =========================
    // CONSTRUCTOR
    // =========================
    public CustomerRow(int id, String name, String phone,
                       String email, String address,
                       String status, String createdAt) {

        this.id = id;

        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
        this.status = new SimpleStringProperty(status);
        this.createdAt = new SimpleStringProperty(createdAt);
    }

    // =========================
    // GETTER
    // =========================
    public int getId() { return id; }

    public String getName() { return name.get(); }
    public String getPhone() { return phone.get(); }
    public String getEmail() { return email.get(); }
    public String getAddress() { return address.get(); }
    public String getStatus() { return status.get(); }
    public String getCreatedAt() { return createdAt.get(); }

    // =========================
    // SETTER
    // =========================
    public void setName(String value) { name.set(value); }
    public void setPhone(String value) { phone.set(value); }
    public void setEmail(String value) { email.set(value); }
    public void setAddress(String value) { address.set(value); }
    public void setStatus(String value) { status.set(value); }
    public void setCreatedAt(String value) { createdAt.set(value); }

    // =========================
    // PROPERTY (CHO TABLEVIEW)
    // =========================
    public StringProperty nameProperty() { return name; }
    public StringProperty phoneProperty() { return phone; }
    public StringProperty emailProperty() { return email; }
    public StringProperty addressProperty() { return address; }
    public StringProperty statusProperty() { return status; }
    public StringProperty createdAtProperty() { return createdAt; }
}