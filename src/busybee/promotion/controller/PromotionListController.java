package busybee.promotion.controller;

import busybee.promotion.model.PromotionRow;
import busybee.promotion.controller.PromotionDetailController;
import busybee.promotion.controller.PromotionCreateController;
import busybee.util.DialogUtils;
import busybee.util.StageManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class PromotionListController {

    @FXML private TableView<PromotionRow> promotionTable;

    @FXML private TableColumn<PromotionRow,String> colName;
    @FXML private TableColumn<PromotionRow,String> colService;
    @FXML private TableColumn<PromotionRow,String> colDiscountType;
    @FXML private TableColumn<PromotionRow,String> colDiscountValue;
    @FXML private TableColumn<PromotionRow,String> colStartDate;
    @FXML private TableColumn<PromotionRow,String> colEndDate;
    @FXML private TableColumn<PromotionRow,String> colStatus;
    @FXML private TableColumn<PromotionRow,Void> colAction;

    @FXML private TextField txtSearch;
    @FXML private ComboBox<String> cbStatus;
    @FXML private ComboBox<String> cbService;
    @FXML private Button btnCreate;

    private final ObservableList<PromotionRow> masterData = FXCollections.observableArrayList();
    private final Map<PromotionRow,Stage> openStages = new HashMap<>();

    @FXML
    private void initialize() {

        colName.setCellValueFactory(c -> c.getValue().nameProperty());
        colService.setCellValueFactory(c -> c.getValue().serviceProperty());
        colDiscountType.setCellValueFactory(c -> c.getValue().discountTypeProperty());
        colDiscountValue.setCellValueFactory(c -> c.getValue().discountValueProperty());
        colStartDate.setCellValueFactory(c -> c.getValue().startDateProperty());
        colEndDate.setCellValueFactory(c -> c.getValue().endDateProperty());
        colStatus.setCellValueFactory(c -> c.getValue().statusProperty());

        setupStatusColor();
        setupActionColumn();
        setupFilters();
        setupRowDoubleClick();

        loadMockData();

        FilteredList<PromotionRow> filtered = new FilteredList<>(masterData, p -> true);

        Runnable applyFilter = () -> {

            String keyword = txtSearch.getText();
            String status = cbStatus.getValue();
            String service = cbService.getValue();

            filtered.setPredicate(promo -> {

                boolean matchSearch = true;
                boolean matchStatus = true;
                boolean matchService = true;

                if(keyword != null && !keyword.isEmpty()){

                    String lower = keyword.toLowerCase();

                    matchSearch =
                            promo.getName().toLowerCase().contains(lower) ||
                            promo.getService().toLowerCase().contains(lower);
                }

                if(status != null && !status.equals("All")){
                    matchStatus = promo.getStatus().equals(status);
                }

                if(service != null && !service.equals("All")){
                    matchService = promo.getService().equals(service);
                }

                return matchSearch && matchStatus && matchService;

            });

        };

        txtSearch.textProperty().addListener((o,a,b)->applyFilter.run());
        cbStatus.valueProperty().addListener((o,a,b)->applyFilter.run());
        cbService.valueProperty().addListener((o,a,b)->applyFilter.run());

        promotionTable.setItems(filtered);

        btnCreate.setOnAction(e -> openCreateDialog());
    }

    private void openCreateDialog(){

        try{

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/busybee/promotion/promotion_create.xml"));

            Parent root = loader.load();

            PromotionCreateController controller = loader.getController();

            controller.setOnPromotionCreated(masterData::add);

            Stage stage = createModalStage(root,"Create Promotion");

            stage.showAndWait();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void openEditDialog(PromotionRow promotion){

        try{

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/busybee/promotion/promotion_create.xml"));

            Parent root = loader.load();

            PromotionCreateController controller = loader.getController();
            controller.setPromotionForEdit(promotion);

            Stage stage = createModalStage(root,"Edit Promotion");

            stage.showAndWait();

            promotionTable.refresh();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private Stage createModalStage(Parent root,String title){

        Stage stage = new Stage();

        Scene scene = new Scene(root);

        scene.getStylesheets().addAll(
                promotionTable.getScene().getStylesheets()
        );

        stage.setScene(scene);
        stage.setTitle(title);

        stage.initOwner(promotionTable.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setResizable(false);
        stage.centerOnScreen();

        return stage;
    }

    private void setupStatusColor(){

        colStatus.setCellFactory(column -> new TableCell<>() {

            @Override
            protected void updateItem(String status, boolean empty){

                super.updateItem(status, empty);

                if(empty || status == null){
                    setText(null);
                    setStyle("");
                    return;
                }

                setText(status);

                switch(status){

                    case "Active" ->
                            setStyle("-fx-background-color:#d4edda;-fx-text-fill:#155724;");

                    case "Expired" ->
                            setStyle("-fx-background-color:#f8d7da;-fx-text-fill:#721c24;");

                    case "Inactive" ->
                            setStyle("-fx-background-color:#e2e3e5;-fx-text-fill:#383d41;");
                }
            }
        });
    }

    private void setupActionColumn(){

        colAction.setCellFactory(col -> new TableCell<>() {

            private final Button btnEdit = new Button("Edit");
            private final Button btnDisable = new Button("Disable");

            private final HBox box = new HBox(8,btnEdit,btnDisable);

            {
                btnEdit.setOnAction(e -> {

                    PromotionRow row =
                            getTableView().getItems().get(getIndex());

                    openEditDialog(row);

                });

                btnDisable.setOnAction(e -> {

                    PromotionRow row =
                            getTableView().getItems().get(getIndex());

                    row.setStatus("Inactive");
                    promotionTable.refresh();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty){

                super.updateItem(item,empty);

                setGraphic(empty ? null : box);

            }
        });
    }

    private void setupFilters(){

        cbStatus.getItems().addAll("All","Active","Inactive","Expired");
        cbService.getItems().addAll("All","Home Cleaning","Office Cleaning","Babysitting");

        cbStatus.setValue("All");
        cbService.setValue("All");
    }

    private void setupRowDoubleClick(){

        promotionTable.setRowFactory(tv -> {

            TableRow<PromotionRow> row = new TableRow<>();

            row.setOnMouseClicked(e -> {

                if(e.getClickCount()==2 && !row.isEmpty()){

                    openPromotionDetail(row.getItem());

                }
            });

            return row;

        });

    }

    private void openPromotionDetail(PromotionRow promotion){

        if(openStages.containsKey(promotion)){

            Stage existing = openStages.get(promotion);
            existing.toFront();
            existing.setIconified(false);
            return;
        }

        try{

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/busybee/promotion/view/promotion_detail.xml"));

            Parent root = loader.load();

            PromotionDetailController controller = loader.getController();
            controller.setPromotion(promotion);

            Stage stage = new Stage();

            Scene scene = new Scene(root);
            scene.getStylesheets().addAll(
                    promotionTable.getScene().getStylesheets()
            );

            stage.setScene(scene);
            stage.setTitle("Promotion Detail");

            stage.setOnCloseRequest(event -> {

                if(!controller.hasUnsavedChanges())
                    return;

                event.consume();

                int choice =
                        DialogUtils.showConfirmationWithThreeOptions(
                                "Unsaved Changes",
                                "You have unsaved changes.",
                                "Save and Close",
                                "Don't Save",
                                "Cancel"
                        );

                if(choice == 1){
                    controller.setUnsavedChanges(false);
                    stage.close();
                }
                else if(choice == 2){
                    stage.close();
                }

            });

            stage.setOnHidden(e -> openStages.remove(promotion));

            StageManager.getInstance().addStage(stage);

            stage.show();

            openStages.put(promotion,stage);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void loadMockData(){

        masterData.addAll(

                new PromotionRow(
                        "Summer Sale",
                        "Home Cleaning",
                        "Percent",
                        "20%",
                        "2026-06-01",
                        "2026-06-30",
                        "Active"
                ),

                new PromotionRow(
                        "Babysit Promo",
                        "Babysitting",
                        "Fixed",
                        "50000",
                        "2026-05-01",
                        "2026-05-31",
                        "Expired"
                ),

                new PromotionRow(
                        "Office Discount",
                        "Office Cleaning",
                        "Percent",
                        "15%",
                        "2026-06-10",
                        "2026-07-30",
                        "Active"
                )
        );
    }
}