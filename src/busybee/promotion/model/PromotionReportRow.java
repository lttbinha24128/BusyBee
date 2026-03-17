package busybee.promotion.model;

import javafx.beans.property.*;

public class PromotionReportRow {

    private final StringProperty name;
    private final IntegerProperty usage;
    private final StringProperty revenue;
    private final StringProperty status;

    public PromotionReportRow(String name, int usage, String revenue, String status) {

        this.name = new SimpleStringProperty(name);
        this.usage = new SimpleIntegerProperty(usage);
        this.revenue = new SimpleStringProperty(revenue);
        this.status = new SimpleStringProperty(status);

    }

    public StringProperty nameProperty() { return name; }
    public IntegerProperty usageProperty() { return usage; }
    public StringProperty revenueProperty() { return revenue; }
    public StringProperty statusProperty() { return status; }

}