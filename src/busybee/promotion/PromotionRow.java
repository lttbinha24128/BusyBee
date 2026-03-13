package busybee.promotion;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PromotionRow {

    private final StringProperty name;
    private final StringProperty service;
    private final StringProperty discountType;
    private final StringProperty discountValue;
    private final StringProperty startDate;
    private final StringProperty endDate;
    private final StringProperty status;

    public PromotionRow(String name, String service, String type,
                        String value, String start, String end, String status) {

        this.name = new SimpleStringProperty(name);
        this.service = new SimpleStringProperty(service);
        this.discountType = new SimpleStringProperty(type);
        this.discountValue = new SimpleStringProperty(value);
        this.startDate = new SimpleStringProperty(start);
        this.endDate = new SimpleStringProperty(end);
        this.status = new SimpleStringProperty(status);
    }

    public StringProperty nameProperty() { return name; }
    public StringProperty serviceProperty() { return service; }
    public StringProperty discountTypeProperty() { return discountType; }
    public StringProperty discountValueProperty() { return discountValue; }
    public StringProperty startDateProperty() { return startDate; }
    public StringProperty endDateProperty() { return endDate; }
    public StringProperty statusProperty() { return status; }

    public String getName() { return name.get(); }
    public String getService() { return service.get(); }
    public String getStatus() { return status.get(); }

    public void setStatus(String value) { status.set(value); }

}