package busybee.request.service;

import busybee.request.model.RequestRow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RequestReportService {

    public List<RequestRow> getMockRequests() {

        List<RequestRow> list = new ArrayList<>();

        list.add(new RequestRow(1, 101, "Alice", 201, "John",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now(), "Completed", ""));

        list.add(new RequestRow(2, 102, "Bob", 202, "Mike",
                LocalDateTime.now().minusDays(3),
                LocalDateTime.now(), "Pending", ""));

        list.add(new RequestRow(3, 103, "Charlie", 203, "Anna",
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now(), "Cancelled", ""));

        list.add(new RequestRow(4, 104, "David", 204, "Emma",
                LocalDateTime.now().minusDays(20),
                LocalDateTime.now(), "Completed", ""));

        return list;
    }

    public int countByStatus(List<RequestRow> list, String status) {
        return (int) list.stream()
                .filter(r -> r.getStatus().equalsIgnoreCase(status))
                .count();
    }
}
