package busybee.layout.sidebar;

import busybee.layout.MainLayoutController;
import javafx.fxml.FXML;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SidebarReceptionController {

    @FXML
    private TreeView<String> sidebarTree;

    @FXML
    private void initialize() {
        // Root invisible
        TreeItem<String> root = new TreeItem<>("Root");
        root.setExpanded(true);
        sidebarTree.setRoot(root);
        sidebarTree.setShowRoot(false);

        // Dashboard
        TreeItem<String> dashboard = new TreeItem<>("🏠 Dashboard");
        root.getChildren().add(dashboard);

        // Customers
        TreeItem<String> customers = new TreeItem<>("👥 Customers");
        root.getChildren().add(customers);

        // Service Requests
        TreeItem<String> requests = new TreeItem<>("📝 Service Requests");
        root.getChildren().add(requests);

        // Ongoing Status
        TreeItem<String> status = new TreeItem<>("📊 Ongoing Status");
        root.getChildren().add(status);

        // Billing / Payment
        TreeItem<String> billing = new TreeItem<>("💰 Billing / Payment");
        root.getChildren().add(billing);

        // Cell factory để highlight item active
        sidebarTree.setCellFactory(tv -> new TreeCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    getStyleClass().remove("active-item");
                } else {
                    setText(item);
                    if (isSelected()) {
                        if (!getStyleClass().contains("active-item")) {
                            getStyleClass().add("active-item");
                        }
                    } else {
                        getStyleClass().remove("active-item");
                    }
                }
            }
        });

        // Listener cho chọn item
        sidebarTree.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) return;
            String value = newVal.getValue();

            switch (value) {
                case "🏠 Dashboard" -> MainLayoutController.getInstance().showContent("Reception Dashboard VIEW HERE");
                case "👥 Customers" -> MainLayoutController.getInstance().showContent("CUSTOMERS VIEW HERE");
                case "📝 Service Requests" -> MainLayoutController.getInstance().showContent("SERVICE REQUESTS VIEW HERE");
                case "📊 Ongoing Status" -> MainLayoutController.getInstance().showContent("ONGOING STATUS VIEW HERE");
                case "💰 Billing / Payment" -> MainLayoutController.getInstance().showContent("BILLING / PAYMENT VIEW HERE");
            }
        });
    }
}
