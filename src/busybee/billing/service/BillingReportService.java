package busybee.billing.service;

import busybee.billing.model.PaymentRow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BillingReportService {

    public List<PaymentRow> getMockPayments() {

        List<PaymentRow> list = new ArrayList<>();

        list.add(new PaymentRow("1", "R1", 100, "Cash",
                LocalDateTime.now().minusDays(1).toString(),
                "Paid", "Admin"));

        list.add(new PaymentRow("2", "R2", 200, "Card",
                LocalDateTime.now().minusDays(3).toString(),
                "Pending", "Admin"));

        list.add(new PaymentRow("3", "R3", 150, "Cash",
                LocalDateTime.now().minusDays(10).toString(),
                "Paid", "Admin"));

        list.add(new PaymentRow("4", "R4", 300, "Transfer",
                LocalDateTime.now().minusDays(20).toString(),
                "Paid", "Admin"));

        return list;
    }
}