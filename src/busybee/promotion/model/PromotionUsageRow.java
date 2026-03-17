package busybee.promotion.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PromotionUsageRow {

    private final StringProperty date;
    private final StringProperty customer;
    private final StringProperty discount;

    public PromotionUsageRow(String date, String customer, String discount) {

        this.date = new SimpleStringProperty(date);
        this.customer = new SimpleStringProperty(customer);
        this.discount = new SimpleStringProperty(discount);
    }

    public StringProperty dateProperty() { return date; }
    public StringProperty customerProperty() { return customer; }
    public StringProperty discountProperty() { return discount; }

    public String getDate() { return date.get(); }
    public String getCustomer() { return customer.get(); }
    public String getDiscount() { return discount.get(); }

    public void setDate(String value) { date.set(value); }
    public void setCustomer(String value) { customer.set(value); }
    public void setDiscount(String value) { discount.set(value); }
}