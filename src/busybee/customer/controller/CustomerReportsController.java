package busybee.customer.controller;

import busybee.customer.model.CustomerRow;
import busybee.customer.service.CustomerListService;
import busybee.customer.service.CustomerReportService;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;

public class CustomerReportsController {

    private final CustomerReportService reportService = new CustomerReportService();
    private final CustomerListService listService = new CustomerListService();

    @FXML private ComboBox<String> cbTimeRange;

    @FXML private Label lblTotalCustomers;
    @FXML private Label lblActiveCustomers;
    @FXML private Label lblInactiveCustomers;
    @FXML private Label lblNewCustomers;

    @FXML private BarChart<String, Number> customerChart;

    @FXML private TableView<CustomerRow> customerTable;
    @FXML private TableColumn<CustomerRow, String> colName;
    @FXML private TableColumn<CustomerRow, String> colPhone;
    @FXML private TableColumn<CustomerRow, String> colEmail;
    @FXML private TableColumn<CustomerRow, String> colStatus;

    private final ObservableList<CustomerRow> masterData = FXCollections.observableArrayList();
    private final ObservableList<CustomerRow> filteredData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        setupTimeRange();
        setupTable();

        loadData();

        applyFilter(); // load lần đầu
    }

    // =========================
    // TIME RANGE
    // =========================
    private void setupTimeRange() {

        cbTimeRange.getItems().addAll(
                "All",
                "Today",
                "This Week",
                "This Month",
                "Last 3 Months",
                "This Year"
        );

        cbTimeRange.setValue("All");

        cbTimeRange.setOnAction(e -> applyFilter());
    }

    // =========================
    // TABLE
    // =========================
    private void setupTable() {

        colName.setCellValueFactory(c -> c.getValue().nameProperty());
        colPhone.setCellValueFactory(c -> c.getValue().phoneProperty());
        colEmail.setCellValueFactory(c -> c.getValue().emailProperty());
        colStatus.setCellValueFactory(c -> c.getValue().statusProperty());

        customerTable.setItems(filteredData);
    }

    // =========================
    // LOAD DATA
    // =========================
    private void loadData() {
        masterData.setAll(listService.getDummyData());
    }

    // =========================
    // APPLY FILTER
    // =========================
    private void applyFilter() {

        String range = cbTimeRange.getValue();

        List<CustomerRow> result =
                reportService.filterByRange(masterData, range);

        filteredData.setAll(result);

        loadStatistics(result);
        loadChart(result);
    }

    // =========================
    // STATISTICS
    // =========================
    private void loadStatistics(List<CustomerRow> data) {

        int total = reportService.countTotal(data);
        int active = reportService.countActive(data);
        int inactive = reportService.countInactive(data);

        lblTotalCustomers.setText(String.valueOf(total));
        lblActiveCustomers.setText(String.valueOf(active));
        lblInactiveCustomers.setText(String.valueOf(inactive));

        lblNewCustomers.setText("+" + total);
    }

    // =========================
    // CHART
    // =========================
    private void loadChart(List<CustomerRow> data) {

        Map<String, Integer> map =
                reportService.groupByMonth(data);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (String month : map.keySet()) {
            series.getData().add(
                    new XYChart.Data<>(month, map.get(month))
            );
        }

        customerChart.getData().clear();
        customerChart.getData().add(series);
    }
}