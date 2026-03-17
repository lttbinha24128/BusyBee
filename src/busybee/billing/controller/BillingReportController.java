package busybee.billing.controller;

import busybee.billing.model.PaymentRow;
import busybee.billing.service.BillingReportService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BillingReportController {

    @FXML private ComboBox<String> cbTimeRange;

    @FXML private Label lblTotalRevenue;
    @FXML private Label lblTotalPayments;
    @FXML private Label lblPaid;
    @FXML private Label lblPending;

    @FXML private BarChart<String, Number> billingChart;

    @FXML private TableView<PaymentRow> tablePayments;
    @FXML private TableColumn<PaymentRow, String> colRequest;
    @FXML private TableColumn<PaymentRow, String> colAmount;
    @FXML private TableColumn<PaymentRow, String> colMethod;
    @FXML private TableColumn<PaymentRow, String> colDate;
    @FXML private TableColumn<PaymentRow, String> colStatus;

    private final BillingReportService service = new BillingReportService();

    private List<PaymentRow> masterData;

    @FXML
    public void initialize() {

        masterData = service.getMockPayments();

        setupTable();
        setupFilter();

        applyFilter();
    }

    private void setupTable() {

        colRequest.setCellValueFactory(c -> c.getValue().requestIdProperty());

        colAmount.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getFormattedAmount()
                ));

        colMethod.setCellValueFactory(c -> c.getValue().methodProperty());

        colDate.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getFormattedPaidAt()
                ));

        colStatus.setCellValueFactory(c -> c.getValue().statusProperty());
    }

    private void setupFilter() {

        cbTimeRange.setItems(FXCollections.observableArrayList(
                "Today", "This Week", "This Month"
        ));

        cbTimeRange.setValue("This Month");

        cbTimeRange.setOnAction(e -> applyFilter());
    }

    private void applyFilter() {

        String selected = cbTimeRange.getValue();
        LocalDateTime now = LocalDateTime.now();

        List<PaymentRow> filtered = masterData.stream().filter(p -> {

            LocalDateTime date = LocalDateTime.parse(p.getPaidAt());

            if (selected.equals("Today")) {
                return date.toLocalDate().equals(now.toLocalDate());
            }

            if (selected.equals("This Week")) {
                return date.isAfter(now.minusDays(7));
            }

            if (selected.equals("This Month")) {
                return date.getMonth() == now.getMonth();
            }

            return true;

        }).collect(Collectors.toList());

        updateUI(filtered);
    }

    private void updateUI(List<PaymentRow> data) {

        tablePayments.setItems(FXCollections.observableArrayList(data));

        int totalPayments = data.size();
        double totalRevenue = data.stream().mapToDouble(PaymentRow::getAmount).sum();

        int paid = (int) data.stream().filter(p -> p.getStatus().equals("Paid")).count();
        int pending = (int) data.stream().filter(p -> p.getStatus().equals("Pending")).count();

        lblTotalPayments.setText(String.valueOf(totalPayments));
        lblTotalRevenue.setText(String.format("%.0f", totalRevenue));
        lblPaid.setText(String.valueOf(paid));
        lblPending.setText(String.valueOf(pending));

        billingChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        series.getData().add(new XYChart.Data<>("Revenue", totalRevenue));
        series.getData().add(new XYChart.Data<>("Paid", paid));
        series.getData().add(new XYChart.Data<>("Pending", pending));

        billingChart.getData().add(series);
    }
}