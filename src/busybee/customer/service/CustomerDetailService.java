package busybee.customer.service;

import busybee.child.ChildRow;
import busybee.customer.model.CustomerBillingRow;
import busybee.customer.model.CustomerRequestRow;

import java.util.List;

public class CustomerDetailService {

    // =========================
    // TOGGLE STATUS
    // =========================
    public String toggleStatus(String currentStatus) {
        return currentStatus.equalsIgnoreCase("Active") ? "Inactive" : "Active";
    }

    // =========================
    // MOCK DATA
    // =========================
    public List<CustomerRequestRow> getRequestData() {
        return List.of(
                new CustomerRequestRow("Home Cleaning","2026-03-10","Completed"),
                new CustomerRequestRow("Babysitting","2026-03-15","Scheduled")
        );
    }

    public List<CustomerBillingRow> getBillingData() {
        return List.of(
                new CustomerBillingRow("INV-1001","2026-03-10","500000","Paid"),
                new CustomerBillingRow("INV-1002","2026-03-15","300000","Pending")
        );
    }

    public List<ChildRow> getChildrenData() {
        return List.of(
                new ChildRow("Tom",3,"Milk allergy"),
                new ChildRow("Anna",5,"")
        );
    }
}