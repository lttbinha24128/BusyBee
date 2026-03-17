package busybee.promotion.controller;

import busybee.promotion.model.PromotionRow;
import busybee.promotion.model.PromotionUsageRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PromotionDetailController {

    @FXML private Label lblNameHeader;
    @FXML private Label lblName;
    @FXML private Label lblService;
    @FXML private Label lblDiscountType;
    @FXML private Label lblDiscountValue;
    @FXML private Label lblStartDate;
    @FXML private Label lblEndDate;
    @FXML private Label lblStatus;

    @FXML private Button btnActions;

    @FXML private TableView<PromotionUsageRow> usageTable;
    @FXML private TableColumn<PromotionUsageRow,String> colUsageDate;
    @FXML private TableColumn<PromotionUsageRow,String> colCustomer;
    @FXML private TableColumn<PromotionUsageRow,String> colDiscountApplied;

    private PromotionRow currentPromotion;

    private boolean hasUnsavedChanges = false;

    public boolean hasUnsavedChanges(){
        return hasUnsavedChanges;
    }

    public void setUnsavedChanges(boolean value){
        hasUnsavedChanges = value;
    }

    private final ObservableList<PromotionUsageRow> usageData =
            FXCollections.observableArrayList();

    @FXML
    private void initialize(){

        colUsageDate.setCellValueFactory(c->c.getValue().dateProperty());
        colCustomer.setCellValueFactory(c->c.getValue().customerProperty());
        colDiscountApplied.setCellValueFactory(c->c.getValue().discountProperty());

        usageTable.setItems(usageData);

        loadDummyData();
    }

    public void setPromotion(PromotionRow promotion){

        currentPromotion = promotion;

        lblNameHeader.setText(promotion.getName());

        lblName.setText(promotion.getName());
        lblService.setText(promotion.getService());
        lblDiscountType.setText(promotion.getDiscountType());
        lblDiscountValue.setText(promotion.getDiscountValue());
        lblStartDate.setText(promotion.getStartDate());
        lblEndDate.setText(promotion.getEndDate());
        lblStatus.setText(promotion.getStatus());
    }

    private void loadDummyData(){

        usageData.addAll(
                new PromotionUsageRow("2026-03-10","John Nguyen","20%"),
                new PromotionUsageRow("2026-03-12","Anna Tran","50000 VND"),
                new PromotionUsageRow("2026-03-15","Minh Le","15%")
        );
    }

    private void closeWindow(){

        Stage stage =
                (Stage) lblName.getScene().getWindow();

        stage.close();
    }
}