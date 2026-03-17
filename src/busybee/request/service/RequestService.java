package busybee.request.service;

import busybee.customer.model.CustomerRow;
import busybee.request.model.RequestItemRow;
import busybee.request.model.RequestRow;

import java.time.LocalDateTime;
import java.util.List;

public class RequestService {

    // =========================
    // CREATE REQUEST
    // =========================
    public RequestRow createRequest(
            CustomerRow customer,
            String employee,
            LocalDateTime requestDate,
            LocalDateTime scheduledStart,
            String status,
            String notes,
            List<RequestItemRow> items
    ) {

        // TODO: sau này thêm tính tiền tại đây

        return new RequestRow(
                0,
                customer.getId(),
                customer.getName(),
                1, // employeeId (tạm)
                employee,
                requestDate,
                scheduledStart,
                status,
                notes
        );
    }

    // =========================
    // CREATE ITEM
    // =========================
    public RequestItemRow createItem(String serviceName, int quantity) {
        return new RequestItemRow(0, 0, 0, serviceName, quantity);
    }

    // =========================
    // VALIDATE
    // =========================
    public boolean isValid(CustomerRow customer, List<RequestItemRow> items) {
        return customer != null && !items.isEmpty();
    }
}