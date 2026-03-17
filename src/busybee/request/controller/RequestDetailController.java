package busybee.request.controller;

import busybee.request.model.RequestItemRow;
import busybee.request.model.RequestRow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class RequestDetailController {

    @FXML private Label lblRequestIdValue;
    @FXML private Label lblCustomer;
    @FXML private Label lblEmployee;
    @FXML private Label lblRequestDate;
    @FXML private Label lblScheduledStart;
    @FXML private Label lblStatusValue;
    @FXML private TextArea txtNotes;

    @FXML private TableView<RequestItemRow> itemsTable;
    @FXML private TableColumn<RequestItemRow,String> colServiceName;
    @FXML private TableColumn<RequestItemRow,Integer> colQuantity;

    private final ObservableList<RequestItemRow> itemsData =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        colServiceName.setCellValueFactory(
                new PropertyValueFactory<>("serviceName")
        );

        colQuantity.setCellValueFactory(
                new PropertyValueFactory<>("quantity")
        );

        itemsTable.setItems(itemsData);
    }

    public void setRequest(RequestRow request) {

        lblRequestIdValue.setText(String.valueOf(request.getRequestId()));
        lblCustomer.setText(request.getCustomerName());
        lblEmployee.setText(request.getEmployeeName());
        lblRequestDate.setText(request.getRequestDateFormatted());
        lblScheduledStart.setText(request.getScheduledStartFormatted());
        lblStatusValue.setText(request.getStatus());
        txtNotes.setText(request.getNotes());

        loadMockItems();
    }

    private void loadMockItems() {

        itemsData.clear();

        itemsData.addAll(
                new RequestItemRow(1,1,1,"Home Cleaning",2),
                new RequestItemRow(2,1,2,"Deep Cleaning",1)
        );
    }

}