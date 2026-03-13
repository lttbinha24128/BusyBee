package busybee.promotion;

import busybee.layout.MainLayoutController;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class PromotionCreateController {

    @FXML private TextField txtName;
    @FXML private ComboBox<String> cbService;
    @FXML private ComboBox<String> cbDiscountType;
    @FXML private TextField txtDiscountValue;
    @FXML private TextField txtMaxDiscount;
    @FXML private TextField txtMinOrder;
    @FXML private TextField txtUsageLimit;
    @FXML private TextField txtMaxUsagePerCustomer;

    @FXML private DatePicker dpStartDate;
    @FXML private DatePicker dpEndDate;

    @FXML private ComboBox<String> cbStatus;

    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    @FXML private Label lblPreview;

    @FXML
    private void initialize() {

        // =========================
        // LOAD COMBOBOX DATA
        // =========================

        cbDiscountType.getItems().addAll(
                "Percent",
                "Fixed Amount"
        );

        cbStatus.getItems().addAll(
                "Active",
                "Inactive"
        );

        cbService.getItems().addAll(
                "Home Cleaning",
                "Office Cleaning",
                "Babysitting",
                "Birthday Party"
        );

        // =========================
        // PREVIEW LISTENER
        // =========================

        txtDiscountValue.textProperty().addListener((obs, oldVal, newVal) -> updatePreview());

        cbDiscountType.valueProperty().addListener((obs, oldVal, newVal) -> updatePreview());

        // =========================
        // BUTTON ACTION
        // =========================

        btnSave.setOnAction(e -> savePromotion());

        btnCancel.setOnAction(e ->
                MainLayoutController
                        .getInstance()
                        .loadContent("/busybee/promotion/promotion_list.xml")
        );

        lblPreview.setText("0");

    }

    // =========================
    // PREVIEW DISCOUNT
    // =========================

    private void updatePreview() {

        if (txtDiscountValue.getText().isEmpty()) {
            lblPreview.setText("0");
            return;
        }

        try {

            double value = Double.parseDouble(txtDiscountValue.getText());
            String type = cbDiscountType.getValue();

            if (type == null) {
                lblPreview.setText("Select discount type");
                return;
            }

            if (type.equals("Percent")) {
                lblPreview.setText(value + "% discount");
            } else {
                lblPreview.setText(value + " VND discount");
            }

        } catch (Exception e) {
            lblPreview.setText("Invalid number");
        }

    }

    // =========================
    // SAVE PROMOTION
    // =========================

    private void savePromotion() {

        if (!validateForm()) {
            return;
        }

        LocalDate today = LocalDate.now();
        LocalDate end = dpEndDate.getValue();

        String status;

        if (end.isBefore(today)) {
            status = "Expired";
        } else {
            status = cbStatus.getValue();
        }

        // DEMO OUTPUT
        System.out.println("===== PROMOTION CREATED =====");
        System.out.println("Name: " + txtName.getText());
        System.out.println("Service: " + cbService.getValue());
        System.out.println("Type: " + cbDiscountType.getValue());
        System.out.println("Value: " + txtDiscountValue.getText());
        System.out.println("Max Discount: " + txtMaxDiscount.getText());
        System.out.println("Min Order: " + txtMinOrder.getText());
        System.out.println("Usage Limit: " + txtUsageLimit.getText());
        System.out.println("Max per Customer: " + txtMaxUsagePerCustomer.getText());
        System.out.println("Start: " + dpStartDate.getValue());
        System.out.println("End: " + dpEndDate.getValue());
        System.out.println("Status: " + status);

        alert("Promotion created successfully!");

        // quay về promotion list
        MainLayoutController
                .getInstance()
                .loadContent("/busybee/promotion/promotion_list.xml");

    }

    // =========================
    // VALIDATE FORM
    // =========================

    private boolean validateForm() {

        if (txtName.getText().isEmpty()) {
            alert("Promotion name required");
            return false;
        }

        if (cbService.getValue() == null) {
            alert("Select service");
            return false;
        }

        if (cbDiscountType.getValue() == null) {
            alert("Select discount type");
            return false;
        }

        if (!isNumber(txtDiscountValue.getText())) {
            alert("Discount value must be number");
            return false;
        }

        if (dpStartDate.getValue() == null || dpEndDate.getValue() == null) {
            alert("Select start and end date");
            return false;
        }

        if (dpEndDate.getValue().isBefore(dpStartDate.getValue())) {
            alert("End date must be after start date");
            return false;
        }

        return true;

    }

    // =========================
    // HELPER
    // =========================

    private boolean isNumber(String text) {

        try {
            Double.parseDouble(text);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private void alert(String msg) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("BusyBee");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();

    }

}