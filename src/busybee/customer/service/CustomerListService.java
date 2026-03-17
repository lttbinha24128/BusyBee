package busybee.customer.service;

import busybee.customer.model.CustomerRow;

import java.util.List;

public class CustomerListService {

    // =========================
    // TOGGLE STATUS
    // =========================
    public void toggleStatus(CustomerRow customer) {
        if ("Active".equals(customer.getStatus())) {
            customer.setStatus("Inactive");
        } else {
            customer.setStatus("Active");
        }
    }

    // =========================
    // FILTER
    // =========================
    public boolean matchFilter(CustomerRow customer, String keyword, String status) {

        String lowerKeyword = keyword.toLowerCase();

        boolean matchKeyword
                = customer.getName().toLowerCase().contains(lowerKeyword)
                || customer.getPhone().toLowerCase().contains(lowerKeyword);

        boolean matchStatus
                = "All".equals(status)
                || customer.getStatus().equals(status);

        return matchKeyword && matchStatus;
    }

    // =========================
    // MOCK DATA
    // =========================
    public List<CustomerRow> getDummyData() {
        return List.of(
                new CustomerRow(1,"John Nguyen", "0900000001", "john@mail.com", "HCM", "Active", "2026-01-10"),
                new CustomerRow(2,"Anna Tran", "0900000002", "anna@mail.com", "Hanoi", "Active", "2026-02-15"),
                new CustomerRow(3,"Minh Le", "0900000003", "minh@mail.com", "Danang", "Inactive", "2026-03-01")
        );
    }
}
