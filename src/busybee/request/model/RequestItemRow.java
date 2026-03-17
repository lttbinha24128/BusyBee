package busybee.request.model;

import javafx.beans.property.*;

public class RequestItemRow {

    private final IntegerProperty itemId;
    private final IntegerProperty requestId;
    private final IntegerProperty serviceId;
    private final StringProperty serviceName;
    private final IntegerProperty quantity;

    public RequestItemRow(int itemId, int requestId, int serviceId, String serviceName, int quantity) {

        this.itemId = new SimpleIntegerProperty(itemId);
        this.requestId = new SimpleIntegerProperty(requestId);
        this.serviceId = new SimpleIntegerProperty(serviceId);
        this.serviceName = new SimpleStringProperty(serviceName);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    // PROPERTIES
    public IntegerProperty itemIdProperty() { return itemId; }
    public IntegerProperty requestIdProperty() { return requestId; }
    public IntegerProperty serviceIdProperty() { return serviceId; }
    public StringProperty serviceNameProperty() { return serviceName; }
    public IntegerProperty quantityProperty() { return quantity; }

    // GETTERS
    public int getItemId() { return itemId.get(); }
    public int getRequestId() { return requestId.get(); }
    public int getServiceId() { return serviceId.get(); }
    public String getServiceName() { return serviceName.get(); }
    public int getQuantity() { return quantity.get(); }

    // SETTERS
    public void setItemId(int value) { itemId.set(value); }
    public void setRequestId(int value) { requestId.set(value); }
    public void setServiceId(int value) { serviceId.set(value); }
    public void setServiceName(String value) { serviceName.set(value); }
    public void setQuantity(int value) { quantity.set(value); }

}