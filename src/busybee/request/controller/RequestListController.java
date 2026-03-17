package busybee.request.controller;

import busybee.request.model.RequestRow;
import busybee.util.DialogUtils;

import java.time.LocalDateTime;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;

public class RequestListController {

    @FXML private TableView<RequestRow> requestTable;

    @FXML private TableColumn<RequestRow,Integer> colRequestId;
    @FXML private TableColumn<RequestRow,String> colCustomerName;
    @FXML private TableColumn<RequestRow,String> colEmployeeName;
    @FXML private TableColumn<RequestRow,String> colRequestDate;
    @FXML private TableColumn<RequestRow,String> colScheduledStart;
    @FXML private TableColumn<RequestRow,String> colStatus;

    @FXML private Button btnCreate;

    private final ObservableList<RequestRow> masterData =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        setupTable();
        loadDummyData();

        btnCreate.setOnAction(e -> openCreateRequest());
    }

    private void setupTable() {

        colRequestId.setCellValueFactory(
                new PropertyValueFactory<>("requestId"));

        colCustomerName.setCellValueFactory(
                new PropertyValueFactory<>("customerName"));

        colEmployeeName.setCellValueFactory(
                new PropertyValueFactory<>("employeeName"));

        colRequestDate.setCellValueFactory(
                c -> new SimpleStringProperty(
                        c.getValue().getRequestDateFormatted()));

        colScheduledStart.setCellValueFactory(
                c -> new SimpleStringProperty(
                        c.getValue().getScheduledStartFormatted()));

        colStatus.setCellValueFactory(
                new PropertyValueFactory<>("status"));

        requestTable.setItems(masterData);
    }

    private void loadDummyData() {

        masterData.addAll(

                new RequestRow(
                        1,1,"John Doe",1,"Alice Johnson",
                        LocalDateTime.now().minusDays(2),
                        LocalDateTime.now().plusDays(1),
                        "Pending","Cleaning"),

                new RequestRow(
                        2,2,"Jane Smith",2,"Bob Wilson",
                        LocalDateTime.now().minusDays(1),
                        LocalDateTime.now().plusDays(2),
                        "Confirmed","Deep cleaning")
        );
    }

    private void openCreateRequest() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource(
                            "/busybee/request/view/request_create.xml"));

            Parent root = loader.load();

            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("Create Request");

            stage.show();

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}