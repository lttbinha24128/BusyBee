package busybee.request.controller;

import busybee.request.model.RequestRow;
import busybee.request.service.RequestReportService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class RequestReportController {

    @FXML private ComboBox<String> cbTimeRange;

    @FXML private Label lblTotal;
    @FXML private Label lblCompleted;
    @FXML private Label lblPending;
    @FXML private Label lblCancelled;

    @FXML private BarChart<String, Number> requestChart;

    @FXML private TableView<RequestRow> tableRequests;
    @FXML private TableColumn<RequestRow, String> colCustomer;
    @FXML private TableColumn<RequestRow, String> colEmployee;
    @FXML private TableColumn<RequestRow, String> colDate;
    @FXML private TableColumn<RequestRow, String> colStatus;

    private final RequestReportService service = new RequestReportService();

    private List<RequestRow> masterData;

    @FXML
    public void initialize() {

        // mock data
        masterData = service.getMockRequests();

        setupTable();

        setupFilter();

        applyFilter(); // load lần đầu
    }

    private void setupTable() {
        colCustomer.setCellValueFactory(c -> c.getValue().customerNameProperty());
        colEmployee.setCellValueFactory(c -> c.getValue().employeeNameProperty());
        colDate.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getRequestDateFormatted()
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

        List<RequestRow> filtered = masterData.stream().filter(r -> {

            LocalDateTime date = r.getRequestDate();

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

    private void updateUI(List<RequestRow> data) {

        // TABLE
        tableRequests.setItems(FXCollections.observableArrayList(data));

        // SUMMARY
        int total = data.size();
        int completed = (int) data.stream().filter(r -> r.getStatus().equals("Completed")).count();
        int pending = (int) data.stream().filter(r -> r.getStatus().equals("Pending")).count();
        int cancelled = (int) data.stream().filter(r -> r.getStatus().equals("Cancelled")).count();

        lblTotal.setText(String.valueOf(total));
        lblCompleted.setText(String.valueOf(completed));
        lblPending.setText(String.valueOf(pending));
        lblCancelled.setText(String.valueOf(cancelled));

        // CHART
        requestChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        series.getData().add(new XYChart.Data<>("Total", total));
        series.getData().add(new XYChart.Data<>("Completed", completed));
        series.getData().add(new XYChart.Data<>("Pending", pending));
        series.getData().add(new XYChart.Data<>("Cancelled", cancelled));

        requestChart.getData().add(series);
    }
}