package busybee.promotion.controller;

import busybee.promotion.model.PromotionRow;
import busybee.util.DialogUtils;
import busybee.util.NumberUtils;
import busybee.util.ValidationUtils;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.function.Consumer;

public class PromotionCreateController {

    @FXML private TextField txtName;
    @FXML private ComboBox<String> cbService;
    @FXML private ComboBox<String> cbDiscountType;
    @FXML private TextField txtDiscountValue;

    @FXML private DatePicker dpStartDate;
    @FXML private DatePicker dpEndDate;

    @FXML private ComboBox<String> cbStatus;

    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    @FXML private Label lblPreview;

    private Consumer<PromotionRow> onPromotionCreated;

    private PromotionRow editingPromotion;

    private boolean hasUnsavedChanges = false;
    private boolean isSettingData = false;

    public boolean hasUnsavedChanges(){
        return hasUnsavedChanges;
    }

    public void setUnsavedChanges(boolean value){
        hasUnsavedChanges = value;
    }

    public void setOnPromotionCreated(Consumer<PromotionRow> callback){
        this.onPromotionCreated = callback;
    }

    @FXML
    private void initialize(){

        cbDiscountType.getItems().addAll("Percent","Fixed Amount");

        cbService.getItems().addAll(
                "Home Cleaning",
                "Office Cleaning",
                "Babysitting",
                "Birthday Party"
        );

        cbStatus.getItems().addAll("Active","Inactive");

        lblPreview.setText("0");

        txtDiscountValue.textProperty()
                .addListener((o,a,b)->updatePreview());

        cbDiscountType.valueProperty()
                .addListener((o,a,b)->updatePreview());

        trackChanges();

        btnSave.setOnAction(e->savePromotion());
        btnCancel.setOnAction(e->handleCancel());
    }

    private void trackChanges(){

        ChangeListener<Object> listener = (obs,oldVal,newVal)->{

            if(!isSettingData)
                hasUnsavedChanges = true;

        };

        txtName.textProperty().addListener(listener);
        cbService.valueProperty().addListener(listener);
        cbDiscountType.valueProperty().addListener(listener);
        txtDiscountValue.textProperty().addListener(listener);
        dpStartDate.valueProperty().addListener(listener);
        dpEndDate.valueProperty().addListener(listener);
        cbStatus.valueProperty().addListener(listener);
    }

    public void setPromotionForEdit(PromotionRow promotion){

        editingPromotion = promotion;

        isSettingData = true;

        txtName.setText(promotion.getName());
        cbService.setValue(promotion.getService());
        cbDiscountType.setValue(promotion.getDiscountType());
        txtDiscountValue.setText(promotion.getDiscountValue());

        dpStartDate.setValue(LocalDate.parse(promotion.getStartDate()));
        dpEndDate.setValue(LocalDate.parse(promotion.getEndDate()));

        cbStatus.setValue(promotion.getStatus());

        btnSave.setText("Update Promotion");

        isSettingData = false;

        updatePreview();
    }

    private void updatePreview(){

        if(!NumberUtils.isValidNumber(txtDiscountValue.getText())){

            lblPreview.setText("Invalid number");
            return;

        }

        double value =
                Double.parseDouble(txtDiscountValue.getText());

        String type = cbDiscountType.getValue();

        if(type == null){

            lblPreview.setText("Select discount type");
            return;

        }

        lblPreview.setText(

                type.equals("Percent")
                        ? value + "% discount"
                        : value + " VND discount"

        );
    }

    private void savePromotion(){

        if(!validateForm())
            return;

        LocalDate today = LocalDate.now();
        LocalDate end = dpEndDate.getValue();

        String status =
                end.isBefore(today)
                        ? "Expired"
                        : cbStatus.getValue();

        if(editingPromotion != null){

            editingPromotion.setName(txtName.getText());
            editingPromotion.setService(cbService.getValue());
            editingPromotion.setDiscountType(cbDiscountType.getValue());
            editingPromotion.setDiscountValue(txtDiscountValue.getText());
            editingPromotion.setStartDate(dpStartDate.getValue().toString());
            editingPromotion.setEndDate(dpEndDate.getValue().toString());
            editingPromotion.setStatus(status);

            DialogUtils.showInfo(
                    "Success",
                    "Promotion updated successfully!"
            );

        }
        else{

            PromotionRow promotion =
                    new PromotionRow(
                            txtName.getText(),
                            cbService.getValue(),
                            cbDiscountType.getValue(),
                            txtDiscountValue.getText(),
                            dpStartDate.getValue().toString(),
                            dpEndDate.getValue().toString(),
                            status
                    );

            if(onPromotionCreated != null)
                onPromotionCreated.accept(promotion);

            DialogUtils.showInfo(
                    "Success",
                    "Promotion created successfully!"
            );
        }

        hasUnsavedChanges = false;

        closeWindow();
    }

    private boolean validateForm(){

        if(!ValidationUtils.isNotEmpty(txtName.getText())){

            DialogUtils.showError(
                    "Validation Error",
                    ValidationUtils.createRequiredFieldError("Promotion name")
            );

            return false;
        }

        if(cbService.getValue() == null){

            DialogUtils.showError(
                    "Validation Error",
                    ValidationUtils.createRequiredFieldError("Service")
            );

            return false;
        }

        if(cbDiscountType.getValue() == null){

            DialogUtils.showError(
                    "Validation Error",
                    ValidationUtils.createRequiredFieldError("Discount type")
            );

            return false;
        }

        if(!NumberUtils.isValidNumber(txtDiscountValue.getText())){

            DialogUtils.showError(
                    "Validation Error",
                    "Discount value must be a valid number"
            );

            return false;
        }

        if(dpStartDate.getValue()==null || dpEndDate.getValue()==null){

            DialogUtils.showError(
                    "Validation Error",
                    "Start and end dates are required"
            );

            return false;
        }

        if(dpEndDate.getValue().isBefore(dpStartDate.getValue())){

            DialogUtils.showError(
                    "Validation Error",
                    "End date must be after start date"
            );

            return false;
        }

        return true;
    }

    private void handleCancel(){

        if(!hasUnsavedChanges){

            closeWindow();
            return;
        }

        int choice =
                DialogUtils.showConfirmationWithThreeOptions(
                        "Unsaved Changes",
                        "You have unsaved changes.",
                        "Save and Close",
                        "Don't Save",
                        "Cancel"
                );

        if(choice == 1){
            savePromotion();
        }
        else if(choice == 2){
            closeWindow();
        }
    }

    private void closeWindow(){

        ((Stage)btnCancel.getScene().getWindow()).close();

    }
}