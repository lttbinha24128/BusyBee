package busybee.promotion.model;

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

    // =========================
    // PROPERTY
    // =========================

    public StringProperty nameProperty() { return name; }
    public StringProperty serviceProperty() { return service; }
    public StringProperty discountTypeProperty() { return discountType; }
    public StringProperty discountValueProperty() { return discountValue; }
    public StringProperty startDateProperty() { return startDate; }
    public StringProperty endDateProperty() { return endDate; }
    public StringProperty statusProperty() { return status; }

    // =========================
    // GETTER
    // =========================

    public String getName() { return name.get(); }
    public String getService() { return service.get(); }
    public String getDiscountType() { return discountType.get(); }
    public String getDiscountValue() { return discountValue.get(); }
    public String getStartDate() { return startDate.get(); }
    public String getEndDate() { return endDate.get(); }
    public String getStatus() { return status.get(); }

    // =========================
    // SETTER
    // =========================

    public void setName(String value) { name.set(value); }
    public void setService(String value) { service.set(value); }
    public void setDiscountType(String value) { discountType.set(value); }
    public void setDiscountValue(String value) { discountValue.set(value); }
    public void setStartDate(String value) { startDate.set(value); }
    public void setEndDate(String value) { endDate.set(value); }
    public void setStatus(String value) { status.set(value); }

}