package busybee.promotion;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

public class PromotionReportController {

    // =====================
    // FILTER
    // =====================

    @FXML
    private ComboBox<String> cbTimeRange;

    // =====================
    // SUMMARY
    // =====================

    @FXML
    private Label lblUsage;

    @FXML
    private Label lblRevenue;

    @FXML
    private Label lblDiscount;

    // =====================
    // CHART
    // =====================

    @FXML
    private BarChart<String, Number> usageChart;

    // =====================
    // TABLE
    // =====================

    @FXML
    private TableView<PromotionReportRow> topPromoTable;

    @FXML
    private TableColumn<PromotionReportRow, String> colPromo;

    @FXML
    private TableColumn<PromotionReportRow, Integer> colUsage;

    @FXML
    private TableColumn<PromotionReportRow, String> colRevenue;

    @FXML
    private TableColumn<PromotionReportRow, String> colStatus;

    // =====================
    // INITIALIZE
    // =====================

    @FXML
    private void initialize() {

        setupFilter();
        setupTable();
        setupChart();

        loadReportData();

    }

    // =====================
    // FILTER
    // =====================

    private void setupFilter() {

        cbTimeRange.setItems(FXCollections.observableArrayList(
                "Last 7 Days",
                "Last 30 Days",
                "This Month"
        ));

        cbTimeRange.setValue("Last 30 Days");

        cbTimeRange.setOnAction(e -> loadReportData());

    }

    // =====================
    // CHART SETUP
    // =====================

    private void setupChart() {

        usageChart.setLegendVisible(false);
        usageChart.setAnimated(false);

    }

    // =====================
    // LOAD ALL DATA
    // =====================

    private void loadReportData() {

        loadSummary();
        loadChart();
        loadTable();

    }

    // =====================
    // SUMMARY
    // =====================

    private void loadSummary() {

        // Demo data (sau này thay bằng database)

        lblUsage.setText("14,285");
        lblRevenue.setText("$128,402");
        lblDiscount.setText("$21,094");

    }

    // =====================
    // BAR CHART
    // =====================

    private void loadChart() {

        usageChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        series.getData().add(new XYChart.Data<>("SUMMER24", 3420));
        series.getData().add(new XYChart.Data<>("BEEFREE", 2100));
        series.getData().add(new XYChart.Data<>("WELCOME10", 1850));
        series.getData().add(new XYChart.Data<>("FLASH20", 2980));

        usageChart.getData().add(series);

    }

    // =====================
    // TABLE SETUP
    // =====================

    private void setupTable() {

        colPromo.setCellValueFactory(c -> c.getValue().nameProperty());

        colUsage.setCellValueFactory(
                c -> c.getValue().usageProperty().asObject()
        );

        colRevenue.setCellValueFactory(
                c -> c.getValue().revenueProperty()
        );

        colStatus.setCellValueFactory(
                c -> c.getValue().statusProperty()
        );

        // STATUS BADGE STYLE

        colStatus.setCellFactory(column -> new TableCell<>() {

            @Override
            protected void updateItem(String status, boolean empty) {

                super.updateItem(status, empty);

                if (empty || status == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                Label label = new Label(status);

                if (status.equalsIgnoreCase("Active")) {
                    label.getStyleClass().add("status-active");
                } else if (status.equalsIgnoreCase("Expired")) {
                    label.getStyleClass().add("status-expired");
                } else {
                    label.getStyleClass().add("status-inactive");
                }

                setGraphic(label);

            }

        });

    }

    // =====================
    // TABLE DATA
    // =====================

    private void loadTable() {

        topPromoTable.setItems(

                FXCollections.observableArrayList(

                        new PromotionReportRow(
                                "SUMMER24",
                                3420,
                                "$42,500",
                                "Active"
                        ),

                        new PromotionReportRow(
                                "FLASH20",
                                2980,
                                "$31,200",
                                "Active"
                        ),

                        new PromotionReportRow(
                                "WELCOME10",
                                1850,
                                "$18,450",
                                "Active"
                        ),

                        new PromotionReportRow(
                                "BEEFREE",
                                2100,
                                "$15,200",
                                "Expired"
                        )

                )

        );

    }

}