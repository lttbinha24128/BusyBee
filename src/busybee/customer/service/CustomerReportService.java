package busybee.customer.service;

import busybee.customer.model.CustomerRow;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerReportService {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // =========================
    // PARSE DATE
    // =========================
    private LocalDateTime parse(String date) {
        try {
            return LocalDateTime.parse(date, FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    // =========================
    // FILTER BY RANGE
    // =========================
    public List<CustomerRow> filterByRange(List<CustomerRow> data, String range) {

        LocalDate today = LocalDate.now();

        return data.stream().filter(c -> {

            LocalDateTime created = parse(c.getCreatedAt());
            if (created == null) return false;

            LocalDate date = created.toLocalDate();

            switch (range) {

                case "Today":
                    return date.equals(today);

                case "This Week":
                    return date.isAfter(today.minusDays(7));

                case "This Month":
                    return date.getMonth() == today.getMonth()
                            && date.getYear() == today.getYear();

                case "Last 3 Months":
                    return date.isAfter(today.minusMonths(3));

                case "This Year":
                    return date.getYear() == today.getYear();

                default:
                    return true; // All
            }

        }).collect(Collectors.toList());
    }

    // =========================
    // STATISTICS
    // =========================
    public int countTotal(List<CustomerRow> data) {
        return data.size();
    }

    public int countActive(List<CustomerRow> data) {
        return (int) data.stream()
                .filter(c -> "Active".equalsIgnoreCase(c.getStatus()))
                .count();
    }

    public int countInactive(List<CustomerRow> data) {
        return countTotal(data) - countActive(data);
    }

    // =========================
    // GROUP BY MONTH
    // =========================
    public Map<String, Integer> groupByMonth(List<CustomerRow> data) {

        Map<String, Integer> result = new TreeMap<>();

        for (CustomerRow c : data) {

            String createdAt = c.getCreatedAt();
            if (createdAt == null || createdAt.length() < 7) continue;

            String month = createdAt.substring(0, 7);

            result.put(month,
                    result.getOrDefault(month, 0) + 1);
        }

        return result;
    }
}